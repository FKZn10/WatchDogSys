package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 归档文件名称规则
 *
 * @Author: 李文涛 aaron
 * @date: 2024/8/29 14:03
 */
@Data
@TableName("Archive_File_Name_Rule")
public class ArchiveFileNameRule {
    @TableId(type = IdType.INPUT)
    private String id;
    private String afnId;
    /**
     * 顺序序号
     */
    private Integer afnrSort;
    /**
     * 规则类型
     */
    private String afnrType;
    /**
     * 常量值
     * 项目字段名
     * 合同字段名
     * 标段字段名
     * 本表单字段名
     * 关联表字段名
     * SQL语句
     */
    private String afnrValue;
    /**
     * 字典编码
     */
    private String afnrDicCode;
    /**
     * 链表时表示 关联表名
     */
    private String afnrShipTable;
    /**
     * 链表时表示 关联字段名
     */
    private String afnrShipCode;
    /**
     * 关联字段名在本表单的对应的字段名
     */
    private String afnrShipCurrent;
    /**
     * 规则字段名(表单字段名的值与配置值相同时当前规则生效)
     */
    private String afnrField;
    /**
     * 规则字段值(表单字段名的值与配置值相同时当前规则生效)
     */
    private String afnrFieldVal;

}
