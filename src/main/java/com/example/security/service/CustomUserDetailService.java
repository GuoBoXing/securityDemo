package com.example.security.service;

import com.example.security.dto.SysRole;
import com.example.security.dto.SysUser;
import com.example.security.dto.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @ClassName UserDetailService
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/22 14:59
 * @Version 1.0
 **/
@Service("userDetailsService")
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        // 从数据库中获取信息
        SysUser sysUser = userService.selectByName(userName);

        // 判断用户名是否存在
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        // 添加权限
        List<SysUserRole> userRoles = userRoleService.listByUserId(sysUser.getId());
        for (SysUserRole userRole:userRoles) {
            SysRole sysRole = roleService.selectById(userRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(sysRole.getName()));
        }
        // 返回UserDetail的实现类
        return new User(sysUser.getName(),sysUser.getPassword(),authorities);
    }
}
