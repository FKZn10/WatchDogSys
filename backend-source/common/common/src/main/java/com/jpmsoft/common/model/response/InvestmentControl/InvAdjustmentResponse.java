package com.jpmsoft.common.model.response.InvestmentControl;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvAdjustmentTemplateDtlModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 调整事项
 */
@Data
public class InvAdjustmentResponse extends BaseFileRequest {

    private String iaId;
    private String pId;
    private String pName;
    private String conId;
    private String conName;
    private String conCode;
    private String conAmount;
    private String tId;
    private String iaType;
    private String remark;
    private BigDecimal totalAmount;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private List<InvAdjustmentDtlModel> invAdjustmentDtls;
}
