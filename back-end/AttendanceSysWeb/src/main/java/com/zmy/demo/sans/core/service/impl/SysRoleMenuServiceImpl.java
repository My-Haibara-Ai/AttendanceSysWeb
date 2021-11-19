package com.zmy.demo.sans.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zmy.demo.sans.core.dao.SysRoleMenuDao;
import com.zmy.demo.sans.core.entity.SysRoleMenuEntity;
import com.zmy.demo.sans.core.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * @Description 角色与权限业务实现
 * @Author Sans
 * @CreateTime 2019/9/14 15:57
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

}