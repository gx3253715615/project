package com.github.project.handler.factory;

import com.github.project.util.HttpUtil;
import com.mybatisflex.core.audit.AuditMessage;
import com.mybatisflex.core.audit.MessageCollector;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义mybatis-flex sql审计消息工厂
 *
 * @author gaoxinyu
 * @date 2025/8/27 16:05
 */
@Slf4j
public class FlexSqlMessageCollector implements MessageCollector {

    /**
     * [09:25:19:228] [INFO] - c.e.y.f.FlexSqlMessageCollector.collect:23 - [MyBatis-Flex SQL 审计]
     * ==> Request URL   : http://..
     * ==> User IP       : 0:0:0:0:0:0:0:1
     * ==> Elapsed Time  : 17 ms
     * ==> SQL Statement :
     * SELECT `code` FROM `permission` WHERE (`id` = 2) AND `deleted` = 0 LIMIT 1
     * <== Total Queries : 1
     */
    @Override
    public void collect(AuditMessage message) {
        String fullSql = message.getFullSql();
        long elapsedTime = message.getElapsedTime();
        int queryCount = message.getQueryCount();
        log.info("""
                [MyBatis-Flex SQL 审计]
                ==> Request URL   : {}
                ==> User IP       : {}
                ==> Elapsed Time  : {} ms
                ==> SQL Statement :
                {}
                <== Total Queries : {}
                """, HttpUtil.getRequestUrl(), HttpUtil.getIp(), elapsedTime, fullSql, queryCount);
    }
}
