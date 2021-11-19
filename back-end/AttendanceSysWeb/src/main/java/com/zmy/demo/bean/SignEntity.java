package com.zmy.demo.bean;

import com.zmy.demo.util.MyDate;

// 员工打卡(签到签退)记录(用于Repository层)
@lombok.Data public class SignEntity {
    private String userId;      // 用户名
    private int year;           // 年份,如2020
    private int month;          // 月份,1-12
    private int day;            // 日子,1-31
    private int hour;           // 时,0-23
    private int minute;         // 分,0-59

    public SignEntity(String userId, int year, int month, int day, int hour, int minute) {
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * @return 当前打卡记录对应的Service层对象
     */
    public SignBO toBO() {
        return new SignBO(
                userId,
                new MyDate(year, month, day, hour, minute)
        );
    }
}
