package com.jpmsoft.projectProducer.pojo.vo.acpm;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目投拓立项
 * 链表选择
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 14:34
 */
@Data
public class AcpmProjectInvestmentApprovalSelectVo implements Serializable {


    private static final long serialVersionUID = -261806525440891999L;

    private String id;
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
    private BigDecimal piaProvisionalInvestment;
    private String createUname;
    private Date createTime;
    private BigDecimal piaGuaranteeAmtReq;
}
