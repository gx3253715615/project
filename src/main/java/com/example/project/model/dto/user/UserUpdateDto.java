package com.example.project.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 更新用户接收参数实体类
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:41
 */
@Data
public class UserUpdateDto {
    @NotNull(message = "主键不能为空！请输入！")
    private Long id;

    @NotNull(message = "角色不能为空！")
    private Long roleId;

    @NotBlank(message = "昵称不能为空！")
    private String nickname;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误！")
    @NotBlank(message = "账号不能为空！")
    private String username;
    private String password;
}
