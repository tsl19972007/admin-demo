package com.example.demo.config.shiro;

import com.example.demo.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author ：tsl
 * @date ：Created in 2020/8/28 17:20
 * @description ：shiro principal
 */

@Data
public class UserDetail {
    private User user;
    private List<String> roles;
    private List<String> permissions;
}
