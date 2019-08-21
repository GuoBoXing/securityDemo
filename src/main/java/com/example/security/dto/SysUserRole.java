package com.example.security.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SysUserRole
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/22 11:05
 * @Version 1.0
 **/
@Data
public class SysUserRole implements Serializable {
    static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer roleId;
}
