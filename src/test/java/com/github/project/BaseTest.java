package com.github.project;

import com.github.project.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用于测试时启动容器
 *
 * @author gaoxinyu
 * @date 2025/8/27 15:00
 **/
@SpringBootTest
public class BaseTest {

    @Mock
    private RoleMapper roleMapper;


}
