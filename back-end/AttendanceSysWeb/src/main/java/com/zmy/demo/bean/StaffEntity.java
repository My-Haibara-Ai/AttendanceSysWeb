package com.zmy.demo.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 更详细的员工信息,用于Entity层
 */
@lombok.Data public class StaffEntity extends Staff {
    private int departId;      // 员工所属部门的部门编号

    public StaffEntity() {
        super();
    }

    public StaffEntity(String userId, String userName, String sex,
                   String email, int age, int employed, int departId
    ) {
        super(userId, userName, sex, email, age, employed);
        this.departId = departId;
    }

    /**
     * @return 当前对象对应的Service层对象
     */
    public StaffBO toBO() {
        return new StaffBO(
                userId,
                userName,
                sex,
                email,
                age,
                employed,
                mapper.getDepartById(departId)
        );
    }

    /**
     * 将Repository层员工信息对象列表转换为Service层员工信息对象列表
     * @param staffEntityList Repository层员工信息对象列表
     * @return 转换后的Service层员工信息对象列表
     */
    public static List<StaffBO> convertToBO(List<StaffEntity> staffEntityList) {
        List<StaffBO> staffBOList = new ArrayList<>();

        for (StaffEntity staffEntity: staffEntityList) {
            staffBOList.add(staffEntity.toBO());
        }

        return staffBOList;
    }
}
