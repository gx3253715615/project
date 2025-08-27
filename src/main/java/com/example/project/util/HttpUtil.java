package com.example.project.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * http工具类
 *
 * @author gaoxinyu
 * @date 2025/8/27 16:06
 */
@Component
public class HttpUtil {
    /**
     * 获取 RequestAttributes
     */
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return attributes instanceof ServletRequestAttributes ? (ServletRequestAttributes) attributes : null;
    }

    /**
     * 获取 HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attributes = getRequestAttributes();
            return attributes == null ? null : attributes.getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取客户端 IP
     */
    public static String getIp() {
        HttpServletRequest request = getRequest();
        if (Objects.isNull(request)) {
            return null;
        }
        return request.getRemoteAddr();
    }

    /**
     * 获取请求 URL
     */
    public static String getRequestUrl() {
        HttpServletRequest request = getRequest();
        if (Objects.isNull(request)) {
            return null;
        }
        return request.getRequestURL().toString();
    }

    /**
     * 获取指定请求参数
     */
    public static String getParam(String name) {
        HttpServletRequest request = getRequest();
        if (Objects.isNull(request)) {
            return null;
        }
        return request.getParameter(name);
    }
}
