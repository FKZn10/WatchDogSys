package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 城建合同支付台账
 */
@Data
public class ContractPayListForCj extends BaseFileRequest {

    private String pId;
    private String pName;
    private Integer count;
    private BigDecimal conTotalAmount;
    private BigDecimal payTotalAmount;
    private BigDecimal payTotalRate;
    private String pCompetentDepartment;

}
