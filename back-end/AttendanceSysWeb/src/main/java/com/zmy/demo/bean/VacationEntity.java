package com.zmy.demo.bean;

import java.util.*;

import com.zmy.demo.util.MyDate;

/**
 * 更详细的请假单信息,用于Repository层
 */
@lombok.Data public class VacationEntity extends Vacation {
    private int vacLong;            // 假期时长(时)
    private int typeId;             // 请假类型编号
    private int statusId;           // 请假单状态编号
    private int departId;           // 员工所属部门的部门编号

    private String startDate;       // 请假开始日期
    private String startTime;       // 请假开始时间
    private String endDate;         // 请假结束日期
    private String endTime;         // 请假结束时间

    public VacationEntity() {
        super();
    }

    public VacationEntity(String userId, String userName, String reason,
                          int vacLong, int typeId, int statusId, int departId,
                          String startDate, String startTime, String endDate, String endTime
    ) {
        super(userId, userName, reason);
        this.vacLong = vacLong;
        this.typeId = typeId;
        this.statusId = statusId;
        this.departId = departId;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    /**
     * @return 当前对象对应的Service层对象
     */
    public VacationBO toBO() {
        return new VacationBO(
                userId,
                userName,
                reason,
                vacLong,
                mapper.getTypeById(typeId),
                mapper.getStatusById(statusId),
                mapper.getDepartById(departId),
                new MyDate(startDate, startTime),
                new MyDate(endDate, endTime)
        );
    }

    /**
     * 将Repository层请假单对象列表转换为Service层请假单对象列表
     * @param vacEntityList Repository层请假单对象列表
     * @return 转换后的Service层请假单对象列表
     */
    public static List<VacationBO> convertToBO(List<VacationEntity> vacEntityList) {
        List<VacationBO> vacBOList = new ArrayList<>();

        for (VacationEntity vacEntity: vacEntityList) {
            vacBOList.add(vacEntity.toBO());
        }

        return vacBOList;
    }
}
