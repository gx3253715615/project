package com.example.project.handler.listener;

import cn.hutool.core.util.ObjectUtil;
import com.example.project.model.entity.Base;
import com.example.project.model.entity.User;
import com.example.project.util.UserUtil;
import com.mybatisflex.annotation.UpdateListener;

import java.time.LocalDateTime;

/**
 * 修改数据监听器
 *
 * @author gaoxinyu
 * @date 2025/8/27 15:51
 */
public class MyUpdateListener implements UpdateListener {

    /**
     * 修改数据
     */
    @Override
    public void onUpdate(Object entity) {
        User user = UserUtil.get();
        Long operatorId;
        if (ObjectUtil.isNull(user)) {
            operatorId = 0L;
        } else {
            operatorId = user.getId();
        }
        Base base = (Base) entity;
        base.setUpdateUserId(operatorId);
        base.setUpdateTime(LocalDateTime.now());
    }
}
