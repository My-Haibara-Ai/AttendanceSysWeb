package com.zmy.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zmy.demo.bean.*;
import com.zmy.demo.util.*;
import com.zmy.demo.util.menu.*;
import com.zmy.demo.repository.*;

@Service public class StaffService {
    @Autowired private SysUserMapper sysUserMapper;
    @Autowired private StaffMapper staffMapper;


    // 普通员工、人事部、部门经理、总经理的菜单栏
    private static Menu staffMenu ;
    private static Menu personnelMenu;
    private static Menu managerMenu;
    private static Menu generalMenu;

    static {
        // 构造孩子菜单项

        MenuChild m11 = new MenuChild(11, "请假表", "vacationTable");
        MenuChild m21 = new MenuChild(21, "请假表状态", "myVacationTable");
        MenuChild m22 = new MenuChild(22, "员工请假信息", "othersVacationTable");
        MenuChild m31 = new MenuChild(31, "总经理审核", "checkByGeneral");
        MenuChild m32 = new MenuChild(32, "部门经理审核", "checkByManager");
        MenuChild m41 = new MenuChild(41, "员工信息管理", "staffManage");
        MenuChild m42 = new MenuChild(42, "人事科审核", "checkByPersonnel");
        MenuChild m43 = new MenuChild(43, "月底报表", "baobiao");

        // 分别计算普通员工、人事部、部门经理、总经理的菜单栏
        staffMenu = new Menu(
                new MenuRoot(1, "请假申请", m11),
                new MenuRoot(2, "请假管理", m21)
        );

        managerMenu = new Menu(
                new MenuRoot(1, "请假申请", m11),
                new MenuRoot(2, "请假管理", m21, m22),
                new MenuRoot(3, "请假审核", m32)
        );

        generalMenu = new Menu(
                new MenuRoot(1, "请假申请", m11),
                new MenuRoot(2, "请假管理", m21, m22),
                new MenuRoot(3, "请假审核", m31)
        );

        personnelMenu = new Menu(
                new MenuRoot(1, "请假申请", m11),
                new MenuRoot(2, "请假管理", m21),
                new MenuRoot(4, "人事科管理", m41, m42, m43)
        );
    }

    // 新增人员信息
    public Result add(StaffBO staff) {
        String userId = staff.getUserId();

        if (staffMapper.selectByUserId(userId) != null) {
            // 如果该员工已存在

            return new Result(1, false, "员工已存在", null);
        } else {
            // 员工不存在,向数据库中新增员工信息

            staffMapper.insert(staff.toEntity());

            return new Result(0, true, "添加员工成功", null);
        }
    }

    // 查看公司人员信息
    public Result getByPersonnel(String query, int currentPage, int pagesize) {
        if (query.isEmpty()) {
            // 查询一部分员工的信息

            int numStaffs = staffMapper.getNumOfAll();  // 公司员工总数

            // 公司员工信息列表
            List<StaffBO> staffList = StaffEntity.convertToBO(staffMapper.selectAll(
                    Page.getStartIndex(currentPage, pagesize), pagesize
            ));

            return new Result(0, true, "查询成功", new Page(
                    numStaffs, StaffBO.convertToVO(staffList)
            ));
        } else {
            // 查询某员工的信息

            // 取出该员工的信息
            StaffEntity staffEntity = staffMapper.selectByUserId(query);

            if (staffEntity == null) {
                return new Result(1, false, "员工不存在", null);
            } else {
                return new Result(2, true, "查询成功", new Page(
                        1, Arrays.asList(staffEntity.toBO().toVO())
                ));
            }
        }
    }

    // 删除人员信息
    public Result delete(String userId) {
        staffMapper.deleteByUserId(userId);

        return new Result(0, true, "删除员工信息成功", null);
    }

    // 更新人员信息
    public Result update(StaffBO staff) {
        staffMapper.updateByUserId(staff.getUserId(), staff.toEntity());

        return new Result(0, true, "更新员工信息成功", null);
    }

    // 查看个人基本信息
    public Result getSelf(String userId) {
        return new Result(
                0, true, "查询个人信息成功",
                staffMapper.selectByUserId(userId).toBO().toVO()
        );
    }

    // 获取自己的菜单栏信息
    public Menu getMenu(String userId) {
        String role = getRoleName(userId);              // 该员工的角色

        // 根据员工的角色返回相应的菜单栏
        if (role.equals("staff")) {
            return staffMenu;
        } else if (role.equals("manager")) {
            return managerMenu;
        } else if (role.equals("general")) {
            return generalMenu;
        } else {
            // personnel
            return personnelMenu;
        }
    }

    // 获取自己的角色名
    public String getRoleName(String userId) {
        return sysUserMapper.selectRoleNameByUsername(userId);
    }
}
