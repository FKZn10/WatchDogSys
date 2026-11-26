package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 资金计划申请明细
 */
@Data
@TableName("Fund_Plan_Modify")
public class FundPlanModify {

    @TableId(type = IdType.INPUT)
    private String fpmId;
    private String pId;
    private Integer fpmYear;
    private BigDecimal fpmYearCapitalScale;
    private BigDecimal fpmModify;
    private BigDecimal fpmModifyTotal;
    private BigDecimal fpmCapitalScale;
    private BigDecimal fpmNotFunds;
    private BigDecimal fpmReceivedFunds;
    private BigDecimal fpmReleaseAmountTotal;
    private String fpmRemarks;
    private String fpmBudgetItemName;
}
