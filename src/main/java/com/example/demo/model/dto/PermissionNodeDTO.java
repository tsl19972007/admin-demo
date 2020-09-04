package com.example.demo.model.dto;

import com.example.demo.model.entity.Permission;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/8/19 13:25
 * @description：permission node of tree in permission page
 */

@Data
@Accessors(chain = true)
public class PermissionNodeDTO {
    //id
    private Long id;
    //权限名
    private String name;
    //权限描述
    private String desc;
    //权限类型
    private Integer type;
    //创建人
    private String createBy;
    //创建角色
    private Date createTime;
    //子权限
    private List<PermissionNodeDTO> children;

    public PermissionNodeDTO(Permission p) {
        this.id = p.getId();
        this.name = p.getName();
        this.desc = p.getDesc();
        this.type = p.getType();
        this.createBy = p.getCreateBy();
        this.createTime = p.getCreateTime();
    }
}
