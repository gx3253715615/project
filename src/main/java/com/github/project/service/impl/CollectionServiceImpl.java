package com.github.project.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.github.project.model.entity.Collection;
import com.github.project.mapper.CollectionMapper;
import com.github.project.service.CollectionService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author gaoxinyu
 * @date 2025/09/02 14:46
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection>  implements CollectionService{

}
