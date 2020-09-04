package com.example.demo.model.dto;

import com.example.demo.model.entity.Permission;
import com.example.demo.model.entity.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/8/18 20:48
 * @description：dto of role in roleList page
 */

@Data
@Accessors(chain = true)
public class RolePageDTO {
    //id
    private Long id;
    //角色名
    private String name;
    //角色描述
    private String desc;
    //包含的角色列表,键为角色id,值为角色描述
    private List<Permission> permissions;
    //创建人
    private String createBy;
    //创建角色
    private Date createTime;
}
