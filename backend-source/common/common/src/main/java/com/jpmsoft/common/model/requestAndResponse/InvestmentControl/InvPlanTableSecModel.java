package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 投资计划表
 */
@Data
public class InvPlanTableSecModel extends BaseModeRequest {
    //序号
    private String num;
    //项目及费用名称
    private String costName;
    //概算ID
    private String budgetId;
    //父节点ID
    private String budgetParentId;
    //总概算投资额
    private BigDecimal totalAmount;
    //已使用投资额
    private BigDecimal alReadyAmount;
    //未使用投资额
    private BigDecimal noAmount;
    //已使用占比
    private BigDecimal rate;
    //颜色
    private String color;

    //排序
    private Integer order;

    private List<InvPlanTableSecModel> childData;

}
