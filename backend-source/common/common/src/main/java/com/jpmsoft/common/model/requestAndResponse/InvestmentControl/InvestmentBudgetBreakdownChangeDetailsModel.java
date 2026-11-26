package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import lombok.Data;

import java.math.BigDecimal;

/**

 */
@Data
public class InvestmentBudgetBreakdownChangeDetailsModel extends InvestmentEstimateBreakdownChangeDetailsModel {
    //估算详情表id
    private String ieDetailId;
    //概算详情表id
    private String ibDetailId;
    //投资概算分解-概算价（万元）
    private BigDecimal isbdBudgetAmount;
    //投资概算分解-概算备注
    private String isbdBudgetRemark;

}
