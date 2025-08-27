package com.example.project.handler.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.project.anno.RequirePermission;
import com.example.project.constant.RedisConst;
import com.example.project.enums.ResultEnum;
import com.example.project.enums.RoleEnum;
import com.example.project.handler.exception.GlobalException;
import com.example.project.model.entity.User;
import com.example.project.util.JwtUtil;
import com.example.project.util.RedisUtil;
import com.example.project.util.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * Token校验拦截器
 *
 * @author gaoxinyu
 * @date 2025/8/27 16:31
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        // 1. 获取请求头中的 token
        String token = request.getHeader("token");
        // 2. 判断 token 是否为空
        if (StrUtil.isBlank(token)) {
            throw new GlobalException(ResultEnum.NO_LOGIN);
        }
        // 3. 判断token是否有效
        if (!JwtUtil.isTokenValid(token)) {
            throw new GlobalException(ResultEnum.BAD_TOKEN);
        }
        // 4. 判断 token 是否过期
        if (JwtUtil.isTokenExpired(token)) {
            throw new GlobalException(ResultEnum.LOGIN_EXPIRED);
        }
        // 5. 解析 token
        Long userId = JwtUtil.parseToken(token);
        if (ObjectUtil.isNull(userId)) {
            throw new GlobalException(ResultEnum.BAD_TOKEN);
        }
        // 6. 取出用户信息放到UserUtil
        User user = RedisUtil.getObject(RedisConst.getUserToken(userId), User.class);
        // 如果redis中数据过期，则返回登录过期
        if (ObjectUtil.isNull(user)) {
            throw new GlobalException(ResultEnum.LOGIN_EXPIRED);
        }
        UserUtil.set(user);

        // 接口权限控制, 防止普通用户访问管理员接口
        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(RequirePermission.class)) {
                RequirePermission requirePermission = method.getDeclaredAnnotation(RequirePermission.class);
                RoleEnum roleEnum = requirePermission.value();
                // 判断是否需要管理员身份
                if (roleEnum == RoleEnum.ADMIN) {
                    // 如果当前用户不是超级管理员
                    RoleEnum curRole = RoleEnum.stringToEnum(user.getRoleName());
                    if (RoleEnum.ADMIN != curRole) {
                        throw new GlobalException(ResultEnum.NO_PERMISSION);
                    }
                }
            }
        }

        return true;
    }

    /**
     * 清除ThreadLocal
     */
    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        UserUtil.remove();
    }
}
