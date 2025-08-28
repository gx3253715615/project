package com.github.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色枚举类
 *
 * @author gaoxinyu
 * @date 2025/8/27 22:47
 **/
@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN(1, "管理员"),
    USER(2, "普通用户");

    private final Integer code;
    private final String name;

    /**
     * 根据用户的角色名返回对应的枚举值
     */
    public static RoleEnum stringToEnum(String name) {
        for (RoleEnum value : values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }

}
