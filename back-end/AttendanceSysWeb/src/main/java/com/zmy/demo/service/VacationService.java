package com.zmy.demo.service;

import java.util.*;

import com.zmy.demo.util.MyDate;
import com.zmy.demo.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.demo.bean.*;
import com.zmy.demo.repository.*;
import com.zmy.demo.util.Result;

@Service public class VacationService {
    @Autowired private VacationMapper vacMapper;
    @Autowired private StaffMapper staffMapper;
    @Autowired private RemainingMapper remainingMapper;

    // 提出请假申请
    public Result apply(VacationBO vac) {
        String userId = vac.getUserId();    // 用户名
        String type = vac.getType();        // 假期类型
        int vacLong = vac.getVacLong();     // 请假时长

        // 取出该员工的假期余额信息
        Remaining remaining = remainingMapper.selectByUserId(userId);

        boolean isMarriage = type.equals(Vacation.MARRIAGE);    // 婚假
        boolean isMaternity = type.equals(Vacation.MATERNITY);  // 产假
        boolean isAnnual = type.equals(Vacation.Annual);        // 年假
        boolean isAffair = type.equals(Vacation.AFFAIR);        // 事假

        // 计算婚假和产假余额
        if (
                (isMarriage && remaining.getMarriage() != 0) ||
                (isMaternity && remaining.getMaternity() != 0)
        ) {
            int max = 0;    // 假期最多可休天数
            String detailedType = "";   // 详细假期类型

            // 计算假期最多可休天数
            if (isMarriage) {
                detailedType = vac.getMarriageType();

                if (detailedType.equals("早婚假")) {
                    max = 3;
                } else if (detailedType.equals("晚婚")) {
                    max = 10;
                }

                remaining.setMarriage(max);
            } else if (isMaternity) {
                detailedType = vac.getMaternityType();
                max = 98;       // 产假基础天数

                if (detailedType.equals("难产")) {
                    max += 15;
                } else if (detailedType.equals("多胞胎")) {
                    max += 15 * vac.getMaternityNum();
                } else if (detailedType.equals("未满4月流产")) {
                    max = 15;
                } else if (detailedType.equals("满4月流产")) {
                    max = 42;
                }

                remaining.setMaternity(max);
            }

            // 更新数据库
            remainingMapper.updateByUserId(userId, remaining);
        }

        String status = "";     // 请假单的最终状态
        Result result = null;   // 方法返回值

        // 处理请假申请
        if (isAnnual) {
            // 年假无需人事部审核且需要判断余额是否充足

            // 计算假期余额
            int balance = remaining.getAnnual();

            if (vacLong <= balance * MyDate.HOUR_PER_DAY) {
                status = Vacation.MANAGER;
                result = new Result(0, true, "请假申请成功,部门经理审核中", null);
            } else {
                status = Vacation.REFUSE;
                result = new Result(1, false, "请假申请失败,假期余额不足", null);
            }
        } else if (isAffair) {
            // 事假无需人事部审核
            status = Vacation.MANAGER;
            result = new Result(0, true, "请假申请成功,部门经理审核中", null);
        } else {
            // 其他假期需要人事部审核
            status = Vacation.PERSONNEL;
            result = new Result(2, true, "请假申请成功,人事部审核中", null);
        }

        vac.setStatus(status);

        // 更新数据库
        vacMapper.deleteByUserId(userId);
        vacMapper.insert(vac.toEntity());

        return result;
    }

    // 查看自己的请假申请
    public Result getSelf(String userId) {
        VacationEntity vac = vacMapper.selectByUserId(userId);  // 从数据库中读取请假单

        if (vac != null) {
            return new Result(0, true, "查询成功", vac.toBO().toVO());
        } else {
            return new Result(1, false, "你还没有申请请假", null);
        }
    }

    // 删除请假申请
    public void delete(String userId) {
        vacMapper.deleteByUserId(userId);
    }

    // 查看部门人员请假申请
    public Result getByManager(String userId, String query, int currentPage, int pagesize) {
        int departId = staffMapper.getDepartIdByUserId(userId); // 部门经理所属部门编号

        if (query.isEmpty()) {
            // 查询部分员工请假信息

            int numVacs = vacMapper.getNumByDepartId(departId); // 该部门请假申请总数

            // 该部门人员的请假申请
            List<VacationBO> vacList = VacationEntity.convertToBO(vacMapper.selectByDepartId(
                    departId, Page.getStartIndex(currentPage, pagesize), pagesize
            ));

            return new Result(0, true, "查询成功", new Page(
                    numVacs, VacationBO.convertToVO(vacList)
            ));
        } else {
            // 查询某员工请假信息

            VacationEntity vac = vacMapper.selectByUserId(query);

            if (vac == null) {
                // 请假单不存在
                return new Result(1, false, "该员工不存在或者尚未申请请假", null);
            } else if (vac.getDepartId() != departId) {
                return new Result(2, false, "该员工不属于该部门", null);
            } else {
                // 查询到请假单
                return new Result(3, true, "查询成功", new Page(
                        1, Arrays.asList(vac.toBO().toVO())
                ));
            }
        }
    }

    // 查看公司人员请假申请
    public Result getByGeneral(String query, int currentPage, int pagesize) {
        if (query.isEmpty()) {
            // 查询部分员工请假信息

            int numVacs = vacMapper.getNumOfAll();  // 该公司请假申请总数

            // 该公司人员的请假申请
            List<VacationBO> vacList = VacationEntity.convertToBO(vacMapper.selectAll(
                    Page.getStartIndex(currentPage, pagesize), pagesize
            ));

            return new Result(0, true, "查询成功", new Page(
                    numVacs, VacationBO.convertToVO(vacList)
            ));
        } else {
            // 查询某员工请假信息

            VacationEntity vac = vacMapper.selectByUserId(query);

            if (vac == null) {
                // 请假单不存在
                return new Result(1, false, "该员工不存在或者尚未申请请假", null);
            } else {
                // 查询到请假单
                return new Result(2, true, "查询成功", new Page(
                        1, Arrays.asList(vac.toBO().toVO())
                ));
            }
        }
    }
}
