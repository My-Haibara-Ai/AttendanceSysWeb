package com.zmy.demo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 更详细的员工信息,用于Service层
 */
@lombok.Data public class StaffBO extends Staff {
    private String department;      // 员工所属部门

    public StaffBO(String userId, String userName, String sex,
                   String email, int age, int employed, String department
    ) {
        super(userId, userName, sex, email, age, employed);
        this.department = department;
    }

    /**
     * @return 当前对象对应的Controller层对象
     */
    public StaffVO toVO() {
        return new StaffVO(
                userId,
                userName,
                sex,
                email,
                age,
                employed,
                department
        );
    }

    /**
     * @return 当前对象对应的Repository层对象
     */
    public StaffEntity toEntity() {
        return new StaffEntity(
                userId,
                userName,
                sex,
                email,
                age,
                employed,
                mapper.getIdByDepart(department)
        );
    }

    /**
     * 将Service层员工信息对象列表转换为Controller层员工信息对象列表
     * @param staffBOList Repository层员工信息对象列表
     * @return 转换后的Controller层员工信息对象列表
     */
    public static List<StaffVO> convertToVO(List<StaffBO> staffBOList) {
        List<StaffVO> staffVOList = new ArrayList<>();

        for (StaffBO staffBO: staffBOList) {
            staffVOList.add(staffBO.toVO());
        }

        return staffVOList;
    }
}
