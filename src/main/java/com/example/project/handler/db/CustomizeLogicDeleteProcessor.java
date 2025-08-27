package com.example.project.handler.db;

import com.example.project.util.UserUtil;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.dialect.IDialect;
import com.mybatisflex.core.logicdelete.AbstractLogicDeleteProcessor;
import com.mybatisflex.core.table.TableInfo;

import static com.mybatisflex.core.constant.SqlConsts.EQUALS;
import static com.mybatisflex.core.constant.SqlConsts.SINGLE_QUOTE;

/**
 * 自定义时间和用户的逻辑删除处理器
 *
 * @author gaoxinyu
 * @date 2025/8/27 15:52
 */
public class CustomizeLogicDeleteProcessor extends AbstractLogicDeleteProcessor {

    @Override
    public String buildLogicDeletedSet(String logicColumn, TableInfo tableInfo, IDialect dialect) {

        String sql = dialect.wrap(logicColumn) + EQUALS + prepareValue(getLogicDeletedValue());

        //扩展一下软删除的sql语句，增加删除时间和删除人。
        sql += "," + dialect.wrap("update_time") + EQUALS + "now()";
        sql += "," + dialect.wrap("update_user_id") + EQUALS + UserUtil.get().getId();

        return sql;
    }

    @Override
    public Object getLogicNormalValue() {
        return FlexGlobalConfig.getDefaultConfig().getNormalValueOfLogicDelete();
    }

    @Override
    public Object getLogicDeletedValue() {
        return FlexGlobalConfig.getDefaultConfig().getDeletedValueOfLogicDelete();
    }

    private static Object prepareValue(Object value) {
        if (value instanceof Number || value instanceof Boolean) {
            return value;
        }
        return SINGLE_QUOTE + value + SINGLE_QUOTE;
    }
}
