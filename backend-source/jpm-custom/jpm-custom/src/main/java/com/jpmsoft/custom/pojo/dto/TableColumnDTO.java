package com.jpmsoft.custom.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/29 8:35
 */
@Data
public class TableColumnDTO implements Serializable {

    private static final long serialVersionUID = -8604766695052642179L;

    /**
     * 字段名字
     */
    private String cmName;
    /**
     * 字段编码
     */
    private String cmCode;
    /**
     * 修改字段名时使用
     * 旧的字段编码
     */
    private String cmOldCode;
    /**
     * 字段类型
     */
    private String cmType;
    /**
     * 字段长度
     */
    private Integer cmLength;
    /**
     * 小数位长度
     */
    private Integer cmSxLength;
    /**
     * 备注
     */
    private String cmRemark;
    /**
     * 是否是主键
     */
    private boolean cmPk;
    /**
     * 是否是附件
     */
    private boolean cmFile;
    /**
     * 导入时 是否唯一校验
     */
    private boolean columnOnly;
    /**
     * 字段是否必填
     */
    private boolean cmMust;
    /**
     * 如果是链表字段 是否创建外键关联
     */
    private boolean cmForeign;
    /**
     * 1 普通字段
     * 2 链表字段
     * 3 编码字段
     */
    private Integer filterType;
    /**
     * 关联的表编码或者字典编码
     */
    private String assocTableCode;
    /**
     * 关联为表编码时 其导入导出对应的字段编码
     */
    private String assocColumnCode;
    /**
     * 关联为表编码时其关联表的主键ID编码
     */
    private String assocColumnPk;
    /**
     * 关联为表编码时 额外导出字段编码
     */
    private String assocShowCode;
    /**
     * 关联为表编码时 额外导出字段名称
     */
    private String assocShowName;
}
