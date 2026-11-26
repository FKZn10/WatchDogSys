package com.jpmsoft.projectProducer.pojo.entity.acpm;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目投拓立项
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 14:34
 */
@Data
@TableName("Acpm_ProjectInvestmentApproval")
public class AcpmProjectInvestmentApproval extends BaseEntity {

    private String piaCode;
    private String piaName;
    private String piaArea;
    private String piaType;
    private String piaDeveloper;
    @TableField(value = "Pia_IsAgencyApproval")
    private String piaIsAgencyApproval;
    @TableField(value = "Pia_TenderClosingTime")
    private Date piaTenderClosingTime;
    @TableField(value = "Pia_AnswerClosingTime")
    private Date piaAnswerClosingTime;
    @TableField(value = "Pia_RequirementForMan")
    private String piaRequirementForMan;
    @TableField(value = "Pia_BidBondMoney")
    private BigDecimal piaBidBondMoney;
    @TableField(value = "Pia_PerformanceBond")
    private BigDecimal piaPerformanceBond;
    private BigDecimal piaAgencyFee;
    private BigDecimal piaPeople;
    private BigDecimal piaPerCapita;
    private BigDecimal piaServiceFee;
    private BigDecimal piaStampDuty;
    private BigDecimal piaAchievement;
    private BigDecimal piaLaborCost;
    private BigDecimal piaTotalCost;
    private BigDecimal piaGrossProfit;
    private BigDecimal piaNetProfit;
    private BigDecimal piaGrossProfitMargin;
    @TableField(value = "Pia_ProjectOverview")
    private String piaProjectOverview;
    @TableField(value = "Pia_RiskInstructions")
    private String piaRiskInstructions;
    private String piaRequestDoc;
    private String piaBiddingDoc;
    private BigDecimal piaProvisionalInvestment;
    private String updateUid;
    private String updateUname;
    private Date updateTime;
    private BigDecimal piaGuaranteeAmtReq;
    private String teId;
}
