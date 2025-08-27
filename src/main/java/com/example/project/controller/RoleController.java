package com.example.project.controller;

import com.example.project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 角色相关接口
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:45
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
