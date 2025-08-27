package com.example.project.config;

import com.example.project.handler.db.CustomizeLogicDeleteProcessor;
import com.example.project.handler.factory.FlexSqlMessageCollector;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryColumnBehavior;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-flex配置
 *
 * @author gaoxinyu
 * @date 2025/8/27 16:11
 */
@Configuration
public class MyBatisFlexConfiguration {

    @Value("${mybatis-flex.audit}")
    private Boolean audit;

    @PostConstruct
    public void init() {
        //开启审计功能
        AuditManager.setAuditEnable(audit);
        //自定义消息工厂
        FlexSqlMessageCollector creator = new FlexSqlMessageCollector();
        AuditManager.setMessageCollector(creator);
        // 所有查询自动忽略null和空字符串条件
        QueryColumnBehavior.setIgnoreFunction(QueryColumnBehavior.IGNORE_BLANK);
        // 自定义逻辑删除处理器
        LogicDeleteManager.setProcessor(new CustomizeLogicDeleteProcessor());
    }
}

