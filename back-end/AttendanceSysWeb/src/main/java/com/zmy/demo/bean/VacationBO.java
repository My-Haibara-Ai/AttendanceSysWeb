package com.zmy.demo.bean;

import java.util.*;

import com.zmy.demo.util.MyDate;

/**
 * 更详细的请假单信息,用于Service层
 */
@lombok.Data public class VacationBO extends Vacation {
    private int vacLong;         // 假期时长(时)
    private String type;        // 请假类型
    private String status;      // 请假单状态
    private String department;  // 员工所属部门
    private MyDate start;       // 请假开始时间
    private MyDate end;         // 请假结束时间

    private String marriageType;        // 婚假详细类型, {"晚婚", "早婚假"}
    private String maternityType;       // 产假详细类型, {"难产", "多胞胎", "未满4月流产", "满4月流产", "其他"}
    private int maternityNum;           // 多胞胎数, >= 1

    public VacationBO(String userId, String userName, String reason, int vacLong,
                      String type, String status, String department, MyDate start, MyDate end
    ) {
        super(userId, userName, reason);
        this.vacLong = vacLong;
        this.type = type;
        this.status = status;
        this.department = department;
        this.start = start;
        this.end = end;
    }

    public VacationBO(String userId, String userName, String reason, int vacLong,
                      String type, String status, String department, MyDate start, MyDate end,
                      String marriageType, String maternityType, int maternityNum
    ) {
        super(userId, userName, reason);
        this.vacLong = vacLong;
        this.type = type;
        this.status = status;
        this.department = department;
        this.start = start;
        this.end = end;
        this.marriageType = marriageType;
        this.maternityType = maternityType;
        this.maternityNum = maternityNum;
    }

    /**
     * @return 当前对象对应的Controller层对象
     */
    public VacationVO toVO() {
        String[] startStr = start.split();
        String[] endStr = start.split();

        return new VacationVO (
                userId,
                userName,
                reason,
                type,
                status,
                department,
                startStr[0],
                startStr[1],
                endStr[0],
                endStr[1]
        );
    }

    /**
     * @return 当前对象对应的Repository层对象
     */
    public VacationEntity toEntity() {
        String[] startStr = start.split();
        String[] endStr = end.split();

        return new VacationEntity(
                userId,
                userName,
                reason,
                vacLong,
                mapper.getIdByType(type),
                mapper.getIdByStatus(status),
                mapper.getIdByDepart(department),
                startStr[0],
                startStr[1],
                endStr[0],
                endStr[1]
        );
    }

    /**
     * 将Service层请假单对象列表转换为Controller层请假单对象列表
     * @param vacBOList Service层请假单对象列表
     * @return 转换后的Controller层请假单对象列表
     */
    public static List<VacationVO> convertToVO(List<VacationBO> vacBOList) {
        List<VacationVO> vacVOList = new ArrayList<>();

        for (VacationBO vacBO: vacBOList) {
            vacVOList.add(vacBO.toVO());
        }

        return vacVOList;
    }
}
