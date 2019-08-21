package com.example.security.dao;

import com.example.security.dto.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @ClassName SysRoleMapper
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/22 11:15
 * @Version 1.0
 **/
@Mapper
@Component("sysRoleMapper")
public interface SysRoleMapper {
    @Select("SELECT * FROM sys_role WHERE id = #{id}")
    SysRole selectById(Integer id);
    @Select("SELECT * FROM sys_role WHERE name = #{name}")
    SysRole selectByName(String name);
}
