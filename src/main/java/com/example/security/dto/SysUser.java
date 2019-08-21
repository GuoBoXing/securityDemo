package com.example.security.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SysUser
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/22 10:59
 * @Version 1.0
 **/
@Data
public class SysUser implements Serializable {
    static final long seriaVersionUID = 1L;

    private Integer id;

    private String name;

    private String password;
}
