package com.example.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义异常结果枚举
 *
 * @author gaoxinyu
 * @date 2025/8/27 16:38
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(200, "请求成功!"),
    /**
     * 失败
     */
    FAIL(400, "请求失败!"),
    /**
     * 系统错误！请稍后再试！
     */
    SYSTEM_ERROR(500, "系统错误！请稍后再试！"),
    /**
     * 找不到资源！
     */
    NO_RESOURCE_FOUND(404, "找不到资源！"),
    /**
     * 用户已存在！
     */
    USER_EXIST(400, "用户已存在！"),
    /**
     * 用户不存在！
     */
    USER_NOT_EXIST(400, "用户不存在！"),
    /**
     * 用户密码错误！
     */
    USER_PASSWORD_ERROR(400, "用户密码错误！"),
    /**
     * 用户未启用
     */
    USER_NOT_ENABLE(400, "用户未启用！"),

    // 登录相关
    /**
     * 未登录！
     */
    NO_LOGIN(401, "未登录！"),
    /**
     * 登录已过期！请重新登录！
     */
    LOGIN_EXPIRED(401, "登录已过期！请重新登录！"),
    /**
     * 账号或密码错误！
     */
    ACCOUNT_OR_PASSWORD_ERROR(400, "账号或密码错误！"),
    /**
     * 认证错误！请重新登录！
     */
    BAD_TOKEN(403, "认证错误！请重新登录！"),
    /**
     * 密码格式错误！
     */
    PASSWORD_FORMAT_ERROR(400, "密码格式不正确"),

    // 角色相关
    /**
     * 角色不存在！
     */
    ROLE_NOT_EXIST(400, "角色不存在！"),

    /**
     * 角色已经存在！
     */
    ROLE_ALREADY_EXIST(400, "角色已经存在！"),

    // 权限相关
    /**
     * 无权限
     */
    NO_PERMISSION(403, "无权限!"),

    // 文件
    /**
     * 文件上传失败
     */
    FILE_UPLOAD_FAILED(500, "文件上传失败!"),

    /**
     * 文件下载失败
     */
    FILE_DOWNLOAD_FAILED(500, "文件下载失败!"),

    /**
     * 图片不存在
     */
    IMAGE_NOT_EXIST(404, "图片不存在!"),

    /**
     * 获取文件失败
     */
    FILE_GET_FAILED(500, "获取文件失败!"),

    /**
     * 获取文件大小失败
     */
    FILE_GET_SIZE_FAILED(500, "获取文件大小失败!"),

    // Excel
    /**
     * 请上传Excel文件!
     */
    EXCEL_FILE_ERROR(400, "请上传Excel文件!"),

    /**
     * Excel至少应包含表头和一行数据
     */
    EXCEL_FILE_CONTENT_ERROR(400, "Excel至少应包含表头和一行数据"),

    /**
     * 读取Excel失败
     */
    EXCEL_READ_FAILED(500, "读取Excel失败");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 状态信息
     */
    private final String msg;

}
