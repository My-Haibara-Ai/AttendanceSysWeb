package com.zmy.demo.repository;

import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zmy.demo.bean.StaffEntity;

// 操作staff表
@Repository public interface StaffMapper {

    /**
     * 查询用户名为userId的员工
     * @param userId 用户名
     * @return 用户名为userId的员工
     */
    StaffEntity selectByUserId(@Param("userId") String userId);

    /**
     * 从索引为start的员工开始查询最多num个员工的信息
     * limit #{start}, #{num}
     * @param start 起始位置索引(初始元组索引为0)
     * @param num 查询的最大数据条数
     * @return 查询到的员工集
     */
    List<StaffEntity> selectAll(@Param("start") int start, @Param("num") int num);

    /**
     * 从索引为start的员工开始查询最多num个员工的员工号
     * limit #{start}, #{num}
     * @param start 起始位置索引(初始元组索引为0)
     * @param num 查询的最大数据条数     * @return 查询到的员工号集
     */
    List<String> selectUserIds(@Param("start") int start, @Param("num") int num);

    /**
     * @return 公司的员工总数
     */
    int getNumOfAll();

    /**
     * 获得用户名为userId的用户的所属部门编号
     * @param userId 用户名
     * @return 用户名为userId的用户的所属部门编号
     */
    int getDepartIdByUserId(@Param("userId") String userId);

    /**
     * 在数据库中增加一个员工staff
     * @param staff 新增员工的信息
     */
    void insert(@Param("staff") StaffEntity staff);

    /**
     * 删除员工号为userId的员工
     * @param userId 要删除的员工的员工号
     */
    void deleteByUserId(@Param("userId") String userId);

    /**
     * 根据staff更新数据库中员工号为userId的员工
     * @param userId 要更新信息的员工的员工号
     * @param staff 员工更新后的信息
     */
    void updateByUserId(@Param("userId") String userId, @Param("staff") StaffEntity staff);
}
