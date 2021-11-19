package com.zmy.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.demo.bean.*;
import com.zmy.demo.repository.*;
import com.zmy.demo.util.*;

@Service public class ApprovalService {
    @Autowired private VacationMapper vacMapper;
    @Autowired private EnumMapper enumMapper;
    @Autowired private StaffMapper staffMapper;
    @Autowired private RemainingMapper remainingMapper;
    @Autowired private AttendanceMapper attendanceMapper;

    // 人事部查看待审核请假申请
    public Result getByPersonnel(String query, int currentPage, int pagesize) {
        int statusId = enumMapper.getIdByStatus(Vacation.PERSONNEL);    // 状态编号
        int numVacs = vacMapper.getNumByStatusId(statusId);             // 该状态下的请假单总数

        // 获取人事部待审核的请假单信息
        List<VacationBO> vacList = VacationEntity.convertToBO(vacMapper.selectByStatusId(
                statusId, Page.getStartIndex(currentPage, pagesize), pagesize
        ));

        return new Result(0, true, "查询成功",
                new Page(numVacs, VacationBO.convertToVO(vacList))
        );
    }

    // 部门经理查看待审核请假申请
    public Result getByManager(String userId, String query, int currentPage, int pagesize) {
        int departId = staffMapper.getDepartIdByUserId(userId); // 部门经理所属部门编号
        int statusId = enumMapper.getIdByStatus(Vacation.MANAGER);  // 状态编号
        int numVacs = vacMapper.getNumForManager(statusId, departId);   // 该部门该状态下的请假单总数

        // 获取部门经理待审核的请假单信息
        List<VacationBO> vacList = VacationEntity.convertToBO(vacMapper.selectForManager(
                statusId, departId, Page.getStartIndex(currentPage, pagesize), pagesize
        ));

        return new Result(0, true, "查询成功",
                new Page(numVacs, VacationBO.convertToVO(vacList))
        );
    }

    // 总经理查看待审核请假申请
    public Result getByGeneral(String query, int currentPage, int pagesize) {
        int statusId = enumMapper.getIdByStatus(Vacation.GENERAL);      // 状态编号
        int numVacs = vacMapper.getNumByStatusId(statusId);             // 该状态下的请假单总数

        // 获取总经理待审核的请假单信息
        List<VacationBO> vacList = VacationEntity.convertToBO(vacMapper.selectByStatusId(
                statusId, Page.getStartIndex(currentPage, pagesize), pagesize
        ));

        return new Result(0, true, "查询成功",
                new Page(numVacs, VacationBO.convertToVO(vacList))
        );
    }

    // 人事部审核
    public void approveByPersonnel(String userId, String pass) {
        VacationBO vac = vacMapper.selectByUserId(userId).toBO();       // 该员工的请假单
        Remaining remaining = remainingMapper.selectByUserId(userId);   // 假期余额信息
        String type = vac.getType();        // 假期类型
        int vacLong = vac.getVacLong();     // 请假时长
        String status = null;               // 请假单最后的状态

        boolean isMarriage = type.equals(Vacation.MARRIAGE);    // 婚假
        boolean isSick = type.equals(Vacation.SICK);            // 病假
        boolean isMaternity = type.equals(Vacation.MATERNITY);  // 产假
        boolean isPaternity = type.equals(Vacation.PATERNITY);  // 陪产假

        if (pass.equals(Vacation.REFUSE)) {
            status = Vacation.REFUSE;

            if (isMarriage) {
                remaining.setMarriage(0);
            } else if (isMaternity) {
                remaining.setMaternity(0);
            }
        } else {
            int balance;        // 假期余额

            if (isSick) {
                balance = remaining.getSick();

                if (vacLong <= balance * MyDate.HOUR_PER_DAY) {
                    status = Vacation.PASS;
                    remaining.setSick(balance - vacLong / MyDate.HOUR_PER_DAY);
                } else {
                    status = Vacation.REFUSE;
                }
            } else if (isMarriage) {
                balance = remaining.getMarriage();

                if (vacLong <= balance * MyDate.HOUR_PER_DAY) {
                    status = Vacation.PASS;
                    remaining.setMarriage(balance - vacLong / MyDate.HOUR_PER_DAY);
                } else {
                    status = Vacation.REFUSE;
                }
            } else if (isMaternity) {
                balance = remaining.getMaternity();

                if (vacLong <= balance * MyDate.HOUR_PER_DAY) {
                    status = Vacation.PASS;
                    remaining.setMaternity(balance - vacLong / MyDate.HOUR_PER_DAY);
                } else {
                    status = Vacation.REFUSE;
                }
            } else if (isPaternity) {
                balance = remaining.getPaternity();

                if (vacLong <= balance * MyDate.HOUR_PER_DAY) {
                    status = Vacation.PASS;
                    remaining.setPaternity(balance - vacLong / MyDate.HOUR_PER_DAY);
                } else {
                    status = Vacation.REFUSE;
                }
            } else {
                status = Vacation.PASS;
            }
        }

        // 修改数据库中的请假单信息和假期余额信息
        vacMapper.setStatusIdByUserId(
                userId,
                enumMapper.getIdByStatus(status)
        );
        remainingMapper.updateByUserId(userId, remaining);

        if (status.equals(Vacation.PASS)) {
            computeAttendance(userId);
        }
    }

