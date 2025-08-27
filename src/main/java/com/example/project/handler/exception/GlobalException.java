package com.example.project.handler.exception;

import com.example.project.enums.ResultEnum;
import lombok.Data;

/**
 * 自定义全局业务异常
 *
 * @author gaoxinyu
 * @date 2025/8/27 16:35
 */
@Data
public class GlobalException extends RuntimeException {

    private final Integer code;

    /**
     * 根据枚举构造全局异常
     */
    public GlobalException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    /**
     * 根据code和msg构造全局异常
     */
    public GlobalException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * 自定义错误信息
     */
    public GlobalException(String message) {
        super(message);
        this.code = ResultEnum.FAIL.getCode();
    }
}
