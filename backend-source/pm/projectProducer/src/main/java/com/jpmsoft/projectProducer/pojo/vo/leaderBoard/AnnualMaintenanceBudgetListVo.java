package com.jpmsoft.projectProducer.pojo.vo.leaderBoard;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AnnualMaintenanceBudgetListVo extends BaseLeaderBoardInfoVo{
    //修缮单数量
    private Integer repairProjectNum;
    //修缮单的预算金额
    private BigDecimal repairProjectBudget;
}