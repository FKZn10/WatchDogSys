package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.List;

/**
 */
@Data
public class InvestmentEstimateBreakdownChangeModel extends BaseFileDto {
    private String projectId;
    private String originApplyId;
    //是否需要进行初步设计 0否 1是
    private Integer isNeedPreliminaryDesign;
    //是否采用全过程咨询 0否 1是
    private Integer isAllProcessAsk;
    private List<InvestmentEstimateBreakdownChangeDetailsModel> details;
}
