package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 当前用户可见的常用功能，合并个人与全局配置。
 */
@Data
public class SysCommonUserListVO {
    private String id;
    private Short targetType;
    private String targetId;
    private String tCode;
    private String targetName;
    private String targetUrl;
    private String icon;
    private Short isPinned;
    private Long sortNo;
    private Boolean global;
    private Boolean personalized;
    private Date createTime;
    private Date updateTime;
}
