package com.jpmsoft.projectProducer.pojo.vo.investment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinalAccountingReportDetailVo {
    private String id;

    private String fardName;  //费用名称

    private String fardCategory;  //类目

    private String fardSectionType; //标段类型

    private BigDecimal fardApprovedBudgetAmount;  //概算批复金额

    private BigDecimal fardAmountSubmitted;  //送审金额

    private String fardSectionName; //标段、费用名称

    private String fardRmk;  //备注

    private String farId; //父级id

    private Integer fardSort;

    private String fardActionDisplay;

}
