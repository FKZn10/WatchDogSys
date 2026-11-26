package com.jpmsoft.projectConsumer.pojo.dto.qualitySafety;

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
     * 巡查时间
     */
    private List<String> ptEvaluationTimes;
    @JsonProperty("pProjectStatus")
    private String pProjectStatus;

    @JsonProperty("qstId")
    private String qstId;

    @JsonProperty("qstIds")
    private List<String> qstIds;
    @JsonProperty("ptType")
    private String ptType;
    @JsonProperty("ptTypes")
    private List<String> ptTypes;

}