package com.jpmsoft.projectProducer.pojo.vo.leaderBoard;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class QsLeaderBoardInfoListVo extends BaseLeaderBoardInfoListVo {
    /**
     * 编号
     */
    private String ptCode;
    /**
     * 巡检类型
     */
    private String ptType;
    /**
     * 检查日期
     */
    private Date ptEvaluationTime;
    /**
     * 要求整改时间
     */
    private Date qsrRectifyRequireTime;
    /**
     * 实际整改时间
     */
    private Date qsrRectifyActualTime;
    //超期天数
    private BigDecimal overdueDays;
    //整改检察人员
    private String ptGroupMemberName;
    private String ptContractorName;
    private String ptSupervisorName;
    /**
     * 问题数量
     */
    private Integer qsRecordCount = 0;
    /**
     * 整改数量
     */
    private Integer qsFinishCount = 0;
    /**
     * 回复数量
     */
    private Integer qsReplyCount = 0;
    /**
     * 闭合率
     */
    private BigDecimal qsFinishRate;
    /**
     * 回复率
     */
    private BigDecimal qsReplyRate;
}