package com.jpmsoft.custom.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 表操作入参
 * 创建表
 *
 * @Author: 李文涛 aaron
 * @date: 2024/1/29 8:34
 */
@Data
public class TableActionDTO implements Serializable {
    /**
     * 表名
     */
    private String tbName;
    /**
     * 表编码
     */
    private String tbCode;
    /**
     * 字段信息
     */
    private List<TableColumnDTO> columns;
}
