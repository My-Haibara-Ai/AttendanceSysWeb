package com.zmy.demo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zmy.demo.util.Mapper;

/**
 * 员工信息基类
 */
@lombok.Data
public class Staff {
    protected String userId;      // 员工号
    protected String userName;    // 姓名
    protected String sex;         // 性别 {"男","女"}
    protected String email;       // 电子邮箱
    protected int age;            // 年龄(年)
    protected int employed;       // 工龄(年)

    @JsonIgnore protected Mapper mapper = new Mapper();

    public Staff() {}

    public Staff(String userId, String userName, String sex,
                 String email, int age, int employed
    ) {
        this.userId = userId;
        this.userName = userName;
        this.sex = sex;
        this.email = email;
        this.age = age;
        this.employed = employed;
    }
}
