package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 科目信息扩展变更
 */
@Data
public class SecInfoSubjectExtraChangeDTO {
    /**
     * 主键
     */
    private String id;

    /**
     * 标段变更id
     */
    private String sicId;

    /**
     * 标段id
     */
    private String secId;

    /**
     * 招标计划变更id
     */
    private String bpcId;

    /**
     * 估算/概算id
     */
    private String parentId;

    /**
     * 科目名称
     */
    private String seName;

    /**
     * 科目金额
     */
    private BigDecimal seMoney;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 科目级别
     */
    private String levelCode;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人姓名
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;
}
