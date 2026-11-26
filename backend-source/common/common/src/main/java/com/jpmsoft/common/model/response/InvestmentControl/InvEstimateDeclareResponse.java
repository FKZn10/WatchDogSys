package com.jpmsoft.common.model.response.InvestmentControl;

import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvEstimateDeclareDtlModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvRoughDeclareDtlModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 估算申报
 */
@Data
public class InvEstimateDeclareResponse extends ProjectCommonRes {

    private String edId;
    private String pId;
    private String pName;
    private BigDecimal edMoney;
    private String edRemark;
    private String edCreateUid;
    private String edCreateUname;
    private Date edCreateTime;
    private Integer edState;
    private Date edStateTime;
    private String teId;
    private String edUpdateUid;
    private String edUpdateUname;
    private List<InvEstimateDeclareDtlModel> invEstimateDeclareDtls;
}
