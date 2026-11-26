package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 城建合同支付台账-下钻合同
 */
@Data
public class ContractPayListForCjDTO extends BaseFileRequest {

    private String pId;
    private String conId;
    private String conName;
    private String conSecondCompanyName;
    private String pCompetentDepartment;
    private BigDecimal payTotalAmount;
    private BigDecimal payTotalRate;
    private BigDecimal conAmount;
}
