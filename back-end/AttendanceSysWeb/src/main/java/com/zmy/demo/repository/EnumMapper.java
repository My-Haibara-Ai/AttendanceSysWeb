package com.zmy.demo.repository;

import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 操作数据库中的vac_type、vac_status、department表
 */
@Repository public interface EnumMapper {
    /**
     * 获取请假单状态status的状态编号
     * @param status 请假单状态
     * @return status对应的状态编号
     */
    int getIdByStatus(@Param("status") String status);
}
