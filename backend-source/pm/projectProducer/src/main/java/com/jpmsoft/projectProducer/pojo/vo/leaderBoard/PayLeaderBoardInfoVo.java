package com.jpmsoft.projectProducer.pojo.vo.leaderBoard;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayLeaderBoardInfoVo extends BaseLeaderBoardInfoVo{
    private String conState;
    private String conStateName;
    private BigDecimal contractMoney;
    private BigDecimal payMoney;
}