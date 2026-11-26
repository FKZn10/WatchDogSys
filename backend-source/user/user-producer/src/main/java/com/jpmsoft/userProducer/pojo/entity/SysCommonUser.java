package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 用户常用功能及个性化设置
 */
@Data
@TableName("sys_common_user")
public class SysCommonUser {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 用户ID
     */
    private String userId;

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

    /** 本人的常用功能显示名称。 */
    @TableField("target_name")
    private String targetName;

    /**
     * 图标标识或地址
     */
    private String icon;

    /**
     * 1从本人常用功能中移除 0显示
     */
    private Short isHidden;

    /**
     * 本人最终置顶状态
     */
    private Short isPinned;

    /**
     * 排序值
     */
    private Long sortNo;

    private Date createTime;
    private Date updateTime;
}
