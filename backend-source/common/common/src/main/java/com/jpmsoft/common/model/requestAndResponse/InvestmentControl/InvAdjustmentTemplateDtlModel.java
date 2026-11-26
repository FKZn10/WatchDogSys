package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 调整事项模板清单
 */
@Data
public class InvAdjustmentTemplateDtlModel extends BaseModeRequest {

    private String atdId;
    private String atId;
    private String atNum;
    private String atContent;
    private String atInfo;
    private BigDecimal atApplyAmount;
    private BigDecimal atReviewAmount;
    private String atRemark;
    private Integer atOrder;
    private String atParentId;
    private List<InvAdjustmentTemplateDtlModel> invAdjustmentTemplateDtls;
}
