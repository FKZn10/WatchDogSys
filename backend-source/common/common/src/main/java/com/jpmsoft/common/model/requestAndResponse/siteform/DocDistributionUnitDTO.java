package com.jpmsoft.common.model.requestAndResponse.siteform;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 下发单位表
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocDistributionUnitDTO extends BasePageEntity {
    /**
     * 主键
     */
    private String dduId;

    /**
     * 关联主表ID
     */
    private String mainTableId;

    /**
     * 主表类型
     */
    private String mainTableType;

    /**
     * 下发单位
     */
    private String unit;

    /**
     * 是否电子版
     */
    private String isElectronic;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 接收人is
     */
    private String recipient;

    /**
     * 批次id
     */
    private String batchId;

    /**
     * 单位类型
     */
    private String unitType;

    /**
     * 下发时间
     */
    private Date releaseTime;

    /**
     * 下发人员id
     */
    private String issuedPersonnelId;

    /**
     * 下发人员
     */
    private String issuedPersonnel;

    /**
     * 审批状态
     */
    private Integer state;

    /**
     * 审批时间
     */
    @JsonProperty("statetime")
    private String statetime;

    /**
     * 下发单位名称
     */
    @JsonProperty("unitname")
    private String unitname;

    /**
     * 接收人名称
     */
    private String recipientName;
}
