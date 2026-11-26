package com.jpmsoft.projectProducer.pojo.dto.qualitySafety;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/25 11:03
 */
@Data
public class ProSafetyLedgerDtlDTO extends ProjectCommonRes implements Serializable {


    private static final long serialVersionUID = -6238520457788445196L;
    private String contractId;
    private String contractName;
    private String constructionUnitId;
    /**
     * 施工单位
     */
    @JsonProperty("constructionUnitName")
    private String constructionUnitName;
    /**
     * 施工合同金额
     */
    private BigDecimal constructionConMoney;
    /**
     * 报告份数
     */
    private Integer qsPlanTaskCount;
    /**
     * 问题数量
     */
    private Integer qsRecordCount = 0;
    /**
     * 整改数量
     */
    private Integer qsFinishCount = 0;
    /**
     * 闭合率
     */
    private BigDecimal qsFinishRate;
    /**
     * 逾期整改数量
     */
    private BigDecimal qsFinishOverdueCount ;
    /**
     * 二次整改数量
     */
    private BigDecimal qsFinishOnTimeCount ;
    /**
     * 逾期整改率
     */
    private BigDecimal qsFinishOverdueRate;
    /**
     * 二次整改率
     */
    private BigDecimal qsFinishOnTimeRate;
    /**
     * 巡查时间
     */
    private List<String> ptEvaluationTimes;

    /**
     * 任务类别 任务类别 01、质量 02、安全
     */
    private String  ptCategory;

    @JsonProperty("pProjectStatus")
    private String pProjectStatus;


    private BigDecimal qsPlanTaskCountSum;

    private BigDecimal qsRecordCountSum;
    private BigDecimal qsFinishCountSum;

    @JsonProperty("qstId")
    private String qstId;

    @JsonProperty("qstIds")
    private List<String> qstIds;
    @JsonProperty("ptType")
    private String ptType;
    @JsonProperty("ptTypes")
    private List<String> ptTypes;
}