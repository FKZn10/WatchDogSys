package com.jpmsoft.common.model.response.InvestmentControl;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentTemplateDtlModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 调整事项模板
 */
@Data
public class InvAdjustmentTemplateResponse extends BaseFileRequest {

    private String atId;
    private String atTitle;
    private String atType;
    private String remark;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private String stateTime;
    private List<InvAdjustmentTemplateDtlModel> invAdjustmentTemplateDtls;
}
