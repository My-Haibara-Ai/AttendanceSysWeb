package com.zmy.demo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zmy.demo.util.Mapper;

/**
 * 请假单基本信息
 */
@lombok.Data public class Vacation {
    protected String userId;        // 员工号
    protected String userName;      // 姓名
    protected String reason;        // 请假理由

    @JsonIgnore protected Mapper mapper = new Mapper();

    // 请假单状态
    public static String PASS       = "通过";
    public static String REFUSE     = "拒绝";
    public static String PERSONNEL  = "人事部审核中";
    public static String MANAGER    = "部门经理审核中";
    public static String GENERAL    = "总经理审核中";

    // 假期类型
    public static String Annual     = "年假";
    public static String SICK       = "病假";
    public static String MARRIAGE   = "婚假";
    public static String CHECK_UP   = "产检假";
    public static String MATERNITY  = "产假";
    public static String FEEDING    = "哺乳假";
    public static String PATERNITY  = "陪产假";
    public static String AFFAIR     = "事假";

    public Vacation() {}

    public Vacation(String userId, String userName, String reason) {
        this.userId = userId;
        this.userName = userName;
        this.reason = reason;
    }
}
