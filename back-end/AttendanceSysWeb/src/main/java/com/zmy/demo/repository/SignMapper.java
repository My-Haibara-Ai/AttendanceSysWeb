package com.zmy.demo.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zmy.demo.bean.SignEntity;

//
@Repository public interface SignMapper {
    /**
     * @param userId 员工号
     * @param year 打卡年份
     * @param month 打卡月份
     * @param day 打卡日
     * @return 员工号为userId的员工在年月日分别为year、month、day的那一天的签到记录
     */
    SignEntity selectInByDate(
            @Param("userId") String userId,
            @Param("year") int year,
            @Param("month") int month,
            @Param("day") int day
    );

    /**
     * @param userId 员工号
     * @param year 打卡年份
     * @param month 打卡月份
     * @param day 打卡日
     * @return 员工号为userId的员工在年月日分别为year、month、day的那一天的签退记录
     */
    SignEntity selectOutByDate(
            @Param("userId") String userId,
            @Param("year") int year,
            @Param("month") int month,
            @Param("day") int day
    );

    /**
     * @param sign 签到记录
     * 在数据库中插入一条签到记录sign
     */
    void insertSignIn(@Param("sign") SignEntity sign);

    /**
     * @param sign 签退记录
     * 在数据库中插入一条签退记录sign
     */
    void insertSignOut(@Param("sign") SignEntity sign);
}
