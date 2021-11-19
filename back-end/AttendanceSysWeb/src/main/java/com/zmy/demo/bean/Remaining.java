package com.zmy.demo.bean;

@lombok.Data public class Remaining {
    private String userId;      // 用户名
    private int annual;         // 剩余年假(天)
    private int sick;           // 剩余带薪病假(天)
    private int marriage;       // 婚假
    private int checkUp;        // 产检假
    private int maternity;      // 产假
    private int feeding;        // 哺乳假
    private int paternity;      // 陪产假
    private int affair;         // 事假
}

