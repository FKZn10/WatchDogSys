package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yb.Sun
 * @date 2024/11/1 14:44
 */
@Data
public class InvestmentBudgetBreakdownModel extends BaseFileDto {
    //估算表id
    private String ieId;
    private String projectId;
    private String projectName;
    //概算批复文件上传
    private String projectBudgetFile;
    private String projectBudgetFileIds;
    //概算总投资（万元）
    private BigDecimal projectBudgetInvestmentAmount;
    //是否需要进行初步设计 0否 1是
    private Integer isNeedPreliminaryDesign;
    //是否采用全过程咨询 0否 1是
    private Integer isAllProcessAsk;
    private List<InvestmentBudgetBreakdownDetailsModel> details;

    // 估算总投资
    private BigDecimal totalInvestment;

    private String investMentFile;

    private String investMentFileIds;

}
