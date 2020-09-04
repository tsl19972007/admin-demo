package com.example.demo.model.dto;

import com.example.demo.model.entity.Role;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/6/10 13:08
 * @description：dto of user in userList page
 */

@Data
@Accessors(chain = true)
public class UserPageDTO {
    //id
    private Long id;
    //用户名
    private String username;
    //姓名
    private String name;
    //包含的角色列表,键为角色id,值为角色描述
    private List<Role> roles;
    //创建人
    private String createBy;
    //创建角色
    private Date createTime;
}
