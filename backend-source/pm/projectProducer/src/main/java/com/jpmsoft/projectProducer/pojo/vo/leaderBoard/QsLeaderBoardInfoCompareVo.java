package com.jpmsoft.projectProducer.pojo.vo.leaderBoard;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class QsLeaderBoardInfoCompareVo extends QsLeaderBoardInfoVo {
    private BigDecimal questionSumCompare;
    private BigDecimal questionFinishedSumCompare;
}