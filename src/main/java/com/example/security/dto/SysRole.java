package com.example.security.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SysRole
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/22 11:01
 * @Version 1.0
 **/
@Data
public class SysRole implements Serializable{
    static final long seriaVersionUID = 1L;

    private Integer id;

    private String name;
}
