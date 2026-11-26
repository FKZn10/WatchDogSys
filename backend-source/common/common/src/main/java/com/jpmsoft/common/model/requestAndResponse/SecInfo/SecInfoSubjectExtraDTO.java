package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 科目扩展信息DTO
 */
@Data
public class SecInfoSubjectExtraDTO {
    /**
     * 主键id
     */
    private String id;

    /**
     * 标段id
     */
    private String secId;

    /**
     * 招标计划id
     */
    private String bpId;

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
