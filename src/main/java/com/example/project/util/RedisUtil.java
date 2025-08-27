package com.example.project.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 * @author gaoxinyu
 * @date 2025/8/27 22:28
 **/

@Component
public class RedisUtil {

    private static final long DEFAULT_EXPIRE_SECONDS = 60L;

    private static StringRedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * 使用当前线程ID加锁
     *
     * @param key           锁的Key
     * @param expireSeconds 过期时间（秒）
     * @return 当前线程ID（作为锁的value），加锁失败返回 null
     */
    public static Boolean tryLock(String key, long expireSeconds) {
        String threadId = String.valueOf(Thread.currentThread().getId());
        return redisTemplate.opsForValue().setIfAbsent(key, threadId, expireSeconds, TimeUnit.SECONDS);
    }

    /**
     * 释放锁（只有当前线程ID加的锁才会释放）
     *
     * @param key 锁的Key
     */
    public static void unlock(String key) {
        String threadId = String.valueOf(Thread.currentThread().getId());
        String luaScript = """
                if redis.call('get', KEYS[1]) == ARGV[1] then
                    return redis.call('del', KEYS[1])
                else
                    return 0
                end
                """;
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(luaScript);
        script.setResultType(Long.class);

        redisTemplate.execute(script, Collections.singletonList(key), threadId);
    }

    /**
     * 修改缓存失效的时间
     *
     * @param key:  key
     * @param time: 时间(秒)
     * @return boolean : true 成功 false 失败
     */
    public static boolean modifyExpire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取缓存的失效时间
     *
     * @param key: key
     * @return Long 失效时间(秒)
     */
    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断缓存中是否有对应的key
     *
     * @param key: key
     * @return boolean true 存在 false 不存在
     */
    public static boolean hasKey(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    /**
     * 删除缓存
     *
     * @param key: key[]
     */
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * 新增缓存(String)
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean setString(String key, String value) {
        return setString(key, value, DEFAULT_EXPIRE_SECONDS);
    }

    /**
     * 新增缓存(String)
     *
     * @param key           键
     * @param value         值
     * @param expireSeconds 过期时间（秒）
     * @return true成功 false失败
     */
    public static boolean setString(String key, String value, long expireSeconds) {
        try {
            redisTemplate.opsForValue().set(key, value, expireSeconds, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取缓存(String)
     *
     * @param key: key
     * @return String 返回值
     */
    public static String getString(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 序列化对象并保存到 Redis
     *
     * @param key   Redis键
     * @param value 要保存的对象
     * @return true 如果成功保存，false 如果失败
     */
    public static boolean setObject(String key, Object value) {
        return setObject(key, value, DEFAULT_EXPIRE_SECONDS);
    }

    /**
     * 序列化对象并保存到 Redis
     *
     * @param key           Redis键
     * @param value         要保存的对象
     * @param expireSeconds 过期时间（秒）
     * @return true 如果成功保存，false 如果失败
     */
    public static boolean setObject(String key, Object value, long expireSeconds) {
        try {
            // 将对象序列化为JSON字符串
            String json = JSON.toJSONString(value);
            // 保存到Redis中
            redisTemplate.opsForValue().set(key, json, expireSeconds, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从 Redis 中获取对象并反序列化
     *
     * @param key   Redis键
     * @param clazz 对象的类型
     * @param <T>   返回对象的类型
     * @return T 返回反序列化后的对象
     */
    public static <T> T getObject(String key, Class<T> clazz) {
        try {
            // 从 Redis 获取 JSON 字符串
            String json = redisTemplate.opsForValue().get(key);
            if (json != null) {
                // 将 JSON 字符串反序列化为 Java 对象
                return JSON.parseObject(json, clazz);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 从 Redis 中获取对象列表并反序列化
     *
     * @param key   Redis键
     * @param clazz 列表中的对象类型
     * @param <T>   返回对象列表的类型
     * @return List<T> 返回反序列化后的对象列表
     */
    public static <T> List<T> getObjectList(String key, Class<T> clazz) {
        try {
            // 从 Redis 获取 JSON 字符串
            String json = redisTemplate.opsForValue().get(key);
            if (json != null) {
                // 使用 TypeReference 反序列化为对象列表, 这里为了防止泛型擦除，使用 TypeReference
                return JSON.parseObject(json, new TypeReference<List<T>>() {
                });
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
