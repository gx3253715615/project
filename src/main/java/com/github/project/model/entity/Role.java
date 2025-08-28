package com.github.project.model.entity;

import com.github.project.handler.listener.MyInsertListener;
import com.github.project.handler.listener.MyUpdateListener;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 角色表
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:19
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "role", onInsert = MyInsertListener.class, onUpdate = MyUpdateListener.class)
public class Role extends Base {
    /**
     * 角色名
     */
    @Column(value = "role_name")
    private String roleName;

    /**
     * 是否启用 true 启用 | false 禁用
     */
    @Column(value = "enable")
    private Boolean enable;
}
