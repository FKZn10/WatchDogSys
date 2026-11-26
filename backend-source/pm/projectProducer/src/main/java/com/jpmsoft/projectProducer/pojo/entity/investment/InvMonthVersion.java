package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Inv_Month_Version")
public class InvMonthVersion {
    @TableId(type = IdType.INPUT)
    private String icmId;
    private String ipvId;
    private String pId;
    private String icmDId;
    private String icmReportingMonth;
    private BigDecimal icmMonthlyInvestment;
    private Double icmMonthlyProportion;
    private BigDecimal icmAnnualInvestment;
    private Double icmAnnualProportion;
    private BigDecimal icmBudgetInvestment;
    private Double icmBudgetProportion;
    private String icmImgProgress;
    private String icmCompleted;
    private String icmIncompleteReason;
    private String icmMeasures;
    private String icmImg;
    private String icmCreateUid;
    private String icmCreateUname;
    private Date icmCreateTime;
    private Integer icmState;
    private Date icmStateTime;
    private String icmReportingYear;
    private BigDecimal icmLanInvAmt;
    private BigDecimal icmDeviceInvAmt;
    private BigDecimal icmInfrastructureInvAmt;
    private BigDecimal icmOtherInvAmt;
    private BigDecimal icmLanExpAmt;
    private BigDecimal icmDeviceExpAmt;
    private BigDecimal icmInfrastructureExpAmt;
    private BigDecimal icmOtherExpAmt;
    private BigDecimal icmPayComposition;
    private String icmAwards;
    private String icmRemark;
    private String icmAnalysisReport;
    private Integer icmPersonnelNum;
    private String icmImageProgress;
    private Float icmConstructionArea;
    private Float icmNewConsArea;
    private Float icmCompletedArea;
    private BigDecimal icmOutputValue;
    private BigDecimal icmCompletionValue;
    private String icmConstructionAreaUnit;
    private String icmNewConsAreaUnit;
    private String icmCompletedAreaUnit;
    private BigDecimal icmActualCompletion;
    private BigDecimal icmBuildProject;
    private BigDecimal icmInstallProject;
    private BigDecimal icmEquipmentPurchase;
    private BigDecimal icmOtherExpenses;
    private BigDecimal icmActualReport;
    private BigDecimal icmMonthInvestTask;
    private BigDecimal icmLagInvest;
    private BigDecimal icmCumulativeInvest;
    private BigDecimal icmNotReported;
    private String teId;
    private Date actualStartTime;
    private Date actualEndTime;
    private String transferStatus;
    private String currentMonthStart;
    private String cumulativeStart;
    private Date planStartTime;
    private String planCompleteResult;
    private String currentMonthComplete;
    private String cumulativeComplete;
    private Date planEndTime;
    private String completeResult;
    private BigDecimal monthCumulativeInv;
    private Float monthCumulativeInvProportion;
    private String icmProjectStage;
    private String conId;
    private String pCompetentDepartments;
    private Date currentTimeStart;
    private Date currentTimeEnd;
    private Date version;
}