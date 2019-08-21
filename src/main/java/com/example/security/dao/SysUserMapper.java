package com.example.security.dao;

import com.example.security.dto.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

/**
 * @ClassName SysUserMapper
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/22 11:08
 * @Version 1.0
 **/
@Mapper
@Component("sysUserMapper")
public interface SysUserMapper {
    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    SysUser selectById(Integer id);

    @Select("SELECT * FROM sys_user WHERE name = #{name}")
    SysUser selectByName(String name);
}
