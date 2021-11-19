package com.zmy.demo.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zmy.demo.bean.Attendance;

@Repository public interface AttendanceMapper {

    /**
     * @param userId 用户名
     * @return userId对应的员工的考勤信息
     */
    Attendance selectByUserId(@Param("userId") String userId);

    /**
     * 根据考勤信息attendance更新员工号为userId的员工的考勤信息
     * @param attendance
     */
    void update(@Param("userId") String userId, @Param("attendance") Attendance attendance);
}
