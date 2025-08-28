package com.github.project.handler.result;

import com.github.project.enums.ResultEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 请求结果封装类
 *
 * @author gaoxinyu
 * @date 2025/8/27 16:49
 */
@Data
@Accessors(chain = true)
public class RequestResult<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 请求成功
     */
    public static RequestResult<Void> success() {
        RequestResult<Void> requestResult = new RequestResult<>();
        requestResult.setCode(ResultEnum.SUCCESS.getCode());
        requestResult.setMessage(ResultEnum.SUCCESS.getMsg());
        requestResult.setData(null);
        return requestResult;
    }

    /**
     * 请求成功
     */
    public static <T> RequestResult<T> success(T data) {
        RequestResult<T> requestResult = new RequestResult<>();
        requestResult.setCode(ResultEnum.SUCCESS.getCode());
        requestResult.setMessage(ResultEnum.SUCCESS.getMsg());
        requestResult.setData(data);
        return requestResult;
    }

    /**
     * 请求失败
     */
    public static RequestResult<Void> fail() {
        RequestResult<Void> requestResult = new RequestResult<>();
        requestResult.setCode(ResultEnum.FAIL.getCode());
        requestResult.setMessage(ResultEnum.FAIL.getMsg());
        requestResult.setData(null);
        return requestResult;
    }

    /**
     * 请求失败
     */
    public static <T> RequestResult<T> fail(T data) {
        RequestResult<T> requestResult = new RequestResult<>();
        requestResult.setCode(ResultEnum.FAIL.getCode());
        requestResult.setMessage(ResultEnum.FAIL.getMsg());
        requestResult.setData(data);
        return requestResult;
    }

    /**
     * 请求失败
     */
    public static RequestResult<Void> fail(String msg) {
        RequestResult<Void> requestResult = new RequestResult<>();
        requestResult.setCode(ResultEnum.FAIL.getCode());
        requestResult.setMessage(msg);
        requestResult.setData(null);
        return requestResult;
    }

    /**
     * 请求失败
     */
    public static RequestResult<Void> fail(Integer code, String msg) {
        RequestResult<Void> requestResult = new RequestResult<>();
        requestResult.setCode(code);
        requestResult.setMessage(msg);
        requestResult.setData(null);
        return requestResult;
    }

    /**
     * 请求失败
     */
    public static RequestResult<Void> fail(ResultEnum re) {
        RequestResult<Void> requestResult = new RequestResult<>();
        requestResult.setCode(re.getCode());
        requestResult.setMessage(re.getMsg());
        requestResult.setData(null);
        return requestResult;
    }

    /**
     * 返回的数据是boolean类型, 比如插入或更新失败, 成功就返回200, 失败就返回400
     */
    public static RequestResult<Boolean> bool(boolean data) {
        RequestResult<Boolean> requestResult = new RequestResult<>();
        requestResult.setCode(data ? ResultEnum.SUCCESS.getCode() : ResultEnum.FAIL.getCode());
        requestResult.setMessage(data ? ResultEnum.SUCCESS.getMsg() : ResultEnum.FAIL.getMsg());
        requestResult.setData(data);
        return requestResult;
    }
}
