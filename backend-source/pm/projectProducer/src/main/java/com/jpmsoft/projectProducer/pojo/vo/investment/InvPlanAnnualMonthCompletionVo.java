package com.jpmsoft.projectProducer.pojo.vo.investment;

import lombok.Data;

@Data
public class InvPlanAnnualMonthCompletionVo {
    //年度投资任务（万元）
    private Double annualInvestment;
    //今年累计完成投资额（万元）
    private Double cumulativeInvestment;
    //当月完成投资额（万元）
    private Double monthlyInvestment;
    //当月计划投资额（万元）
    private Double monthlyPlanInvestment;
    //今年累计完成投资额（万元）占年度投资任务（万元）比例
    private Double annualInvestmentProportion;
    //当月完成投资额（万元）占当月计划投资额（万元）比例
    private Double monthlyInvestmentProportion;
}
