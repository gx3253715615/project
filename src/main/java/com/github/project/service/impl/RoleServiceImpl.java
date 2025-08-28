package com.github.project.service.impl;


import com.github.project.mapper.RoleMapper;
import com.github.project.model.entity.Role;
import com.github.project.service.RoleService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色服务层
 *
 * @author gaoxinyu
 * @date 2025/8/27 23:37
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
