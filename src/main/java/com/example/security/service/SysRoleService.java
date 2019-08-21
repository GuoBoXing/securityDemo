package com.example.security.service;

import com.example.security.dao.SysRoleMapper;
import com.example.security.dto.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysRoleService
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/22 14:29
 * @Version 1.0
 **/
@Service
public class SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    public SysRole selectById(Integer id) {
        return sysRoleMapper.selectById(id);
    }

    public SysRole selectByName(String name) {
        return sysRoleMapper.selectByName(name);
    }
}
