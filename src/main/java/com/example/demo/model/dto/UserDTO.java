package com.example.demo.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/6/10 20:06
 * @description：用于用户新建和更新
 */

@Data
public class UserDTO {
    //更新
    private Long id;
    //新建，更新
    private String name;
    //新建
    private String username;
    //无
    private String password;
    //无
    private String salt;
    //无
    private String createBy;
    //无
    private Date createTime;
    //新建，更新
    private List<Long> roleIds;
}
