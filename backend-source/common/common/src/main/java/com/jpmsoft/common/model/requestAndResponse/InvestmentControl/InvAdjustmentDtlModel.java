package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 调整事项清单
 */
@Data
public class InvAdjustmentDtlModel extends BaseModeRequest {

    private String adId;
    private String iaId;
    private String adNum;
    private String adContent;
    private String adInfo;
    private BigDecimal adApplyAmount;
    private BigDecimal adReviewAmount;
    private String adRemark;
    private Integer adOrder;
    private String adParentId;
    private List<InvAdjustmentDtlModel> invAdjustmentDtls;
}
