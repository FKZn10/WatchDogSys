package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 常用功能外部系统入口
 */
@Data
@TableName("sys_common_external")
public class SysCommonExternal {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 显示名称
     */
    private String name;

    /**
     * 跳转地址
     */
    private String url;

    /**
     * 图标标识或地址
     */
    private String icon;

    /**
     * 1启用 0停用
     */
    private Short status;

    /**
     * 排序值
     */
    private Long sortNo;

    private String createUid;
    private Date createTime;
    private String updateUid;
    private Date updateTime;
}