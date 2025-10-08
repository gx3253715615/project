package com.github.project.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 本地缓存配置类
 *
 * @author gaoxinyu
 * @date 2025/10/8 17:57
 **/
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(100)  // 最大缓存数量
                .expireAfterAccess(java.time.Duration.ofMinutes(10))  // 缓存过期时间
                .weakKeys());  // 使用弱引用作为缓存键
        return cacheManager;
    }
}
