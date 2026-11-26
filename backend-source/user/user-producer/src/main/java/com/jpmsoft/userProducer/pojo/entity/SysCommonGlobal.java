package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 全局默认功能菜单或外部系统入口
 */
@Data
@TableName("sys_common_global")
public class SysCommonGlobal {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 1功能菜单 2外部系统
     */
    private Short targetType;

    /**
     * 外部系统入口ID，仅targetType为2时使用
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String targetId;

    /**
     * 菜单编码，仅targetType为1时使用。
     */
    @TableField(value = "t_code", updateStrategy = FieldStrategy.IGNORED)
    private String tCode;

    /** 常用功能显示名称。 */
    @TableField("target_name")
    private String targetName;

    /**
     * 全局默认置顶：1是 0否
     */
    private Short isPinned;

    /**
     * 图标标识或地址
     */
    private String icon;

    /**
     * 1发布 0停用
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
