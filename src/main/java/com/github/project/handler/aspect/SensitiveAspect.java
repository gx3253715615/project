package com.github.project.handler.aspect;

import java.lang.reflect.Field;

import com.github.project.handler.desensitization.DesensitizeStrategy;
import com.github.project.handler.desensitization.DesensitizeStrategyFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import com.github.project.anno.Sensitive;
import org.springframework.stereotype.Component;

/**
 * 脱敏aop处理
 *
 * @author gaoxinyu
 * @date 2025/9/15 18:30
 **/
@Aspect
@Component
public class SensitiveAspect {

    @AfterReturning(value = "execution(* com.github.project.controller.*.*(..))", returning = "result")
    public Object doAfterReturning(Object result) throws IllegalAccessException {
        if (result != null) {
            Field[] fields = result.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Sensitive.class)) {
                    // 获取值
                    field.setAccessible(true);
                    String value = (String) field.get(result);
                    if (value == null) continue; // 如果值为空，则跳过, 提高性能
                    // 获取工厂
                    Sensitive annotation = field.getAnnotation(Sensitive.class);
                    DesensitizeStrategy strategy = DesensitizeStrategyFactory.getStrategy(annotation.type());
                    field.set(result, strategy.desensitize(value));
                }
            }

        }
        return result;
    }

    //private String doFieldSensitive(String value) {
    //    if (value == null) {
    //        return null;
    //    }
    //    StringBuilder sb = new StringBuilder();
    //    for (int i = 0; i < value.length() - 2; i++) {
    //        sb.append("*");
    //    }
    //    return value.substring(0, 1) + sb.toString() + value.substring(value.length() - 1);
    //}

}
