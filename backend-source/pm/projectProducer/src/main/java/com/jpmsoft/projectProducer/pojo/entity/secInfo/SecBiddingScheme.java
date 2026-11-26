package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招标方案
 */
@Data
@TableName("Sec_Bidding_Scheme")
public class SecBiddingScheme {

    @TableId(type = IdType.INPUT)
    private String bsId;
    private String secId;
    private BigDecimal bsProjectInvestment;
    private BigDecimal bsAnnualInvestment;
    private String bsApprovalType;
    private BigDecimal bsApprovedAmount;
    private Integer bsWhetherStandard;
    private String bsApprovalComments;
    private String bsCreateUid;
    private Date bsCreateTime;
    private String bsCreateUname;
    private Integer bsState;
    private Date bsStateTime;
    private String bsApprovalDoc;
    private String bsBasisDoc;
    private String bsBasisDocFile;
    private String bsContractType;
    private String bsContractNecessary;
    private String bsContractContent;
    private String bsDurationCondition;
    private String bsContractAmountType;
    private String bsContractAmount;
    private String bsPrerequisites;
    private String bsRegistration;
    private String bsContractingUnit;
    private String bsQualificationContractor;
    private String bsDetermineReason;
    private String bsOther;
    private BigDecimal bsHighestPrice;
    private String bsCalculationMethod;
    private String bsAbstract;
    private String bsProjectOverview;
    private String bsApprovalMatter;
    private String bsApprovalFlow;
    private String bsContractingType;
    private String teId;
    private String bsLeaderQualification;
    private String bsInstructions;

    private String bsTenderingUnit;
    private String bsAgencyId;
    private String bsAgentCon;
    private BigDecimal bsAgentConAmt;
    private String bsAgentProcure;
    private BigDecimal bsAgentProcureAmt;
    private String bsInvestType;
    private String bsBidPlatform;
    private BigDecimal bsControlPrice;
    private String bsPmAsk;
    private String bsConsortiumBid;
    private String bsEvaluation;
    private String bsCalibration;
    private BigDecimal bsMoney;
}
