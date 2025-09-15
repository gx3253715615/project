package com.github.project.controller;

import com.github.project.anno.RequirePermission;
import com.github.project.enums.RoleEnum;
import com.github.project.model.dto.user.LoginDto;
import com.github.project.model.dto.user.UserAddDto;
import com.github.project.model.dto.user.UserPageDto;
import com.github.project.model.dto.user.UserUpdateDto;
import com.github.project.model.entity.User;
import com.github.project.service.UserService;
import com.mybatisflex.core.paginate.Page;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理接口
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:51
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public User test() {
        User user = new User();
        user.setUsername("gaoxinyu");
        user.setPhone("19100000032");
        user.setIdCard("130000000000000012");
        return user;
    }

    /**
     * 添加用户信息
     *
     * @param userAddDto 用户注册信息，包括账号、密码、昵称和角色ID
     * @return 添加成功返回 true，否则返回 false
     */
    @PostMapping("/add")
    @RequirePermission(RoleEnum.ADMIN)
    public Boolean add(@RequestBody @Validated UserAddDto userAddDto) {
        return userService.add(userAddDto);
    }

    /**
     * 登录
     *
     * @param loginDto 包含用户名和密码的登录信息
     * @return 登录成功返回 token，失败抛出异常或返回 null
     */
    @PostMapping("/login")
    public String login(@RequestBody @Validated LoginDto loginDto) {
        return userService.login(loginDto);
    }

    /**
     * 登出
     *
     * @return 登出成功返回 true，否则返回 false
     */
    @DeleteMapping("/logout")
    public Boolean logout() {
        return userService.logout();
    }

    /**
     * 根据ID查询用户信息
     *
     * @param id 要查询的用户 ID
     * @return 返回对应的用户对象，若不存在则为 null
     */
    @GetMapping("/getById/{id}")
    public User getById(@PathVariable @NotNull(message = "请输入用户id！") Long id) {
        return userService.getUserById(id);
    }

    /**
     * 编辑用户信息
     *
     * @param userUpdateDto 包含更新信息的 DTO 对象，如昵称、角色等
     * @return 更新成功返回 true，否则返回 false
     */
    @PutMapping("/update")
    @RequirePermission(RoleEnum.ADMIN)
    public Boolean update(@RequestBody @Validated UserUpdateDto userUpdateDto) {
        return userService.UpdateById(userUpdateDto);
    }

    /**
     * 条件分页查询
     *
     * @param userPageDto 查询条件，例如用户名、启用状态等
     * @return 返回包含分页数据的结果对象
     */
    @PostMapping("/page")
    @RequirePermission(RoleEnum.ADMIN)
    public Page<User> page(@RequestBody @Validated UserPageDto userPageDto) {
        return userService.page(userPageDto);
    }

    /**
     * 删除用户
     *
     * @param id 要删除的用户 ID
     * @return 删除成功返回 true，否则返回 false
     */
    @DeleteMapping("/delete/{id}")
    @RequirePermission(RoleEnum.ADMIN)
    public Boolean delete(@PathVariable @NotNull(message = "请输入用户id！") Long id) {
        return userService.removeById(id);
    }

}
