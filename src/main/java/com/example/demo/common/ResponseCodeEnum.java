package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tsl
 */

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {
    RESPONSE_OK(200,"成功"),
    RESPONSE_PARAM_ERROR(400,"参数错误"),
    RESPONSE_UNAUTHORIZED_ERROR(401,"权限错误"),
    RESPONSE_UNAUTHENTICATED_ERROR(402,"认证错误"),
    RESPONSE_SERVER_ERROR(500,"服务器错误");

    private Integer code;
    private String desc;
}
