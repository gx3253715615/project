package com.github.project.mapper;

import com.github.project.model.entity.Order;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper
 *
 * @author gaoxinyu
 * @date 2025/10/8 18:52
 **/
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
