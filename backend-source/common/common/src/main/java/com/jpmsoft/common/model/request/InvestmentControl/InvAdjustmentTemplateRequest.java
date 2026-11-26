package com.jpmsoft.common.model.request.InvestmentControl;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentTemplateDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvRelatedRelationshipsModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 调整事项模板
 */
@Data
public class InvAdjustmentTemplateRequest extends BaseFileRequest {

    private String atId;
    private String atTitle;
    private String atType;
    private String remark;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private List<InvAdjustmentTemplateDtlModel> invAdjustmentTemplateDtls;
}
