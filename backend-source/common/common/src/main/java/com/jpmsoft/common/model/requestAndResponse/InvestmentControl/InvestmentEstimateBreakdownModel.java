package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yb.Sun
 * @date 2024/10/25 15:27
 */
@Data
public class InvestmentEstimateBreakdownModel extends BaseFileDto {
    private String projectId;
    private String projectName;
    //是否需要进行初步设计 0否 1是
    private Integer isNeedPreliminaryDesign;
    //是否采用全过程咨询 0否 1是
    private Integer isAllProcessAsk;
    // 概算总金额
    private BigDecimal totalInvestment;
    // 是否需要概算审核  01 是  02 否
    private String estReview;
    private List<InvestmentEstimateBreakdownDetailsModel> details;

    private String investMentFile;

    private String investMentFileIds;



}
