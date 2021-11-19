package com.zmy.demo.sans.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.zmy.demo.sans.core.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限DAO
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {

}