package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同
 */
@Data
public class ConEstimateModel extends BaseFileRequest {

    private String ceId;
    private String ConId;
    private String ceCost;
    private String ceCostName;
    private String ceMainCost;
    private String ceMainCostName;
    private String ceCompany;
    private String ceQuantities;
    private BigDecimal ceUnitPrice;
    private BigDecimal ceInvestment;
    private String ceRemarks;
    private BigDecimal ceTotalExpenses;
    private Integer ceExpenseType;
    private String ceStageType;
}
