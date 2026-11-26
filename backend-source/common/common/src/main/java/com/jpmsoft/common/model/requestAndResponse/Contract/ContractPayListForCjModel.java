package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 城建合同支付台账
 */
@Data
public class ContractPayListForCjModel extends BaseFileRequest {

    private String projectId;
    private String pName;
    private String pCompetentDepartment;
    private String conId;
    private String conName;
    private BigDecimal conMoneyTotal;
    private String payId;
    private BigDecimal payApproval;
    private BigDecimal payPayable;
}
