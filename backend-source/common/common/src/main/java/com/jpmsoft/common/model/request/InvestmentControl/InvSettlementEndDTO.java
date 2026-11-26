package com.jpmsoft.common.model.request.InvestmentControl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同结算申请
 */
@Data
public class InvSettlementEndDTO extends BaseFileRequest {

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

    @JsonProperty("pName")
    private String pName;
    private String conName;
    private String saeConName;
    private String saeProjectName;
    private String saePartyAName;
    private BigDecimal payApplySum;
    private Boolean outAudit;
    private Boolean haveCostConsult;
    private String saeEndCarefulFileIds;
}
