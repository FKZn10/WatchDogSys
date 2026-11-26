package com.jpmsoft.projectProducer.pojo.vo.leaderBoard;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PreProjectLeaderBoardInfoVo extends BaseLeaderBoardInfoVo {
    private String projectType;
    private String projectStatus;
    private String projectTypeName;
    private String projectStatusName;
    private BigDecimal projectSum;
    private BigDecimal projectSumYearAll;
    private BigDecimal projectSumRate;
    private BigDecimal projectInvTotal;
    private BigDecimal projectInvTotalYearAll;
    private BigDecimal projectInvTotalRate;
}