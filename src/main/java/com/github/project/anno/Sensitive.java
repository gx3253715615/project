package com.github.project.anno;

import com.github.project.enums.SensitiveTypeEnum;

import java.lang.annotation.*;

/**
 * 脱敏注解
 *
 * @author gaoxinyu
 * @date 2025/9/15 18:27
 **/
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Sensitive {
    SensitiveTypeEnum type();
}
