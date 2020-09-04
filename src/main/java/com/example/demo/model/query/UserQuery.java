package com.example.demo.model.query;

import lombok.*;

/**
 * @author ：tsl
 * @date ：Created in 2020/6/9 14:09
 * @description：query parameters of userList
 */

@Data
public class UserQuery extends PageQuery{
    private String name;
}
