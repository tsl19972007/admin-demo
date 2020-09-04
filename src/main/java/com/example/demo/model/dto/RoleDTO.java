package com.example.demo.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/8/17 17:23
 * @description：用于角色创建和更新
 */

@Data
public class RoleDTO {
    //更新
    private Long id;
    //新建
    private String name;
    //新建，更新
    private String desc;
    //无
    private String createBy;
    //无
    private Date createTime;
    //新建，更新
    private List<Long> permissionIds;
}
