package com.jpmsoft.custom.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询字段以及过滤条件
 *
 * @Author: 李文涛 aaron
 * @date: 2023/6/2 11:59
 */
@Data
public class FieldAndFilterDTO implements Serializable {
    /**
     * 查询列名
     */
    private String columnCode;
    /**
     * 列转小驼峰名
     */
    private String columnJavaSmallHump;
    /**
     * 字段类型
     */
    private String columnType;
    /**
     * 查询表别名
     */
    private String tableAlias;
    /**
     * 表编码
     */
    private String tableCode;
    /**
     * 是否支持筛选
     */
    private String selectionState;
    /**
     * 筛选类型
     * accurate：  精确查询
     * vague： 模糊查询
     * interval： 区间查询
     * multiPrecision: 城建下拉选项查询
     */
    private String selectionType;
    /**
     * 统计查询
     * sum  求和
     * max  最大值
     * min  最小值
     */
    private String tjQuery;
}
