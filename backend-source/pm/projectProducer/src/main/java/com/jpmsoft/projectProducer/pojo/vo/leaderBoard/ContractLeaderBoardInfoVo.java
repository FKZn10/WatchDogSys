package com.jpmsoft.projectProducer.pojo.vo.leaderBoard;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContractLeaderBoardInfoVo extends BaseLeaderBoardInfoVo {
    private String contractType;
    private String contractStatus;
    private String contractTypeName;
    private String contractStatusName;
    private BigDecimal contractSum;
    private BigDecimal contractSumYearAll;
    private BigDecimal contractSumRate;
    private BigDecimal contractMoneyTotal;
    private BigDecimal contractMoneyTotalRate;
    private BigDecimal contractMoneyTotalYearAll;
}