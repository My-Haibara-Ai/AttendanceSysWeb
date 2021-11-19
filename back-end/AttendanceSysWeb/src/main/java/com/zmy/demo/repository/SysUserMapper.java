package com.zmy.demo.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository public interface SysUserMapper {

    /**
     * 根据用户名username(对应员工号userId)获取其角色名
     * @param username 用户名
     * @return 用户名为username的用户的角色名
     */
    String selectRoleNameByUsername(@Param("username") String username);
}
