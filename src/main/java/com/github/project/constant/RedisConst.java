package com.github.project.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Redis常量类
 *
 * @author gaoxinyu
 * @date 2025/8/27 22:31
 */
@Getter
@AllArgsConstructor
public class RedisConst {
    public static final String LOCK = "lock";
    public static final String USER = "user";
    public static final String TOKEN = "token";

    /**
     * redis中用户token过期时间, 7天, 毫秒
     */
    public static final long EXPIRE_TIME_MILLIS = 7 * 24 * 60 * 60 * 1000;
    /**
     * redis中用户token过期时间, 7天, 秒
     */
    public static final long EXPIRE_TIME_SECONDS = 7 * 24 * 60 * 60;
    /**
     * 获取用户登录token  user:token:1
     */
    public static String getUserToken(Long userId) {
        return USER + ":" + TOKEN + ":" + userId;
    }
}
