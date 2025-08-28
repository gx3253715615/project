package com.github.project.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 添加用户接收实体类
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:40
 */
@Data
public class UserAddDto {
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误！")
    @NotBlank(message = "账号不能为空！")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{}|;:',.<>/?`~\\\\])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{}|;:',.<>/?`~\\\\]{6,16}$", message = "密码必须为6-16位，包含大写字母、小写字母、数字和特殊字符")
    @NotBlank(message = "密码不能为空！")
    private String password;

    @NotBlank(message = "昵称不能为空！")
    private String nickname;

    @NotNull(message = "角色不能为空！")
    private Long roleId;
}
