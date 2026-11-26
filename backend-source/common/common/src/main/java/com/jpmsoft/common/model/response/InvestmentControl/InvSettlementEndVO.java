package com.jpmsoft.common.model.response.InvestmentControl;

import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同结算申请
 */
@Data
public class InvSettlementEndVO extends ProjectCommonRes {

    private String saeId;
    private String conId;
    private String saeContractor;
    private String saeSupervisor;
    private String saeCostConsult;
    private BigDecimal saeContractorAmount;
    private BigDecimal saeSupervisorAmount;
    private BigDecimal saeCostConsultAmount;
    private Integer saeSupervisorCheck;
    private Integer saeHandDown;
    private String saeCreateUid;
    private String saeCreateUname;
    private Date saeCreateTime;
    private Integer state;
    private Date stateTime;
    private BigDecimal saeAuditFees;
    private Integer saeCuAf;
    private BigDecimal saeAuditReport;
    private Date saeSubmissionTime;
    private Date saeApprovedTime;
    private String saeApprovedDes;
    private BigDecimal saeIncreaseDecrease;
    private String saeSubmissionUser;
    private String saeSubmissionPhone;
    private String saeCode;
    private BigDecimal saeWarrantyAmt;
    private BigDecimal saeWarrantyRate;
    private String saeExamineUser;
    private String saeExaminePhone;
    private String saeEndCarefulFile;
    private Date saeAuditEndTime;
    private Date saeAuditTime;
    private String saeAuditOpinion;

    private String conName;
    private String conType;
    private String conCode;
    private String saeContractorName;
    private String saeSupervisorName;
    private String saeCostConsultName;
    //合同金额
    private BigDecimal conMoneyTotal;
    //合同金额
    private BigDecimal amount;
    private String saeConName;
    private String saeProjectName;
    private String saePartyAName;
    /**
     * 合同已支付金额 定时任务刷取
     */
    private BigDecimal payApplySum;
}
