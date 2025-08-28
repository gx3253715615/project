package com.github.project.handler.listener;

import cn.hutool.core.util.ObjectUtil;
import com.github.project.model.entity.Base;
import com.github.project.model.entity.User;
import com.github.project.util.UserUtil;
import com.mybatisflex.annotation.InsertListener;

import java.time.LocalDateTime;

/**
 * 插入数据监听器
 *
 * @author gaoxinyu
 * @date 2025/8/27 15:47
 */
public class MyInsertListener implements InsertListener {
    @Override
    public void onInsert(Object entity) {
        // 添加元数据
        User user = UserUtil.get();
        Long operatorId;
        if (ObjectUtil.isNull(user)) {
            operatorId = 0L;
        } else {
            operatorId = user.getId();
        }
        Base base = (Base) entity;
        base.setCreateUserId(operatorId);
        base.setUpdateUserId(operatorId);
        LocalDateTime now = LocalDateTime.now();
        if (base.getCreateTime() == null) {
            base.setCreateTime(now);
        }
        if (base.getUpdateTime() == null) {
            base.setUpdateTime(now);
        }
    }
}
