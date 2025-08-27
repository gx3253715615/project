package com.example.project.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.RelationOneToOne;
import com.mybatisflex.core.util.CollectionUtil;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * base
 *
 * @author gaoxinyu
 * @date 2025/8/27 15:41
 */
@Data
@Accessors(chain = true)
public class Base implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id(keyType = KeyType.Auto)
    @NotNull(message = "主键不能为空！请选择！")
    private Long id;


    /**
     * 创建时间
     */
    @Column(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") // 指定序列化日期格式
    private LocalDateTime createTime;

    /**
     * 创建人id
     */
    @Column(value = "create_user_id")
    private Long createUserId;

    /**
     * 创建人姓名
     */
    @Column(ignore = true)
    @RelationOneToOne(
            selfField = "createUserId",
            targetTable = "user",
            targetField = "id",
            valueField = "nickname")
    private String createUserName;


    /**
     * 更新时间
     */
    @Column(value = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 更新人id
     */
    @Column(value = "update_user_id")
    private Long updateUserId;

    /**
     * 更新人姓名
     */
    @Column(ignore = true)
    @RelationOneToOne(
            selfField = "updateUserId",
            targetTable = "user",
            targetField = "id",
            valueField = "nickname")
    private String updateUserName;

    /**
     * 逻辑删除
     */
    @Column(value = "deleted", isLogicDelete = true)
    private Boolean deleted;



    /**
     * 构建保存或更新列表
     *
     * @param oldList:          旧的列表
     * @param newList:          新的列表
     * @param keyConstructor:   构造对象唯一标识的函数
     * @param delete:           是否将新列表中不存在的删除
     * @param updateOtherField: 是否在判断新旧相同后，更新其他字段
     * @return List<T> 返回保存或更新后的列表
     */
    public static <T extends Base, K> List<T> buildSaveOrUpdateList(
            List<T> oldList, List<T> newList,
            Function<T, K> keyConstructor, boolean delete, boolean updateOtherField) {
        if (CollectionUtil.isEmpty(newList)) {
            return newList;
        }
        // 去重处理：转换为Map后提取values生成新列表
        newList = newList.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                keyConstructor,
                                Function.identity(),
                                (existing, replacement) -> replacement
                        ),
                        map -> new ArrayList<>(map.values())
                ));
        if (CollectionUtil.isEmpty(oldList)) {
            return newList;
        }
        Map<K, T> oldMap = oldList.stream()
                .collect(Collectors.toMap(keyConstructor, Function.identity()));
        // 创建resultList
        List<T> resultList = new ArrayList<>();
        // 标记oldItem是否被匹配
        Map<K, Boolean> oldItemMatched = new HashMap<>();
        for (K key : oldMap.keySet()) {
            oldItemMatched.put(key, false);
        }
        // 处理新增和更新
        for (T newItem : newList) {
            K key = keyConstructor.apply(newItem);
            T oldItem = oldMap.get(key);
            if (oldItem != null) {
                // 存在对应oldItem
                if (updateOtherField) {
                    newItem.setId(oldItem.getId());
                    resultList.add(newItem);
                }
                oldItemMatched.put(key, true);
            } else {
                // 不存在对应oldItem，新增
                resultList.add(newItem);
            }
        }
        // 如果需要删除，处理旧列表中不在新列表中的元素
        if (delete) {
            for (T oldItem : oldList) {
                K key = keyConstructor.apply(oldItem);
                if (!oldItemMatched.getOrDefault(key, false)) {
                    oldItem.setDeleted(true);
                    resultList.add(oldItem);
                }
            }
        }
        // 如果去重后的新旧列表大小相同，并且和去重后的所有oldItem都被匹配到，并且不需要更新其他字段，则返回空列表
        if ((newList.size() == oldMap.size()) &&
            oldItemMatched.values().stream().allMatch(matched -> matched) &&
            !updateOtherField) {
            return new ArrayList<>();
        }
        return resultList;
    }
}
