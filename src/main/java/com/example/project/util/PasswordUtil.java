package com.example.project.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;

/**
 * 加密密码和校验密码的工具类
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:44
 */
public class PasswordUtil {

    private static final String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{}|;:',.<>/?`~\\\\])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{}|;:',.<>/?`~\\\\]{6,16}$";
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 1000;

    /**
     * 校验密码格式,  返回true表示格式正确，false表示格式错误
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.matches(regex);
    }

    /**
     * 生成带盐的加密密码
     */
    public static String encryptPassword(String password) {
        // 生成随机盐, 防止每个用户盐相同
        String salt = RandomUtil.randomString(SALT_LENGTH);
        // 多次迭代SHA256加密
        String hash = hashWithSalt(password, salt);
        return salt + ":" + hash;
    }

    /**
     * 校验输入密码是否和数据库加密密码匹配
     */
    public static boolean verifyPassword(String password, String encryptedDbPassword) {
        if (encryptedDbPassword == null || !encryptedDbPassword.contains(":")) {
            return false;
        }
        String[] parts = encryptedDbPassword.split(":");
        if (parts.length != 2) {
            return false;
        }
        String salt = parts[0];
        String hash = parts[1];

        // 计算输入密码的hash
        String inputHash = hashWithSalt(password, salt);

        return inputHash.equals(hash);
    }

    private static String hashWithSalt(String password, String salt) {
        String result = password + salt;
        for (int i = 0; i < ITERATIONS; i++) {
            result = DigestUtil.sha256Hex(result);
        }
        return result;
    }
}
