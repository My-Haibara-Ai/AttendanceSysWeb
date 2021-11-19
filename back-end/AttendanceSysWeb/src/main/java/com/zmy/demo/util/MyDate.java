package com.zmy.demo.util;

import java.util.*;

/**
 * 自定义时间类
 */
@lombok.Data public class MyDate {
    public static int HOUR_PER_DAY = 24;                                // 一天的小时数
    public static long MILLIS_PER_MINUTE = 60 * 1000;                   // 一分钟的毫秒数
    public static long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;        // 一小时的毫秒数

    private static List<MyDate> holidays = new ArrayList<>();   // 法定节假日列表

    private int year;       // 如2020
    private int month;      // 1-12
    private int day;        // 1-31
    private int hour;       // 0-23
    private int minute;     // 0-59

    // 计算2021法定节假日
    static {
        // 元旦
        for (int day = 1; day <= 3; day++) {
            holidays.add(new MyDate(1, day));
        }

        // 春节
        for (int day = 11; day <= 17; day++) {
            holidays.add(new MyDate(2, day));
        }

        // 清明
        for (int day = 3; day <= 5; day++) {
            holidays.add(new MyDate(4, day));
        }

        // 劳动节
        for (int day = 1; day <= 5; day++) {
            holidays.add(new MyDate(5, day));
        }

        // 端午节
        for (int day = 12; day <= 14; day++) {
            holidays.add(new MyDate(6, day));
        }

        // 中秋节
        for (int day = 19; day <= 21; day++) {
            holidays.add(new MyDate(9, day));
        }

        // 国庆节
        for (int day = 1; day <= 7; day++) {
            holidays.add(new MyDate(10, day));
        }
    }

    /**
     * 使用当前时间构造一个日期对象
     */
    public MyDate() {
        this.setUsingCalendar(new GregorianCalendar());
    }

    /**
     * @param date 日期,形如"年-月-日",例如"2020-12-7"
     * @param time 时间,形如"时-分",例如"13-55"
     */
    public MyDate(String date, String time) {
        String[] dates = date.split("-");
        String[] times = time.split("-");

        this.year = Integer.parseInt(dates[0]);
        this.month = Integer.parseInt(dates[1]);
        this.day = Integer.parseInt(dates[2]);

        this.hour = Integer.parseInt(times[0]);
        this.minute = Integer.parseInt(times[1]);
    }

    /**
     * 根据年月日时分构造MyDate对象
     */
    public MyDate(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    // 根据月、日构造MyDate日期对象
    private MyDate(int month, int day) {
        this(0, month, day, 0, 0);
    }

    // 根据日期date构造MyDate日期对象
    public MyDate(MyDate date) {
        this(date.year, date.month, date.day, date.hour, date.minute);
    }

    // 根据日历c构造MyDate日期对象
    private MyDate(Calendar c) {
        this.setUsingCalendar(c);
    }

    // 使用日历c更新当前日期对象
    private void setUsingCalendar(Calendar c) {
        this.year = c.get(Calendar.YEAR);
        this.month = c.get(Calendar.MONTH) + 1;  // 0-11 -> 1-12
        this.day = c.get(Calendar.DAY_OF_MONTH);
        this.hour = c.get(Calendar.HOUR_OF_DAY);
        this.minute = c.get(Calendar.MINUTE);
    }

    /**
     * 将当前对象拆分为"年-月-日"形式与"时-分"形式的两个字符串,
     * @return 拆分后的字符串形成的数组,如2020年12月7日13点55分返回["2020-12-7", "13-55"]
     */
    public String[] split() {
        return new String[] {
                this.year + "-" + this.month + "-" + this.day,
                this.hour + "-" + this.minute
        };
    }

    // 返回当前日期对象的日历表示
    private Calendar toCalendar() {
        return new GregorianCalendar(year, month - 1, day, hour, minute);
    }

    /**
     * @return 当前日期是不是法定节假日
     */
    public boolean isHoliday() {
        for (MyDate holiday: holidays) {
            if (this.month == holiday.month && this.day == holiday.day) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return 当前日期是不是周末
     */
    public boolean isWeekends() {
        Calendar c = this.toCalendar();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);    // 一周中的第几天

        // 周日或周六
        return dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY;
    }

    /**
     * @param date 另一个日期
     * @return 当前日期与date是否是同一年同一月同一天
     */
    public boolean isSameDay(MyDate date) {
        return this.year == date.year &&
                this.month == date.month &&
                this.day == date.day;
    }

    @Override public String toString() {
        return year + "-" + month + "-" + day + " " + hour + ":" + minute;
    }

    /**
     * 返回日期在start之后,end之前的MyDate对象的列表
     * end的日期至少应该start多一天
     * @param start 起始日期
     * @param end 结束日期
     * @return start和end之间的日期对象列表
     */
    public static List<MyDate> datesBetween(MyDate start, MyDate end) {
        List<MyDate> dateList = new ArrayList<>();
        MyDate temp = new MyDate(start);
        Calendar temp_cal;

        while (true) {
            temp_cal = temp.toCalendar();
            temp_cal.add(Calendar.DATE, 1);   // 加一天
            temp = new MyDate(temp_cal);

            if (temp.isSameDay(end)) {
                return dateList;
            } else {
                dateList.add(temp);
            }
        }
    }

    /**
     * 计算date1比date2多的小时数(四舍五入)
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return date1比date2多的小时数,为负数代表date1早于date2
     */
    public static int deltaHours(MyDate date1, MyDate date2) {
        long date1_m = date1.toCalendar().getTimeInMillis();    // date1的毫秒值
        long date2_m = date2.toCalendar().getTimeInMillis();    // date2的毫秒值

        return (int) Math.round(((double) (date1_m - date2_m)) / MILLIS_PER_HOUR);
    }

    /**
     * 计算date1比date2多的分钟数
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return date1比date2多的小时数,为负数代表date1早于date2
     */
    private static int deltaMinutes(MyDate date1, MyDate date2) {
        long date1_m = date1.toCalendar().getTimeInMillis();    // date1的毫秒值
        long date2_m = date2.toCalendar().getTimeInMillis();    // date2的毫秒值

        return (int) ((date1_m - date2_m) / MILLIS_PER_MINUTE);
    }

    /**
     * 计算集合[start0, end0]与集合[start1, end1]的交集跨越了多少个小时(四舍五入)
     * 如果交集为空集,则跨域了0个小时
     * 其中start0早于end0、start1早于end1，且四个时间均为同年同月同日
     * @param start0 起始时间0
     * @param end0 结束时间0
     * @param start1 起始时间1
     * @param end1 结束时间1
     * @return 集合[start0, end0]与集合[start1, end1]的交集跨越的小时数(四舍五入)，交集为空集则返回0
     */
    public static int intersection(MyDate start0, MyDate end0, MyDate start1, MyDate end1) {
        // 找出开始时间中最晚的那一个和结束时间中最早的那一个
        MyDate start = max(start0, start1);
        MyDate end = max(end0, end1);

        if (end.earlierThan(start)) {
            // 交集为空集
            return 0;
        } else {
            return deltaHours(end, start);
        }
    }

    /**
     * 计算集合[start0, end0]减去集合[start1, end1]得到的差集跨越了多少个小时(四舍五入)
     * 其中start0早于end0、start1早于end1，且四个时间均为同年同月同日
     * @param start0 起始时间0
     * @param end0 结束时间0
     * @param start1 起始时间1
     * @param end1 结束时间1
     * @return 集合[start0, end0]减去集合[start1, end1]得到的差集跨越的小时数(四舍五入)
     */
    public static int substration(MyDate start0, MyDate end0, MyDate start1, MyDate end1) {
        // 找出开始时间中最晚的那一个和结束时间中最早的那一个
        MyDate start = max(start0, start1);
        MyDate end = max(end0, end1);

        if (end.earlierThan(start)) {
            // 交集为空集
            return deltaHours(end0, start0);
        } else {
            int delta = deltaMinutes(end0, start0) - deltaMinutes(end, start);
            return (int) Math.round(((double) delta));
        }
    }

    /**
     * @param date 另一个日期
     * @return 忽略年月日,判断当前日期的时间(时、分)是否早于date
     */
    private boolean earlierThan(MyDate date) {
        return (this.hour < date.hour) || (this.hour == date.hour && this.minute < date.minute);
    }

    /**
     * @param date1
     * @param date2
     * @return 日期date1和日期列表date2中时间最早(时、分)的日期
     */
    public static MyDate min(MyDate date1, MyDate date2) {
        if (date1.earlierThan(date2)) {
            return date1;
        } else {
            return date2;
        }
    }

    /**
     * @param date1
     * @param date2
     * @return 日期date1和日期列表date2中时间最晚(时、分)的日期
     */
    public static MyDate max(MyDate date1, MyDate date2) {
        if (date1.earlierThan(date2)) {
            return date2;
        } else {
            return date1;
        }
    }
}
