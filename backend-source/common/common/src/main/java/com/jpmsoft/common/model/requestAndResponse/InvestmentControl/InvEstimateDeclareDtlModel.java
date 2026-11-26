package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 估算申报清单
 */
@Data
public class InvEstimateDeclareDtlModel extends BaseModeRequest {

    private String eddId;
    private String edId;
    private String eddNumber;
    private String eddParentId;
    private String eddCode;
    private String eddName;
    private String eddUnit;
    private Float eddCount;
    private BigDecimal eddPrice;
    private BigDecimal eddTotal;
    private String eddRemark;
    private Integer eddOeder;
    private Integer eddLevel;
    private Integer eddType;
    private Integer eddState;
    private String eddPath;
    private Integer eddLeafNode;

    private List<InvEstimateDeclareDtlModel> childNodes;
}
