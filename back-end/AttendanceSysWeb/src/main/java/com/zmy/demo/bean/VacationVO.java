package com.zmy.demo.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zmy.demo.util.MyDate;

/**
 * 更详细的请假单信息,用于Controller层
 */
@lombok.Data public class VacationVO extends Vacation {
    private String type;        // 请假类型
    private String status;      // 请假单状态
    private String department;  // 员工所属部门

    private String startDate;   // 请假开始日期,形如"年-月-日",如"2020-12-7"
    private String startTime;   // 请假开始时间,形如"时-分",如"22-55"
    private String endDate;     // 请假结束日期
    private String endTime;     // 请假结束时间

    @JsonIgnore private String marriageType;        // 婚假详细类型, {"晚婚", "早婚假"}
    @JsonIgnore private String maternityType;       // 产假详细类型, {"难产", "多胞胎", "未满4月流产", "满4月流产", "其他"}
    @JsonIgnore private String maternityNum;        // 多胞胎数, >= 1

    public VacationVO(String userId, String userName, String reason,
                      String type, String status, String department,
                      String startDate, String startTime, String endDate, String endTime
    ) {
        super(userId, userName, reason);
        this.type = type;
        this.status = status;
        this.department = department;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    /**
     * @return 当前对象对应的Service层对象
     */
    public VacationBO toBO() {
        MyDate start = new MyDate(startDate, startTime);
        MyDate end = new MyDate(endDate, endTime);
        int vacLong = MyDate.deltaHours(end, start);
        int num = maternityNum == null ? 0 : Integer.parseInt(maternityNum);

        return new VacationBO(
                userId,
                userName,
                reason,
                vacLong,
                type,
                status,
                department,
                start,
                end,
                marriageType,
                maternityType,
                num
        );
    }
}
