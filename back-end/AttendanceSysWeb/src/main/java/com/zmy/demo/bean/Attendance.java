package com.zmy.demo.bean;

import com.zmy.demo.util.MyDate;

/**
 * 员工考勤信息
 */
@lombok.Data public class Attendance {
    private String userId;          // 员工号
    private int workHour;           // 出勤时长(时)
    private int paid;               // 带薪假期时长(时)
    private int unpaid;             // 不带薪假期时长(时)
    private int valid;              // 有效工作时长(时)

    // 每天的工作时间段:早上8点到晚上6点
    public static int workStartHour     = 8;
    public static int workStartMinute   = 0;
    public static int workEndHour       = 18;
    public static int workEndMinute     = 0;

    // @return date代表的日期是否是工作日
    public static boolean isWorkday(MyDate date) {
        return ! (date.isHoliday() || date.isWeekends());
    }

    // 生成和date同年同月同日的工作开始时间和工作结束时间
    // @return 第一个元素为工作开始时间,第二个元素为工作结束时间
    public static MyDate[] generateWorkTime(MyDate date) {
        MyDate[] workTime = {new MyDate(date), new MyDate(date)};

        workTime[0].setHour(workStartHour);
        workTime[0].setMinute(workStartMinute);
        workTime[1].setHour(workEndHour);
        workTime[1].setMinute(workEndMinute);

        return workTime;
    }
}