    // 部门经理审核
    public void approveByManager(String userId, String pass) {
        VacationBO vac = vacMapper.selectByUserId(userId).toBO();   // 该员工的请假申请
        String status;          // 请假单状态

        if (pass.equals(Vacation.PASS)) {
            if (vac.getVacLong() <= 3 * MyDate.HOUR_PER_DAY) {
                // 请假天数不超过三天
                status = Vacation.PASS;
            } else {
                // 请假天数超过三天
                status = Vacation.GENERAL;
            }
        } else {
            status = Vacation.REFUSE;
        }

        // 修改数据库中的请假单状态
        vacMapper.setStatusIdByUserId(
                userId,
                enumMapper.getIdByStatus(status)        // 请假单状态编号
        );

        if (status.equals(Vacation.PASS)) {
            computeAttendance(userId);
        }
    }

    // 总经理审核
    public void approveByGeneral(String userId, String pass) {
        String status = pass;       // 请假单状态

        // 修改数据库中的请假单状态
        vacMapper.setStatusIdByUserId(
                userId,
                enumMapper.getIdByStatus(status)        // 请假单状态编号
        );

        if (status.equals(Vacation.PASS)) {
            computeAttendance(userId);
        }
    }

    // 计算员工号为userId的员工的带薪假期和不带薪假期时长
    private void computeAttendance(String userId) {
        VacationBO vac = vacMapper.selectByUserId(userId).toBO();           // 该员工的请假申请(已通过)
        Attendance attendance = attendanceMapper.selectByUserId(userId);    // 该员工的考勤信息
        MyDate start = vac.getStart();      // 请假开始时间
        MyDate end = vac.getEnd();          // 请假结束时间
        int hour = 0;                       // 除去非工作时间(包括非上班时间、节假日、周末)后的实际假期时长

        /*
         * 计算带薪假期时长或不带薪假期时长
         */
        if (start.isSameDay(end)) {
            // 假期只跨越了一天
            if (! Attendance.isWorkday(start)) {
                // 当天不是工作日
            } else {
                // 当天是工作日

                // 当天的工作开始时间与结束时间
                MyDate[] workTime = Attendance.generateWorkTime(start);

                // 计算假期时间段与工作时间段的交集
                hour = MyDate.intersection(start, end, workTime[0], workTime[1]);
            }
        } else {
            // 假期跨越了多天

            List<MyDate> dateList = MyDate.datesBetween(start, end);

            for (MyDate date: dateList) {
                if (Attendance.isWorkday(date)) {
                    hour += Attendance.workEndHour - Attendance.workStartHour;
                }
            }

            // 计算第一天的假期时长
            if (Attendance.isWorkday(start)) {
                MyDate[] workTime = Attendance.generateWorkTime(start);

                hour += MyDate.intersection(start, workTime[1], workTime[0], workTime[1]);
            }

            // 计算最后一天的假期时长
            if (Attendance.isWorkday(end)) {
                MyDate[] workTime = Attendance.generateWorkTime(end);

                hour += MyDate.intersection(workTime[0], end, workTime[0], workTime[1]);
            }
        }

        /*
         * 更新员工考勤信息
         */

        if (vac.getType().equals(Vacation.AFFAIR)) {
            // 不带薪假期
            attendance.setUnpaid(attendance.getUnpaid() + hour);
        } else {
            // 带薪假期
            attendance.setPaid(attendance.getPaid() + hour);
            attendance.setValid(attendance.getValid() + hour);
        }

        attendanceMapper.update(userId, attendance);
    }
}
