package com.zmy.demo.util;

import java.util.*;

import com.zmy.demo.bean.Vacation;

public class Mapper {
    // 部门与部门编号的映射关系
    private static String[] departList = {"技术部门", "运营部门", "产品部门", "市场部门", "综合部门"};
    private static Map<String, Integer> departMapper = new HashMap<>();
    
    // 请假单状态与状态编号的映射关系
    private static String[] statusList = {
            Vacation.PERSONNEL, Vacation.MANAGER, Vacation.GENERAL, Vacation.PASS, Vacation.REFUSE
    }; 
    private static Map<String, Integer> statusMapper = new HashMap<>();    
    
    // 假期类型和类型编号的映射关系
    private static String[] typeList = {
            Vacation.Annual, Vacation.SICK, Vacation.MARRIAGE, Vacation.CHECK_UP,
            Vacation.MATERNITY, Vacation.FEEDING, Vacation.PATERNITY, Vacation.AFFAIR
    };
    private static Map<String, Integer> typeMapper = new HashMap<>();

    // 初始化映射关系
    static {
        for (int i = 0; i < departList.length; i++) {
            departMapper.put(departList[i], i + 1);
        }

        for (int i = 0; i < statusList.length; i++) {
            statusMapper.put(statusList[i], i + 1);
        }

        for (int i = 0; i < typeList.length; i++) {
            typeMapper.put(typeList[i], i + 1);
        }
    }

    /**
     * @param type 假期类型
     * @return type对应的类型编号
     */
    public int getIdByType(String type) {
        return typeMapper.get(type);
    }

    /**
     * @param id 假期类型编号
     * @return 编号为id的假期类型
     */
    public String getTypeById(int id) {
        return typeList[id - 1];
    }

    /**
     * @param status 请假单状态
     * @return status对应的状态编号
     */
    public int getIdByStatus(String status) {
        return statusMapper.get(status);
    }

    /**
     * @param id 请假单状态编号
     * @return 编号为id的请假单状态
     */
    public String getStatusById(int id) {
        return statusList[id - 1];
    }

    /**
     * @param department 员工所属部门的部门名
     * @return 部门department对应的部门编号
     */
    public int getIdByDepart(String department) {
        return departMapper.get(department);
    }

    /**
     * @param id 部门编号
     * @return 编号为id的部门的部门名
     */
    public String getDepartById(int id) {
        return departList[id - 1];
    }
}
