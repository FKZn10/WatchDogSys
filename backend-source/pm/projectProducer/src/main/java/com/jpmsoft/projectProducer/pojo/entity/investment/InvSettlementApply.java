package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同结算申请
 */
@Data
@TableName("Inv_Settlement_Apply")
public class InvSettlementApply {
    @TableId(type = IdType.INPUT)
    private String saId;
    private String conId;
    private BigDecimal saBudgetAmount;
    private String saContractor;
    private String saSupervisor;
    private String saCostConsult;
    private BigDecimal saContractorAmount;
    private BigDecimal saSupervisorAmount;
    private BigDecimal saCostConsultAmount;
    private Integer saSupervisorCheck;
    private Integer saHandDown;
    private String saCreateUid;
    private String saCreateUname;
    private Date saCreateTime;
    private Integer saState;
    private Date saStateTime;
    private String pId;
    // 审定金额
    private BigDecimal saApprovedAmt;
    private BigDecimal saAuditFees;
    private Integer saCuAf;
    private String saAuditReport;
    private Boolean saHistorical;

    private String saDraft;
    private String saSupplement;
    private String saIsSubContract;
    private String saSupplementId;
    private String teId;

    private Date saSubmissionTime;
    private Date saApprovedTime;
    private String saApprovedDes;
    private BigDecimal saIncreaseDecrease;
    private String saSubmissionUser;
    private String saSubmissionPhone;
    private String saConCode;

    private BigDecimal saWarrantyAmt;
    private BigDecimal saWarrantyRate;
    private String saExamineUser;
    private String saExaminePhone;

    private String saOpinion;

    private String saReviewUnit;
    private String saReportFile;
    private String saReviewReportUnit;
    private String mrId;
    private String saSerialNumber;
    /**
     * 施工完成确认单
     */
    private String completionConfirmId;
    /**
     * 结算总价
     */
    private BigDecimal saAmountTotal;

    /**
     * 审计单位备注
     */
    private String saAuditRemark;

    private String saRemark;

    private String saTitle;
}