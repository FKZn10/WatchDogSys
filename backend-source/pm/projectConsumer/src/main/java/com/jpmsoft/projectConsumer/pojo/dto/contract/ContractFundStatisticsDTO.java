package com.jpmsoft.projectConsumer.pojo.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/24 14:30
 */
@Data
public class ContractFundStatisticsDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 8336369111562158409L;
    private String year;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private String conCode;
    private String conName;
    private String conPartyAId;
    private String conPartyBId;
    private String conPartyAName;
    private String conPartyBName;
    private String conType;
    private BigDecimal conMoneyTotal;
    private BigDecimal payApprovalTotal;
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

    private BigDecimal ecApplyMoney;
    private BigDecimal ecApplyRate;
    private BigDecimal ecfApplyMoney;
    private BigDecimal ecfApplyRate;
    /**
     * 结算
     */
    private BigDecimal saApprovedAmtTotal;
    private BigDecimal saContractorAmountTotal;
    private BigDecimal saSupervisorAmountTotal;
    private BigDecimal saCostConsultTotal;
    private BigDecimal payDone;
    private String conState;
    private String conExecution;

    private String childProject;
}
