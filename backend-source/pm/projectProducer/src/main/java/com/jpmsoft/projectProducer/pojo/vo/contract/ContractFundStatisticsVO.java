package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/24 14:30
 */
@Data
public class ContractFundStatisticsVO implements Serializable {

    private static final long serialVersionUID = 8336369111562158409L;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    @JsonProperty("tCode")
    private String tCode;
    private String conId;
    private String conCode;
    private String conName;
    private String conPartyAId;
    private String conPartyBId;
    private String conPartyAName;
    private String conPartyBName;
    private String conType;
    private Date conPlanStartDate;
    private Date conPlanEndDate;
    private BigDecimal conMoneyTotal;
    /**
     * 审批通过支付总额
     */
    private BigDecimal payApprovalTotal;
    /**
     * 审批通过审定金额总额
     */
    private BigDecimal payApprovalSum;
    /**
     * 年度付款
     */
    private BigDecimal payYearTotal;
    /**
     * 未付款
     * 支付申请Pay_Payable减Pay_Approval
     */
    private BigDecimal unPayAmtTotal;
    /**
     * 累计付款比例
     * 累计付款金额/合同金额
     */
    private BigDecimal payApprovalRate;

    private BigDecimal ecApplyMoneyTotal;
    private BigDecimal ecApplyRate;
    private BigDecimal ecfApplyMoneyTotal;
    private BigDecimal ecfApplyRate;
    /**
     * 结算
     */
    private BigDecimal saApprovedAmtTotal;
    private BigDecimal saContractorAmountTotal;
    private BigDecimal saSupervisorAmountTotal;
    private BigDecimal saCostConsultTotal;
    private BigDecimal payDoneTotal;
    private BigDecimal isPaymentAmtTotal;
    private BigDecimal isPaymentRateTotal;
    private BigDecimal isActualAmtTotal;
    private BigDecimal ciCollectTaxAmtTotal;
    private String conState;
    private String conExecution;

    private String saApprovedAmt;

    private BigDecimal conRate;
    private String conMoneyRate;
    private BigDecimal cpafApplicationSum;
}
