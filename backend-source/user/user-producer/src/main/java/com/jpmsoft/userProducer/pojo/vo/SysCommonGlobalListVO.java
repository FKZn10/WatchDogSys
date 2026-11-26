package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 全局常用功能管理列表项。
 */
@Data
public class SysCommonGlobalListVO {
    private String id;
    private Short targetType;
    private String targetId;
    private String tCode;
    private String targetName;
    private String targetUrl;
    private String icon;
    private Short isPinned;
    private Short status;
    private Long sortNo;
    private String createUid;
    private Date createTime;
    private String updateUid;
    private Date updateTime;
}
