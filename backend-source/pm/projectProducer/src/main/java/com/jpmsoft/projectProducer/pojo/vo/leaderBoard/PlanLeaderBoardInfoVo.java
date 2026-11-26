package com.jpmsoft.projectProducer.pojo.vo.leaderBoard;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PlanLeaderBoardInfoVo extends BaseLeaderBoardInfoVo{
    private BigDecimal fundPlan;
    private BigDecimal fundActual;
    private BigDecimal fundActualTotal;
}