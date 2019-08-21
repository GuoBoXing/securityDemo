package com.example.security.service;

import com.example.security.dao.SysUserMapper;
import com.example.security.dto.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysUserService
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/22 11:30
 * @Version 1.0
 **/
@Service
public class SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    public SysUser selectById(Integer id) {
        return sysUserMapper.selectById(id);
    }

    public SysUser selectByName(String name) {
        return sysUserMapper.selectByName(name);
    }
}
