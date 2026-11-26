package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * 邀请码
 */
@Data
public class SysInvitationCodeDTO {
    /**
     * 页码，从 1 开始，默认第 1 页，仅用于查询
     */
    private Integer page = 1;

    /**
     * 每页条数，默认 10 条，仅用于查询
     */
    private Integer size = 10;

    /**
     * 主键
     */
    private String id;

    /**
     * 邀请码
     */
    private String code;

    /**
     * 关联项目 ID，查询时可选，按项目 ID 精确筛选；返回时为邀请码所属项目 ID
     */
    private String pId;

    /**
     * 项目名称，查询时可选，按名称模糊筛选；返回时为关联项目的当前名称
     */
    private String pName;

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

    /**
     * 是否已过期：true 已过期，false 未过期；过期时间为空时未过期。
     * 请求时可用于筛选，返回时由数据库当前时间计算。
     */
    private Boolean expired;

    /**
     * 创建时间范围起点（包含），仅用于筛选
     */
    private Date createTimeStart;

    /**
     * 创建时间范围终点（包含），仅用于筛选
     */
    private Date createTimeEnd;

    /**
     * 过期时间范围起点（包含），仅用于筛选
     */
    private Date expiredTimeStart;

    /**
     * 过期时间范围终点（包含），仅用于筛选
     */
    private Date expiredTimeEnd;
}
