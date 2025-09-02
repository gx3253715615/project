package com.github.project.model.entity;

import com.github.project.handler.listener.MyInsertListener;
import com.github.project.handler.listener.MyUpdateListener;
import com.github.project.model.entity.Base;
import com.mybatisflex.annotation.Table;
import java.math.BigDecimal;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 *  实体类。
 *
 * @author gaoxinyu
 * @date 2025/09/02 14:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(value = "collection", onInsert = MyInsertListener.class, onUpdate = MyUpdateListener.class)
public class Collection extends Base {

    /**
     * 价格
     */
    private BigDecimal price;

}
