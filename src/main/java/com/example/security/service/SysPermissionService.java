package com.example.security.service;

import com.example.security.dao.SysPermissionMapper;
import com.example.security.dto.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SysPermissionService
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/24 9:40
 * @Version 1.0
 **/
@Service
public class SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    /**
     * 获取指定角色所有权限
     */
    public List<SysPermission> listByRoleId(Integer roleId) {
        return sysPermissionMapper.listByRoleId(roleId);
    }
}
