package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 */
@Data
@TableName("Investment_Budget_Breakdown_change_Details")
public class InvestmentBudgetBreakdownChangeDetails extends InvestmentEstimateBreakdownChangeDetails{
    //估算详情表id
    private String ieDetailId;
    //概算详情表id
    private String ibDetailId;
    //投资概算分解-概算价（万元）
    private BigDecimal isbdBudgetAmount;
    //投资概算分解-概算备注
    private String isbdBudgetRemark;
    private String changeType;

}
