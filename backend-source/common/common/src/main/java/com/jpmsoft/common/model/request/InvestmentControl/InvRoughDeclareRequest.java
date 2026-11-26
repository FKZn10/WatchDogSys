package com.jpmsoft.common.model.request.InvestmentControl;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvRoughDeclareDtlModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 匡算申报
 */
@Data
public class InvRoughDeclareRequest extends BaseFileRequest {

    private String rdId;
    private String pId;
    private String pName;
    private BigDecimal rdMoney;
    private String rdRemark;
    private String rdCreateUid;
    private String rdCreateUname;
    private Date rdCreateTime;
    private Integer rdState;
    private Date rdStateTime;
    private String teId;
    private String rdUpdateUid;
    private String rdUpdateUname;
    private List<InvRoughDeclareDtlModel> invRoughDeclareDtls;
}
