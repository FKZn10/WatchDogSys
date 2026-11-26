package com.jpmsoft.projectProducer.pojo.dto.acpm;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目投拓立项
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 14:34
 */
@Data
public class AcpmProjectInvestmentApprovalDto extends BaseFileDto implements Serializable {

    private static final long serialVersionUID = -5167581975155821142L;

    private String piaCode;
    private String piaName;
    private String piaArea;
    private String piaType;
    private String piaDeveloper;
    private String piaIsAgencyApproval;
    private Date piaTenderClosingTime;
    private Date piaAnswerClosingTime;
    private String piaRequirementForMan;
    private BigDecimal piaBidBondMoney;
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
    private String piaProjectOverview;
    private String piaRiskInstructions;
    private String piaRequestDoc;
    private String piaBiddingDoc;
    private BigDecimal piaProvisionalInvestment;
    private String updateUid;
    private String updateUname;
    private Date updateTime;
    private BigDecimal piaGuaranteeAmtReq;

    //附件ID
    private String piaRequestDocIds;
    private String piaBiddingDocIds;


}
