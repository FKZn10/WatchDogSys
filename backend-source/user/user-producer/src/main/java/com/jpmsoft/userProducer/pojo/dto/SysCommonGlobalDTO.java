package com.jpmsoft.userProducer.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysCommonGlobalDTO extends BasePageEntity {
    /**
     * 主键
     */
    private String id;

    /**
     * 1功能菜单 2外部系统
     */
    private Short targetType;

    /**
     * 外部系统入口ID，仅targetType为2时使用
     */
    private String targetId;

    /**
     * 菜单编码，仅targetType为1时使用。
     */
    @JsonProperty("tCode")
    private String tCode;

    /** 常用功能显示名称。 */
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
