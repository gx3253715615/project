package com.github.project.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录入参实体类
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:40
 */
@Data
public class LoginDto {
    @NotBlank(message = "用户名不能为空！")
    private String username;
    @NotBlank(message = "密码不能为空！")
    private String password;
}
