package com.jpmsoft.userProducer.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 当前用户的常用功能设置请求。用户ID始终从登录态获取。
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysCommonUserDTO extends BasePageEntity {
    private String id;
    private Short targetType;
    private String targetId;
    /**
     * 菜单编码，仅targetType为1时使用。
     */
    @JsonProperty("tCode")
    private String tCode;
    /**
     * 本人的常用功能显示名称。
     */
    private String targetName;
    private String icon;
    private Short isHidden;
    private Short isPinned;
    private Long sortNo;
}
