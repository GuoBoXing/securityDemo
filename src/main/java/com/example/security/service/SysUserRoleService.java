package com.example.security.service;

import com.example.security.dao.SysUserRoleMapper;
import com.example.security.dto.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SysUserRoleService
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/22 14:32
 * @Version 1.0
 **/
@Service
public class SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    public List<SysUserRole> listByUserId(Integer id) {
        return sysUserRoleMapper.listByUserId(id);
    }

}
