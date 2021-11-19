package com.zmy.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.demo.util.*;
import com.zmy.demo.bean.*;
import com.zmy.demo.repository.*;

// 包含了员工基本信息的详细考勤信息
@lombok.Data class AttendanceInfo {
    private StaffVO staff;              // 员工基本信息
    private Attendance attendance;      // 考勤信息

    public AttendanceInfo(StaffVO staff, Attendance attendance) {
        this.staff = staff;
        this.attendance = attendance;
    }
}

@Service public class AttendanceService {
    @Autowired private StaffMapper staffMapper;
    @Autowired private VacationMapper vacMapper;
    @Autowired private AttendanceMapper attendanceMapper;
    @Autowired private SignMapper signMapper;
    @Autowired private RemainingMapper remainingMapper;

    // 获取报表
    public Page getReport(String query, int currentPage, int pagesize) {
        int numStaffs = staffMapper.getNumOfAll();  // 公司员工总数
        List<AttendanceInfo> infoList = new ArrayList<>();  // 详细考勤信息列表

        // 当前页员工的员工号集合
        List<String> userIdList = staffMapper.selectUserIds(
                Page.getStartIndex(currentPage, pagesize), pagesize
        );

        // 获取当前页员工的详细考勤信息
        for (String userId: userIdList) {
            infoList.add(new AttendanceInfo(
                    staffMapper.selectByUserId(userId).toBO().toVO(),
                    attendanceMapper.selectByUserId(userId)
            ));
        }

        return new Page(numStaffs, infoList);
    }

    // 签到
    public Result signIn(String userId) {
        MyDate date = new MyDate();

        // 获取今天的签到记录
        SignEntity signEn = signMapper.selectInByDate(userId, date.getYear(), date.getMonth(), date.getDay());

        if (signEn != null) {
            return new Result(1, false, "今日已签到,无法再次签到", null);
        }

        if (date.isHoliday()) {
            return new Result(2, false, "节假日无法签到", null);
        }

        if (date.isWeekends()) {
            return new Result(3, false, "周末无法签到", null);
        }

        // 将今天的签到记录插入数据库中
        signMapper.insertSignIn(new SignBO(userId, date).toEntity());

        return new Result(0, true, "签到成功", null);
    }

    // 签退
    public Result signOut(String userId) {
        MyDate date = new MyDate();
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();

        // 取出今天的签到签退记录
        SignEntity signInEntity = signMapper.selectInByDate(userId, year, month, day);
        SignEntity signOutEntity = signMapper.selectOutByDate(userId, year, month, day);

        if (signInEntity == null) {
            return new Result(1, false, "今日未签到,无法进行签退", null);
        } else if (signOutEntity != null) {
            return new Result(2, false, "今日已签退,无法再次签退", null);
        }

        int hour = 0;           // 今日实际出勤时长
        MyDate signInDate = signInEntity.toBO().getDate();
        MyDate signOutDate = date;
        MyDate[] workTime = Attendance.generateWorkTime(date);  // 产生今日的工作开始结束时间

        // 计算签到签退区间与工作区间的交集
        hour += MyDate.intersection(signInDate, workTime[0], signOutDate, workTime[1]);

        // 取出员工的请假申请
        VacationEntity vacEntity = vacMapper.selectByUserId(userId);
        VacationBO vac;

        if (vacEntity == null
                || ! (vac = vacEntity.toBO())
                        .getStatus()
                        .equals(Vacation.PASS)
        ) {
            // 员工没有申请请假或员工的请假申请尚未通过
        } else {
            MyDate start = MyDate.max(workTime[0], signInDate);
            MyDate end = MyDate.min(workTime[1], signOutDate);
            MyDate vacStart = vac.getStart();
            MyDate vacEnd = vac.getEnd();

            // 计算实际出勤时长
            if (vacStart.isSameDay(vacEnd) && vacStart.isSameDay(start)) {
                // 假期仅跨越了一天
                hour -= MyDate.intersection(start, end, vacStart, vacEnd);
            } else {
                // 假期跨越了多天

                if (vacStart.isSameDay(start)) {
                    hour -= MyDate.intersection(start, end, vacStart, end);
                } else if (vacEnd.isSameDay(start)) {
                    hour -= MyDate.intersection(start, end, start, vacEnd);
                } else {
                    for (MyDate temp: MyDate.datesBetween(vacStart, vacEnd)) {
                        if (temp.isSameDay(start)) {
                            hour = 0;
                            break;
                        }
                    }
                }
            }
        }

        // 更新该员工的考勤信息
        Attendance attendance = attendanceMapper.selectByUserId(userId);

        attendance.setWorkHour(attendance.getWorkHour() + hour);
        attendance.setValid(attendance.getValid() + hour);
        attendanceMapper.update(userId, attendance);

        // 插入今天的签退记录
        signMapper.insertSignOut(new SignBO(userId, date).toEntity());

        return new Result(0, true, "签退成功", null);
    }
}
