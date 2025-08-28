package com.github.project.config;

import com.github.project.handler.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局配置
 *
 * @author gaoxinyu
 * @date 2025/8/27 16:29
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${enable.login}")
    private Boolean enableLogin;

    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (enableLogin) {
            // 添加拦截器 **为嵌套路径
            registry.addInterceptor(tokenInterceptor)
                    .addPathPatterns("/**")
                    .excludePathPatterns("/user/login")
                    .excludePathPatterns("/favicon.ico");
        }
    }

    // TODO 后端跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
