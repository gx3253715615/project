package com.github.project.service.impl;

import com.github.project.mapper.OrderMapper;
import com.github.project.model.entity.Order;
import com.github.project.service.OrderService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author gaoxinyu
 * @date 2025/10/8 18:53
 **/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}
