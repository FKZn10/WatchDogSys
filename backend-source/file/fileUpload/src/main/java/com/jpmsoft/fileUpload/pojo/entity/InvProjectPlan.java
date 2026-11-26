package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 政府投资项目计划
 */
@Data
@TableName("Inv_Project_Plan")
public class InvProjectPlan {
    @TableId(type = IdType.INPUT)
    private String ippId;
    private String ifrId;
    private String pId;
    private String ippConstructionScale;
    private String ippPType;
    private BigDecimal ippAccumulatedFunds;
    private BigDecimal ippFundsArranged;
    private BigDecimal ippAnnualAccumFund;
    private BigDecimal ippReleaseFunds;
    private BigDecimal ippAnnualReleaseFunds;
    private String ippDocNumber;
    private String ippYear;
    private String ippFile;
    private String ippCreateUid;
    private String ippCreateUname;
    private Date ippCreateTime;
    private String ippInvestor;
    private String ippBuildContent;
    private String ippBuildNature;
    private Integer ippBuildPeriod;
    private BigDecimal ippPlanInv;
    private String ippProgressLastYear;
    private BigDecimal ippMunicipalFunds;
    private BigDecimal ippCountyFunds;
    private BigDecimal ippEnterpriseFunds;
    private BigDecimal ippBankLoans;
    private BigDecimal ippOtherFunds;
    private String ippNowYearProgress;
    private String ippLandArea;
    private String ippNewLandArea;
    private String ippConstructionSite;
    private String ippMajorProjects;
    private String ippSpecialPlan;
    private String ippLiableCompany;
    private String ippLiableMan;
    private String ippRemarks;
}
