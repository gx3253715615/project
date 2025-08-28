package com.github.project.handler.exception;

import com.github.project.enums.ResultEnum;
import com.github.project.handler.result.RequestResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理器
 *
 * @author gaoxinyu
 * @date 2025/8/27 16:46
 **/
@Order(1)
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理业务异常 GlobalException
     */
    @ExceptionHandler(GlobalException.class)
    public RequestResult<Void> handleGlobalException(HttpServletRequest request, GlobalException ex) {
        log.warn("请求 {} 时发生业务异常：{}", request.getRequestURI(), ex.getMessage(), ex);
        return RequestResult.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 捕获系统异常, 空指针, SQL异常等
     */
    @ExceptionHandler(Exception.class)
    public RequestResult<Void> handleSystemException(HttpServletRequest request, Exception ex) {
        log.error("请求 {} 时系统异常：{}", request.getRequestURI(), ex.getMessage(), ex);
        // 如果你希望告警，这里可以接入钉钉、飞书、Prometheus等
        return RequestResult.fail(ResultEnum.SYSTEM_ERROR);
    }

    /**
     * 处理 404 类异常
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public RequestResult<Void> noResourceFoundExceptionHandler(HttpServletRequest request, NoResourceFoundException ex) {
        log.error("请求 {} 时异常:", request.getRequestURI(), ex);
        return RequestResult.fail(ResultEnum.NO_RESOURCE_FOUND);
    }
}
