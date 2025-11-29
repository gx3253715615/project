package com.github.project.model.entity;

import com.github.project.enums.OrderEnum;
import com.github.project.handler.listener.MyInsertListener;
import com.github.project.handler.listener.MyUpdateListener;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 订单
 *
 * @author gaoxinyu
 * @date 2025/10/8 18:49
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "order", onInsert = MyInsertListener.class, onUpdate = MyUpdateListener.class)
public class Order extends Base {
    @Column(value = "user_id")
    private String userId;

    @Column(value = "reverse_user_id")
    private String reverseUserId;

    @Column(value = "order_no")
    private String orderNo;

    @Column(value = "status")
    private OrderEnum status;
}
