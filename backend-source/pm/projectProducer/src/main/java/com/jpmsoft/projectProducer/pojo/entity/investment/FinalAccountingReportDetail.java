package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("Final_Accounting_Report_detail")
public class FinalAccountingReportDetail {
    @TableId(type = IdType.INPUT)
    private String id;

    private String fardName;  //费用名称

    private String fardCategory;  //类目

    private String fardSectionType; //标段类型

    private BigDecimal fardApprovedBudgetAmount;  //概算批复金额

    private BigDecimal fardAmountSubmitted;  //送审金额

    private String fardRmk;  //备注

    private String fardSectionName; //标段、费用名称

    private String farId; //父级id

    private Integer fardSort;

    private String fardActionDisplay;

}
