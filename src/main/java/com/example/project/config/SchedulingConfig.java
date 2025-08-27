package com.example.project.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务配置
 *
 * @author gaoxinyu
 * @date 2025/8/27 16:26
 **/
@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "enable.scheduling", havingValue = "true", matchIfMissing = false)
public class SchedulingConfig {
}

