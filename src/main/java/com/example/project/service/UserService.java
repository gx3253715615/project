package com.example.project.service;


import com.example.project.model.dto.user.LoginDto;
import com.example.project.model.dto.user.UserAddDto;
import com.example.project.model.dto.user.UserPageDto;
import com.example.project.model.dto.user.UserUpdateDto;
import com.example.project.model.entity.User;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 用户服务层
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:32
 */
public interface UserService extends IService<User> {

    /**
     * 添加新用户
     *
     * @param userAddDto 用户添加数据传输对象
     * @return Boolean 操作结果（true 成功，false 失败）
     */
    Boolean add(UserAddDto userAddDto);

    /**
     * 登录
     *
     * @param logInDto 登录信息
     * @return String 登录成功后返回的令牌或标识
     */
    String login(LoginDto logInDto);

    /**
     * 登出
     *
     * @return Boolean 操作结果（true 成功，false 失败）
     */
    Boolean logout();

    /**
     * 根据用户ID获取用户信息
     *
     * @param id 用户ID
     * @return User 用户实体对象
     */
    User getUserById(Long id);

    /**
     * 更新用户信息
     *
     * @param userUpdateDto 用户更新数据传输对象
     * @return Boolean 操作结果（true 成功，false 失败）
     */
    Boolean UpdateById(UserUpdateDto userUpdateDto);

    /**
     * 分页查询用户列表
     *
     * @param userPageDto 查询条件
     * @return Page<User> 分页结果
     */
    Page<User> page(UserPageDto userPageDto);

}
