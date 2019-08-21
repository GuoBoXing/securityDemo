package com.example.security.evaluator;

import com.example.security.dao.SysRoleMapper;
import com.example.security.dto.SysPermission;
import com.example.security.dto.SysUserRole;
import com.example.security.service.SysPermissionService;
import com.example.security.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @ClassName CustomPermissionEvaluator
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/24 14:09
 * @Version 1.0
 **/
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private SysRoleService roleService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {

        // 获取loadUserByUserName（）方法的结果
        User user = (User)authentication.getPrincipal();
        // 获得loadUserByUserName（）中注入的角色
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        // 遍历用户所有角色
        for (GrantedAuthority authority: authorities) {
            String roleName = authority.getAuthority();
            Integer roleId = roleService.selectByName(roleName).getId();
            // 得到角色所有的权限
            List<SysPermission> permissionList = permissionService.listByRoleId(roleId);

            // 遍历permissionList
            for (SysPermission sysPermission: permissionList) {
                // 获取权限集
                List permissions = sysPermission.getPermissions();
                // 如果访问的url和权限用户合法的话，返回true
                if (targetUrl.equals(sysPermission.getUrl()) && permissions.contains(targetPermission)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
