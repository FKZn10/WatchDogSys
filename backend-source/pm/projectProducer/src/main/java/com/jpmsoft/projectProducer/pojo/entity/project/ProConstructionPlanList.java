package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Pro_Construction_Plan_List")
public class ProConstructionPlanList {
    @TableId(type = IdType.INPUT)
    private String cplId;
    private String pcpId;
    private String cplPType;
    private String pCode;
    private String pName;
    private BigDecimal cplAccumulatedFunds;
    private BigDecimal cplAnnualAccumFund;
    private String cplInvestor;
    private String cplBuildContent;
    private String cplBuildNature;
    private String cplBuildPeriod;
    private BigDecimal cplPlannedInvestment;
    private String cplProgressLastYear;
    private BigDecimal cplMunicipalFunds;
    private BigDecimal cplCountyFunds;
    private BigDecimal cplEnterpriseFunds;
    private BigDecimal cplBankLoans;
    private BigDecimal cplOtherFunds;
    private String cplNowYearProgress;
    private Float cplLandArea;
    private Float cplNewLandArea;
    private String cplConstructionSite;
    private String cplMajorProjects;
    private String cplSpecialPlan;
    private String cplLiableCompany;
    private String cplLiableMan;
    private String cplRemarks;
    private Date cplCreateTime;
    private String cplCreateUid;
    private String cplCreateUname;
    private String cplFile;
    private String cplYear;

    private String cplAgency;
    private String cplCategory;
    private Date cplPlanStart;
    private Date cplPlanEnd;
    private Float cplLength;
    private Float cplWidth;
    private String cplDeclareDep;
    private String cplUseDep;
    private Boolean cplApproval;

    private String cplSource;
    private Boolean cplAppend;
    private Integer state;
    private Date stateTime;
    private String teId;
}
