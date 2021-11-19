package com.zmy.demo.bean;

import com.zmy.demo.util.MyDate;

/**
 * 更详细的员工信息,用于Controller层
 */
@lombok.Data public class StaffVO extends Staff {
    private String department;      // 员工所属部门

    public StaffVO(String userId, String userName, String sex,
                   String email, int age, int employed, String department
    ) {
        super(userId, userName, sex, email, age, employed);
        this.department = department;
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
                department
        );
    }
}
