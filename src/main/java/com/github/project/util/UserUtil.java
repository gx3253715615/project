package com.github.project.util;

import com.github.project.model.entity.User;

/**
 * 当前用户工具类
 *
 * @author gaoxinyu
 * @date 2025/8/27 15:46
 */
public class UserUtil {
    private static final ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void set(User user) {
        threadLocal.set(user);
    }

    public static User get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}

