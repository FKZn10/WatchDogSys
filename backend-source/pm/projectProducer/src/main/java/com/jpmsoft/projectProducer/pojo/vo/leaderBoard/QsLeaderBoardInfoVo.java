package com.jpmsoft.projectProducer.pojo.vo.leaderBoard;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class QsLeaderBoardInfoVo extends BaseLeaderBoardInfoVo {
    private BigDecimal questionSum;
    private BigDecimal questionFinishedSum;
}