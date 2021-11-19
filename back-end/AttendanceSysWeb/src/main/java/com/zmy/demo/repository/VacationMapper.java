package com.zmy.demo.repository;

import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zmy.demo.bean.VacationEntity;

@Repository public interface VacationMapper {

    /**
     * 从索引为start的请假单开始查询最多num条请假记录
     * limit #{start}, #{num}
     * @param start 起始位置索引(初始元组索引为0)
     * @param num 查询的最大数据条数
     * @return 查询到的请假记录集
     */
    List<VacationEntity> selectAll(
            @Param("start") int start,
            @Param("num") int num
    );

    /**
     * @param userId 员工号
     * @return 员工号为userId的员工的请假申请
     */
    VacationEntity selectByUserId(@Param("userId") String userId);

    /**
     * 在请假单状态编号为statusId的请假单中,从索引为start的请假单开始查询最多num条请假记录
     * where status_id=#{statusId}
     * limit #{start}, #{num}
     * @param statusId 请假单状态编号
     * @param start 起始位置索引(初始元组索引为0)
     * @param num 查询的最大数据条数
     * @return 查询到的请假记录集
     */
    List<VacationEntity> selectByStatusId(
            @Param("statusId") int statusId,
            @Param("start") int start,
            @Param("num") int num
    );

    /**
     * 在部门编号为departId的请假单中,从索引为start的请假单开始查询最多num条请假记录
     * where depart_id=#{departId}
     * limit #{start}, #{num}
     * @param departId 部门编号
     * @param start 起始位置索引(初始元组索引为0)
     * @param num 查询的最大数据条数
     * @return 查询到的请假记录集
     */
    List<VacationEntity> selectByDepartId(
            @Param("departId") int departId,
            @Param("start") int start,
            @Param("num") int num
    );

    /**
     * 请假单状态编号为statusId且部门编号为departId的请假单中,
     * 从索引为start的请假单开始查询最多num条请假记录
     * where status_id=#{statusId} and depart_id=#{departId}
     * limit #{start}, #{num}
     * @param statusId 请假单状态编号
     * @param start 起始位置索引(初始元组索引为0)
     * @param num 查询的最大数据条数
     * @return 查询到的请假记录集
     */
    List<VacationEntity> selectForManager(
            @Param("statusId") int statusId,
            @Param("departId") int departId,
            @Param("start") int start,
            @Param("num") int num
    );

    /**
     * @return 总的请假申请数
     */
    int getNumOfAll();

    /**
     * @param statusId 请假单状态编号
     * @return 请假单状态编号为statusId的请假申请数
     */
    int getNumByStatusId(@Param("statusId") int statusId);

    /**
     * @param departId 部门编号
     * @return 部门编号为departId的请假申请数
     */
    int getNumByDepartId(@Param("departId") int departId);

    /**
     *
     * @param statusId 请假单状态编号
     * @param departId 部门编号
     * @return 请假单状态编号为statusId且部门编号为departId的请假单数目
     */
    int getNumForManager(@Param("statusId") int statusId, @Param("departId") int departId);

    /**
     * 将用户名为userId的用户的请假申请的状态编号修改为statusId
     * @param userId 用户名
     * @param statusId 请假单状态编号
     */
    void setStatusIdByUserId(@Param("userId") String userId, @Param("statusId") int statusId);

    /**
     * 删除员工号为userId的员工的请假申请
     * @param userId 员工号
     */
    void deleteByUserId(@Param("userId") String userId);

    /**
     * 插入一条请假记录vac
     * @param vac 请假记录
     */
    void insert(@Param("vac") VacationEntity vac);
}
