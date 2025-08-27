package com.example.project.model.dto.base;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 分页参数基础类
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:39
 */
@Data
public class BasePageDto {
    @NotNull
    @Min(value = 1, message = "页码必须大于等于 1")
    private Integer pageNum;

    @NotNull
    @Min(value = 10, message = "每页条数必须大于等于 10")
    @Max(value = 100, message = "每页条数不能超过 100")
    private Integer pageSize;
}
