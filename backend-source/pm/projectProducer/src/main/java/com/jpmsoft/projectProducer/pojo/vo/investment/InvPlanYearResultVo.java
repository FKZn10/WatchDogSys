package com.jpmsoft.projectProducer.pojo.vo.investment;

import com.jpmsoft.projectProducer.pojo.entity.investment.InvCompleteMonth;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvMonthlySchedule;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class InvPlanYearResultVo {

    private String id;

    private String pId;


    private String ipvPType;


    private List<InvMonthlySchedule> scheduleList;

    private List<InvCompleteMonth>  monthList;

    private String dName;

    private BigDecimal actualPaymentAmt = BigDecimal.ZERO;  //实际支付 汇总

    private BigDecimal  advancePaymentAmt = BigDecimal.ZERO;  //预付 汇总

    private  BigDecimal investmentRatio= BigDecimal.ZERO;  //比例


    private String projectState;
    private String pName;


    private Integer year;  //年

    private String month; //月
    private BigDecimal lastYearipvPlannedInvestment = BigDecimal.ZERO;  //去年计划投资

    private BigDecimal lastYeartotalInvestment = BigDecimal.ZERO;  //去年概算投资




    private BigDecimal lastYearPlannedInvestment = BigDecimal.ZERO;  //去年投资占比

    private BigDecimal lastYearInvestmentProportion ;  //去年积累计划投资占比

    private  String  lastYearPlanImageProgress;   //去年计划形象进度

    private  BigDecimal lastYearActualInvestment = BigDecimal.ZERO;  //去年实际投资

    private  BigDecimal lastYearActualInvestmentProportion = BigDecimal.ZERO;  //去年实际投资占比

    private  BigDecimal lastYearAccumulateInvestment = BigDecimal.ZERO;  //去年积累投资占比

    private String lastYearActualImageProgress; //去年实际形象进度

    private BigDecimal nextYearipvPlannedInvestment = BigDecimal.ZERO;  //明年计划投资

    private BigDecimal nextYearPlannedInvestment = BigDecimal.ZERO;  //明年投资占比

    private BigDecimal nextYeartotalInvestment = BigDecimal.ZERO;  //明年概算投资

    private Float nextYearInvestmentProportion ;  //明年积累计划投资占比

    private  String  nextYearPlanImageProgress;   //明年计划形象进度

    private  BigDecimal nextYearActualInvestment = BigDecimal.ZERO;  //明年实际投资

    private  BigDecimal nextYearActualInvestmentProportion = BigDecimal.ZERO;  //明年实际投资占比

    private  BigDecimal nextYearAccumulateInvestment = BigDecimal.ZERO;  //明年积累投资占比

    private String nextYearActualImageProgress; //明年实际形象进度

    private List<Map<String,Object>> thisYearList;  //今年

    private List<List<InvPlanYearResultVo>> qiTaYear;



}
