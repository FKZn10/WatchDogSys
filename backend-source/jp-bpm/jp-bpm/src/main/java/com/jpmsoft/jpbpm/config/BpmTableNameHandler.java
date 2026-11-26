package com.jpmsoft.jpbpm.config;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.jpmsoft.common.utils.ValidateUtil;

/**
 * 流程动态表名处理器
 *
 * @Author: 李文涛 aaron
 * @date: 2025/2/11 14:38
 */
public class BpmTableNameHandler implements TableNameHandler {

    @Override
    public String dynamicTableName(String sql, String tableName) {
        if (tableName.equalsIgnoreCase("sys_file") || tableName.equalsIgnoreCase("sys_task_processin") || tableName.equalsIgnoreCase("sys_task_process_user")) {
            // 获取参数方法
            String code = RequestDataHelper.getRequestData();
            //System.err.println("分表编码----" + tableName + "----" + code);
            if (ValidateUtil.isNotEmpty(code)) {
                if (code.toLowerCase().contains(tableName.toLowerCase())) {
                    return code;
                } else {
                    return tableName;
                }

            } else {
                return tableName;
            }
        }
        return tableName;
    }
}

