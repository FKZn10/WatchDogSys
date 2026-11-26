package com.jpmsoft.common.model.response.InvestmentControl;

import com.jpmsoft.common.model.ProjectCommonRes;
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
public class InvRoughDeclareResponse extends ProjectCommonRes {

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
