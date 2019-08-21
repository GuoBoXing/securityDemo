package com.example.security.dao;

import com.example.security.dto.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.security.Permission;
import java.util.List;

/**
 * @ClassName SysPermissionMapper
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/24 9:36
 * @Version 1.0
 **/
@Mapper
@Component("sysPermissionMapper")
public interface SysPermissionMapper {
    @Select("SELECT * FROM sys_permission WHERE role_id=#{roleId}")
    List<SysPermission> listByRoleId(Integer roleId);
}
