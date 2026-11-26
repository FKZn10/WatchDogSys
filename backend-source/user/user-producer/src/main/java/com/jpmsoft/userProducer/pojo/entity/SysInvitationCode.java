package com.jpmsoft.userProducer.pojo.entity;

import lombok.Data;

import java.util.Date;

/**
 * 邀请码
 */
@Data
public class SysInvitationCode {
    /**
     * 主键
     */
    private String id;

    /**
     * 邀请码
     */
    private String code;

    /**
     * 关联项目 ID
     */
    private String pId;

    /**
     * 过期时间
     */
    private Date expiredTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUid;

    /**
     * 创建人姓名
     */
    private String createUname;
}