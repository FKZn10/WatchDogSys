package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;

/**
 * 外部系统入口树节点。
 */
@Data
public class SysCommonExternalTreeVO {
    private String key;
    private String title;
    private String value;
    private String id;
    private String parentId;
    private String name;
    private String url;
    private String icon;
    private Short status;
    private Long sortNo;
    private java.util.List<SysCommonExternalTreeVO> children;
}
