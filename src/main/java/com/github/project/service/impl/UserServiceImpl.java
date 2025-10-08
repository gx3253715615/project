package com.github.project.service.impl;

import cn.hutool.core.util.ObjectUtil;

import com.github.project.constant.RedisConst;
import com.github.project.enums.ResultEnum;
import com.github.project.handler.exception.GlobalException;
import com.github.project.mapper.UserMapper;
import com.github.project.model.dto.user.LoginDto;
import com.github.project.model.dto.user.UserAddDto;
import com.github.project.model.dto.user.UserPageDto;
import com.github.project.model.dto.user.UserUpdateDto;
import com.github.project.model.entity.Role;
import com.github.project.model.entity.User;
import com.github.project.service.RoleService;
import com.github.project.service.UserService;
import com.github.project.util.JwtUtil;
import com.github.project.util.PasswordUtil;
import com.github.project.util.RedisUtil;
import com.github.project.util.UserUtil;
import com.github.project.model.entity.table.RoleTableDef;
import com.github.project.model.entity.table.UserTableDef;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * 用户服务层
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:34
 */
@Service
public class  UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;

    /**
     * 添加用户
     *
     * @param userAddDto 用户添加数据传输对象
     * @return Boolean 操作结果（true 成功，false 失败）
     */
    @Override
    public Boolean add(UserAddDto userAddDto) {
        // 1. 查询用户是否存在
        QueryWrapper wrapper = QueryWrapper.create()
                .select(UserTableDef.USER.DEFAULT_COLUMNS)
                .where(UserTableDef.USER.USERNAME.eq(userAddDto.getUsername()));
        User user = userMapper.selectOneByQuery(wrapper);
        if (ObjectUtil.isNotNull(user)) {
            throw new GlobalException(ResultEnum.USER_EXIST);
        }

        // 2. 校验role是否存在
        QueryWrapper RoleIdQueryWrapper = QueryWrapper.create()
                .select(RoleTableDef.ROLE.DEFAULT_COLUMNS)
                .where(RoleTableDef.ROLE.ID.eq(userAddDto.getRoleId()));
        Role role = roleService.getOne(RoleIdQueryWrapper);
        if (ObjectUtil.isNull(role)) {
            throw new GlobalException(ResultEnum.ROLE_NOT_EXIST);
        }

        // 3. 保存用户
        User newUser = new User();
        newUser.setNickname(userAddDto.getNickname()); // 昵称
        newUser.setUsername(userAddDto.getUsername()); // 账号
        newUser.setPassword(PasswordUtil.encryptPassword(userAddDto.getPassword())); // 加密密码
        newUser.setRoleId(userAddDto.getRoleId()); // 角色id
        newUser.setEnable(true); // 默认启用此用户

        return save(newUser);
    }

    /**
     * 登录
     *
     * @param loginDto 登录信息
     * @return String 登录成功后返回的令牌或标识
     */
    @Override
    public String login(LoginDto loginDto) {
        // 1. 查询用户是否存在
        QueryWrapper wrapper = QueryWrapper.create()
                .select(UserTableDef.USER.DEFAULT_COLUMNS)
                .where(UserTableDef.USER.USERNAME.eq(loginDto.getUsername()));
        User user = userMapper.selectOneWithRelationsByQuery(wrapper);
        if (ObjectUtil.isNull(user)) {
            throw new GlobalException(ResultEnum.USER_NOT_EXIST);
        }
        // 2. 账号是否启用
        if (!user.getEnable()) {
            throw new GlobalException(ResultEnum.USER_NOT_ENABLE);
        }
        // 3. 密码校验
        if (!PasswordUtil.verifyPassword(loginDto.getPassword(), user.getPassword())) {
            throw new GlobalException(ResultEnum.USER_PASSWORD_ERROR);
        }
        // 4. 生成token
        String token = JwtUtil.createToken(user.getId());
        // 5. 将用户信息写入Redis
        String key = RedisConst.getUserToken(user.getId());
        RedisUtil.setObject(key, user, RedisConst.EXPIRE_TIME_SECONDS);
        return token;
    }

    /**
     * 登出
     *
     * @return Boolean 操作结果（true 成功，false 失败）
     */
    @Override
    public Boolean logout() {
        // 1. 获取用户id
        User user = UserUtil.get();
        if (ObjectUtil.isNull(user)) {
            throw new GlobalException(ResultEnum.LOGIN_EXPIRED);
        }
        Long UserUtilId = user.getId();

        // 2. 删除redis中的用户信息
        try {
            String key = RedisConst.getUserToken(UserUtilId);
            RedisUtil.del(key);
        } catch (Exception e) {
            throw new GlobalException(ResultEnum.LOGIN_EXPIRED);
        }
        return true;
    }

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     * @return User 用户实体对象
     */
    @Override
    @Cacheable(value = "userInfo:", key = "#id")
    public User getUserById(Long id) {
        // 1. 查询用户信息和关联信息
        QueryWrapper selectOneQueryWrapper = QueryWrapper.create()
                .select(UserTableDef.USER.DEFAULT_COLUMNS).where(UserTableDef.USER.ID.eq(id));
        User user = userMapper.selectOneWithRelationsByQuery(selectOneQueryWrapper);
        return user;
    }

    /**
     * 修改用户信息
     *
     * @param userUpdateDto 用户更新数据传输对象
     * @return Boolean 操作结果（true 成功，false 失败）
     */
    @Override
    public Boolean UpdateById(UserUpdateDto userUpdateDto) {
        Long userId = userUpdateDto.getId();
        User user = userMapper.selectOneById(userId);
        if (ObjectUtil.isNull(user)) {
            throw new GlobalException(ResultEnum.USER_NOT_EXIST);
        }
        // 1. 如果密码需要修改, 校验新密码
        String password = userUpdateDto.getPassword();
        if (StringUtils.isNotBlank(password)) {
            if (!PasswordUtil.isValidPassword(password)) {
                // 1.1 校验不通过
                throw new GlobalException(ResultEnum.PASSWORD_FORMAT_ERROR);
            } else {
                // 1.2 校验通过, 直接覆盖原密码
                user.setPassword(PasswordUtil.encryptPassword(password));
            }
        }
        // 2. 判断新账号是否存被占用
        if (!Objects.equals(user.getUsername(), userUpdateDto.getUsername())) {
            QueryWrapper wrapper = QueryWrapper.create()
                    .select(UserTableDef.USER.DEFAULT_COLUMNS)
                    .where(UserTableDef.USER.USERNAME.eq(userUpdateDto.getUsername()));
            User newUser = userMapper.selectOneByQuery(wrapper);
            if (ObjectUtil.isNotNull(newUser)) {
                throw new GlobalException(ResultEnum.USER_EXIST);
            }
            user.setUsername(userUpdateDto.getUsername());
        }
        // 3. 修改nickname
        if (!Objects.equals(user.getNickname(), userUpdateDto.getNickname())) {
            user.setNickname(userUpdateDto.getNickname());
        }
        // 4. 校验roleId
        if (!Objects.equals(user.getRoleId(), userUpdateDto.getRoleId())) {
            // 4.1 查询传来的roleId是否存在
            QueryWrapper RoleIdQueryWrapper = QueryWrapper.create()
                    .select(RoleTableDef.ROLE.DEFAULT_COLUMNS)
                    .where(RoleTableDef.ROLE.ID.eq(userUpdateDto.getRoleId()));
            Role role = roleService.getOne(RoleIdQueryWrapper);
            if (ObjectUtil.isNull(role)) {
                throw new GlobalException(ResultEnum.ROLE_NOT_EXIST);
            }
            user.setRoleId(userUpdateDto.getRoleId());
        }
        return updateById(user);
    }

    /**
     * 按条件分页查询
     *
     * @param userPageDto 查询条件
     * @return Page<User> 分页结果
     */
    @Override
    public Page<User> page(UserPageDto userPageDto) {
        // 1. 条件查询
        QueryWrapper listPageQueryWrapper = QueryWrapper.create()
                .select(UserTableDef.USER.DEFAULT_COLUMNS)
                .join(RoleTableDef.ROLE)
                .on(UserTableDef.USER.ROLE_ID.eq(RoleTableDef.ROLE.ID))
                .where(UserTableDef.USER.USERNAME.like(userPageDto.getUsername()))
                .and(UserTableDef.USER.ENABLE.eq(userPageDto.getEnable()))
                .and(RoleTableDef.ROLE.ENABLE.eq(userPageDto.getRoleEnable()))
                .orderBy(UserTableDef.USER.ID.desc());
        return userMapper.paginateWithRelations(userPageDto.getPageNum(), userPageDto.getPageSize(), listPageQueryWrapper);
    }


}
