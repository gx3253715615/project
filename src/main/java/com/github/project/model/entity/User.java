package com.github.project.model.entity;

import com.github.project.handler.listener.MyInsertListener;
import com.github.project.handler.listener.MyUpdateListener;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.RelationManyToOne;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户实体类
 *
 * @author gaoxinyu
 * @date 2025/8/27 16:12
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "user", onInsert = MyInsertListener.class, onUpdate = MyUpdateListener.class)
public class User extends Base {

    /**
     * 用户名
     */
    @Column(value = "username")
    private String username;

    /**
     * 密码
     */
    @Column(value = "password")
    private String password;

    /**
     * 昵称
     */
    @Column(value = "nickname")
    private String nickname;

    /**
     * 是否启用 true 启用 | false 禁用
     */
    @Column(value = "enable")
    private Boolean enable;

    /**
     * 角色ID
     */
    @Column(value = "role_id")
    private Long roleId;

    /**
     * 角色名
     */
    @Column(ignore = true)
    @RelationManyToOne(
            selfField = "roleId",
            targetTable = "role",
            targetField = "id",
            valueField = "roleName")
    private String roleName;

    /**
     * 角色是否启用 true 启用 | false 禁用
     */
    @Column(ignore = true)
    @RelationManyToOne(
            selfField = "roleId",
            targetTable = "role",
            targetField = "id",
            valueField = "enable")
    private Boolean roleEnable;
}
