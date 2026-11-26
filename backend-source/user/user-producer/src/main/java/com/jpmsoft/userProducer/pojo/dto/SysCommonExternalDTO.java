package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SysCommonExternalDTO {
    /**
     * 主键
     */
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
