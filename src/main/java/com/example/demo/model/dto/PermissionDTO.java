package com.example.demo.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author ：tsl
 * @date ：Created in 2020/8/19 0:54
 * @description：用于权限更新或修改
 */

@Data
public class PermissionDTO {
    //更新
    private Long id;
    //新建
    private Integer type;
    //新建
    private String name;
    //新建，更新
    private String desc;
    //新建
    private Long parentId;
    //无
    private String createBy;
    //无
    private Date createTime;
}
