package com.jpmsoft.projectProducer.pojo.vo.leaderBoard;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class PlanLeaderBoardInfoListVo extends BaseLeaderBoardInfoListVo{
    private BigDecimal contractMoney;//资金需求
    private BigDecimal payMoney;//合同支付
}