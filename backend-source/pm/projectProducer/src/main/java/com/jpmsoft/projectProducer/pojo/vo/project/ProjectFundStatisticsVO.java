package com.jpmsoft.projectProducer.pojo.vo.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目资金统计列表
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/24 11:09
 */
@Data
public class ProjectFundStatisticsVO implements Serializable {
    private static final long serialVersionUID = -4985511377390773949L;

    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    @JsonProperty("pUseUnit")
    private String pUseUnit;
    @JsonProperty("pUseUnitName")
    private String pUseUnitName;
    @JsonProperty("pConstructionUnit")
    private String pConstructionUnit;
    @JsonProperty("pConstructionUnitName")
    private String pConstructionUnitName;
    @JsonProperty("pCalculateRoughlyAmount")
    private BigDecimal pCalculateRoughlyAmount;
    @JsonProperty("pEstimateAmount")
    private BigDecimal pEstimateAmount;
    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;
    @JsonProperty("pFinalAccounts")
    private BigDecimal pFinalAccounts;
    @JsonProperty("pParentId")
    private String pParentId;
    private BigDecimal secMoneyTotal;
    private BigDecimal conMoneyTotal;
    //合同支付已付款
    private BigDecimal conPayApprovalTotal;
    //合同支付年度已付款
    private BigDecimal conPayYearTotal;
    //项目支付已付款（无合同支付）
    private BigDecimal proPayApprovalTotal;
    /**
     * 结算
     */
    private BigDecimal saApprovedAmtTotal;
    /**
     * 未付款
     * 支付申请Pay_Payable减Pay_Approval
     */
    private BigDecimal unPayAmtTotal;
    @JsonProperty("pProjectStage")
    private String pProjectStage;
    @JsonProperty("pPLanStartDate")
    private Date pPLanStartDate;
    @JsonProperty("pPlanCompleteDate")
    private Date pPlanCompleteDate;
    @JsonProperty("pProjectStatus")
    private String pProjectStatus;

    private List<ProjectFundStatisticsVO> children;
}
