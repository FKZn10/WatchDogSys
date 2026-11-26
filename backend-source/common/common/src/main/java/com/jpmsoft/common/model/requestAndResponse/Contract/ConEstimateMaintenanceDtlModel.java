package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 合同
 */
@Data
public class ConEstimateMaintenanceDtlModel extends BaseFileRequest {

    private String cemdId;
    private String CemId;
    private String cemdCost;
    private String cemdCostName;
    private String cemdMainCost;
    private String cemdMainCostName;
    private String cemdCompany;
    private String cemdQuantities;
    private BigDecimal cemdUnitPrice;
    private BigDecimal cemdInvestment;
    private String cemdRemarks;
    private BigDecimal cemdTotalExpenses;
    private Integer cemdExpenseType;
    private String cemdStageType;
}
