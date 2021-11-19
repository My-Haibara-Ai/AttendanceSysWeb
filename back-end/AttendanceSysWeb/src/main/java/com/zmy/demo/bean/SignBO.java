package com.zmy.demo.bean;

import com.zmy.demo.util.MyDate;

// 员工打卡(签到、签退)记录(用于Service层)
@lombok.Data public class SignBO {
    String userId;  // 员工号
    MyDate date;    // 打卡时间

    public SignBO(String userId, MyDate date) {
        this.userId = userId;
        this.date = date;
    }

    /**
     * @return 当前打卡记录对应的Repository层对象
     */
    public SignEntity toEntity() {
        return new SignEntity(
                userId,
                date.getYear(),
                date.getMonth(),
                date.getDay(),
                date.getHour(),
                date.getMinute()
        );
    };
}
