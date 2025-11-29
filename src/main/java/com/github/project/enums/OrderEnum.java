package com.github.project.enums;

/**
 * 订单状态
 *
 * @author gaoxinyu
 * @date 2025/10/8 18:52
 **/
public enum OrderEnum {
    CREATE, CONFIRM, PAID, CLOSE;

    public static OrderEnum random() {
        // 随机返回一个枚举值
        return values()[(int) (Math.random() * values().length)];
    }
}
