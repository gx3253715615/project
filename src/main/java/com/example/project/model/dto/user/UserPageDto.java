package com.example.project.model.dto.user;

import com.example.project.model.dto.base.BasePageDto;
import lombok.Data;

/**
 * 分页查询用户接收参数实体类
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:41
 */
@Data
public class UserPageDto extends BasePageDto {
    private String username;
    private Boolean enable;
    private Boolean roleEnable;
}
