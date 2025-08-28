package com.github.project.handler.exception;

import com.github.project.handler.result.RequestResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

/**
 * 参数校验异常处理器
 *
 * @author gaoxinyu
 * @date 2025/8/27 17:08
 */
@Order(0) // 设置优先级，确保该异常处理器在全局异常处理器之前执行
@Slf4j
@RestControllerAdvice
public class GlobalHibernateValidatorExceptionHandler {

    /**
     * 校验对象参数统一异常处理 (POST请求)
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class})
    public RequestResult<Void> handleBindException(HttpServletRequest request, BindException ex) {
        String requestURI = request.getRequestURI();

        // 收集错误信息
        String errorMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("字段 [%s] %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining("; "));

        // 记录详细日志
        log.error("请求 {} 时参数校验异常: {}", requestURI, errorMsg);

        // 返回用户友好的失败响应
        return RequestResult.fail("参数校验失败: " + errorMsg);
    }

    /**
     * 校验单个参数统一异常处理 (GET请求)
     */
    @ExceptionHandler({ConstraintViolationException.class, HttpMessageNotReadableException.class})
    public RequestResult<Void> handleConstraintViolationException(HttpServletRequest request, Exception ex) {
        String requestURI = request.getRequestURI();

        // 记录详细日志
        log.error("请求 {} 时参数校验异常: {}", requestURI, ex.getMessage());

        // 处理HttpMessageNotReadableException (枚举类转换错误)
        if (ex instanceof HttpMessageNotReadableException httpEx) {
            Throwable cause = httpEx.getCause();
            if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException formatEx) {
                // 判断是否是枚举类转换错误
                Class<?> targetType = formatEx.getTargetType();
                if (targetType.isEnum()) {
                    String errorMsg = String.format("参数格式错误：无效的枚举值 '%s' 对应类型 '%s'", formatEx.getValue(), targetType.getSimpleName());
                    return RequestResult.fail(errorMsg);
                }
            }
        }

        // 处理ConstraintViolationException
        if (ex instanceof ConstraintViolationException) {
            String errorMsg = ((ConstraintViolationException) ex).getConstraintViolations().stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining("; "));
            return RequestResult.fail("参数校验失败: " + errorMsg);
        }

        // 默认返回异常信息
        return RequestResult.fail(ex.getMessage().substring(ex.getMessage().indexOf(":") + 1));
    }
}
