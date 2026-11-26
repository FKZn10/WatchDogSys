package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("inv_plan_year_yw")
public class InvPlanYearYw {
    @TableId(type = IdType.INPUT)
    private String ipvId;
    private String pId;
    private String conId;
    private String ipvConstructionStage;
    private BigDecimal ipvBudgetTotal;
    private String ipvPType;
    private String ipvDId;
    private String ipvCId;
    private String ipvExpenseDetails;
    private String ipvDeclareYear;
    private BigDecimal ipvDoneLastYear;
    private BigDecimal ipvCarryOver;
    private BigDecimal ipvScheduledPlan;
    private BigDecimal ipvReleasedPlan;
    private BigDecimal ipvTotalFunds;
    private BigDecimal ipvPlannedInvestment;
    private BigDecimal ipvPlannedPay;
    private BigDecimal ipvCumulativePay;
    private BigDecimal ipvAnnualInv;
    private BigDecimal ipvAnnualPay;
    private String ipvLImgProgress;
    private String ipvTImgProgress;
    private Date ipvCreateTime;
    private String ipvCreateUid;
    private String ipvCreateUname;
    private Integer ipvState;
    private Date ipvStateTime;
    private BigDecimal ipvAccumulatedSchedule;

    private String ipvConstStatus;
    private String ipvSegments;
    private String ipvStreet;
    private String ipvArea;
    private Date ipvStart;
    private Date ipvEnd;
    private Boolean ipvLibrary;
    private String teId;

    /**
     * 当年是否新开工
     */
    private Boolean ipvNewly;

    /**
     * 当年是否完工
     */
    private Boolean ipvCompleted;

    /**
     * 上报表格编号
     */
    private String reportTableNum;

    private String ipvPhone;
}
