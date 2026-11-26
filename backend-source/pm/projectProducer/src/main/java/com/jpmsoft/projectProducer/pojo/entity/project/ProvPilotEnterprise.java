package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 省试点企业
 *
 * @author whjp
 * @since 2026-09-17
 */
@Data
@TableName("prov_pilot_enterprise")
public class ProvPilotEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 id（雪花）
     */
    private String id;

    /**
     * 年度（即年份）
     */
    private Integer annualYear;

    /**
     * 批次
     */
    private String batch;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 企业类型（字典码，字典 JPMsoft_ZPSBZY：01=勘察设计、02=建筑施工、03=部品生产、04=智能装备研发制造、05=技术服务）
     */
    private String enterpriseType;

    /**
     * 企业编号（自动生成：SQ+年度4+批次2+序号3，如 SQ202401001）
     */
    private String enterpriseCode;

    /**
     * 所在区（字典码，字典 JPMsoft_ZPSZQ）
     */
    private String district;

    /**
     * 创建人 id
     */
    private String createUid;

    /**
     * 创建人名称
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人 id
     */
    private String updateUid;

    /**
     * 修改人名称
     */
    private String updateUname;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 租户 id
     */
    private String teId;
}