package com.github.project.anno;

import com.github.project.enums.RoleEnum;

import java.lang.annotation.*;

/**
 * 接口权限校验注解
 *
 * @author gaoxinyu
 * @date 2025/8/27 22:47
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {
    RoleEnum value() default RoleEnum.USER; // 枚举类型参数
}
