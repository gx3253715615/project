package com.github.project.handler.desensitization;

import com.github.project.enums.SensitiveTypeEnum;

/**
 * 脱敏策略工厂
 *
 * @author gaoxinyu
 * @date 2025/9/15 19:41
 **/
public class DesensitizeStrategyFactory {
    public static DesensitizeStrategy getStrategy(SensitiveTypeEnum type) {
        return switch (type) {
            case IDCARD -> new IdCardDesensitizeStrategy();
            case PHONE -> new PhoneDesensitizeStrategy();
            case USERNAME -> new UsernameDesensitizeStrategy();
            default -> throw new UnsupportedOperationException("不支持的脱敏类型");
        };
    }
}
