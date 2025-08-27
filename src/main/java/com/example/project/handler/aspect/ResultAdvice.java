package com.example.project.handler.aspect;

import com.alibaba.fastjson2.JSON;
import com.example.project.handler.result.PageResult;
import com.example.project.handler.result.RequestResult;
import com.mybatisflex.core.paginate.Page;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * controller层的结果统一封装
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:47
 */
@RestControllerAdvice(basePackages = "com.example.project.controller")
public class ResultAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(@NotNull MethodParameter returnType, @NotNull Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NotNull MethodParameter returnType, @NotNull MediaType selectedContentType, @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response) {

        // 1. 已包装
        if (body instanceof RequestResult || body instanceof PageResult) {
            return body;
        }

        // 2. Boolean 类型包装为 success/fail, 根据结果判断请求是否成功
        if (body instanceof Boolean) {
            return RequestResult.bool((Boolean) body);
        }

        // 3. String 类型需手动转 JSON 字符串
        if (body instanceof String) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return JSON.toJSONString(RequestResult.success(body));
        }

        // 4. 如果是Page类型包装为PageResult
        if (body instanceof Page<?>) {
            return RequestResult.success(PageResult.pageToPageResult((Page<?>) body));
        }

        // 5. 普通对象类型 默认包装为成功
        return RequestResult.success(body);
    }
}
