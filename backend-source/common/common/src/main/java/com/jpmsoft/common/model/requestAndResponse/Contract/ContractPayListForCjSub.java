package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 城建合同支付台账-下钻支付
 */
@Data
public class ContractPayListForCjSub extends BaseFileRequest {

    private String pId;
    private String conId;
    private String payId;
    private String pCompetentDepartment;
    private BigDecimal payAmount;
    private BigDecimal conAmount;
    private BigDecimal payRate;
    private Date payDate;

}
