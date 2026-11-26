package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 匡算申报清单
 */
@Data
public class InvRoughDeclareDtlModel extends BaseModeRequest {

    private String rddId;
    private String rdId;
    private String rddNumber;
    private String rddParentId;
    private String rddCode;
    private String rddName;
    private String rddUnit;
    private Float rddCount;
    private BigDecimal rddPrice;
    private BigDecimal rddTotal;
    private String rddRemark;
    private Integer rddOrder;
    private Integer rddLevel;
    private Integer rddType;
    private Integer rddState;
    private String rddPath;
    private Integer rddLeafNode;

    private List<InvRoughDeclareDtlModel> childNodes;
}
