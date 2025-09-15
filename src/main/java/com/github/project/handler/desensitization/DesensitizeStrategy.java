package com.github.project.handler.desensitization;

/**
 * 脱敏策略接口
 *
 * @author gaoxinyu
 * @date 2025/9/15 19:38
 **/
public interface DesensitizeStrategy {
    String desensitize(String value);
}

class UsernameDesensitizeStrategy implements DesensitizeStrategy {
    @Override
    public String desensitize(String value) {
        if (value != null && value.length() > 3) {
            return value.substring(0, 3) + "****";
        }
        return value;
    }
}

class PhoneDesensitizeStrategy implements DesensitizeStrategy {
    @Override
    public String desensitize(String value) {
        if (value != null && value.length() == 11) {
            return value.substring(0, 3) + "****" + value.substring(value.length() - 4);
        }
        return value;
    }
}

class IdCardDesensitizeStrategy implements DesensitizeStrategy {
    @Override
    public String desensitize(String value) {
        if (value != null && value.length() == 18) {
            return value.substring(0, 4) + "**********" + value.substring(value.length() - 4);
        }
        return value;
    }
}


