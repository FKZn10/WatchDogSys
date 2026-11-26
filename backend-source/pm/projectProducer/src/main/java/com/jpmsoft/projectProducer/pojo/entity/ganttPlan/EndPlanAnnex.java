package com.jpmsoft.projectProducer.pojo.entity.ganttPlan;

import lombok.Data;

@Data
public class EndPlanAnnex {
    private String competentDepartmentName; // 单位名称

    private String type;//计划内/外

    // 年度指标
    private int annualCompleteNum;
    private int annualCompleteRecordsNum;
    private int annualPRemoveNum;
    private int annualAccountSubmitNum;

    // 1-x月
    private int completeNum; // 竣工验收完成数
    private double completeRate; // 竣工验收完成率

    private int completeRecordsNum; // 竣工备案完成数
    private double completeRecordsRate; // 竣工备案完成率

    private int pRemoveNum; // 工程移交完成数
    private double pRemoveRate; // 工程移交完成率

    private int accountSubmitNum; // 决算上报完成数
    private double accountSubmitRate; // 决算上报完成率

    private int totalNeed;
    private int total;
    private double totalRate;
}
