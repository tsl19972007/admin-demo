package com.example.demo.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：tsl
 * @date ：Created in 2020/8/22 13:54
 * @description：
 */

@AllArgsConstructor
@Getter
public enum PermissionTypeEnum {
    MENU(1, "菜单"),
    BUTTON(2, "按钮");
    private int code;
    private String name;

    public static PermissionTypeEnum fromCode(int code) {
        for (PermissionTypeEnum value : PermissionTypeEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    public static PermissionTypeEnum fromName(String name) {
        for (PermissionTypeEnum value : PermissionTypeEnum.values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
