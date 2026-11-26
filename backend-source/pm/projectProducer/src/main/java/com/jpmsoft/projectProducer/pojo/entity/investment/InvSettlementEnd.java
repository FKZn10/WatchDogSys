package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同结算申请
 */
@Data
@TableName("Inv_Settlement_End")
public class InvSettlementEnd {
    @TableId(type = IdType.INPUT)
    private String saeId;
    private String pId;
    private String conId;
    private String saeContractor;
    private String saeSupervisor;
    private String saeCostConsult;
    private BigDecimal saeContractorAmount;
    private BigDecimal saeSupervisorAmount;
    private BigDecimal saeCostConsultAmount;
    private Integer saeSupervisorCheck;
    private Integer saeHandDown;
    private Integer saeCuAf;
    private BigDecimal saeAuditFees;
    private Date saeSubmissionTime;
    private String saeApprovedDes;
    private String saeSubmissionUser;
    private String saeSubmissionPhone;
    private BigDecimal saeAuditReport;
    private BigDecimal saeIncreaseDecrease;
    private Date saeApprovedTime;
    private String saeExamineUser;
    private String saeExaminePhone;
    private BigDecimal saeWarrantyAmt;
    private BigDecimal saeWarrantyRate;
    private String saeEndCarefulFile;
    private String saeCode;
    private String saeCreateUid;
    private String saeCreateUname;
    private Date saeCreateTime;
    private Integer state;
    private Date stateTime;
    private Date saeAuditEndTime;
    private String teId;
    private Date saeAuditTime;
    private String saeAuditOpinion;

    private String saeConName;
    private String saeProjectName;
    private String saePartyAName;
    private BigDecimal payApplySum;

}
