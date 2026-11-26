package com.jpmsoft.common.model.response.Contract;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同计量计价合同选择数据源响应
 */
@Data
public class ContractMeasurementContractResponse {
    private String conId;
    private String conName;
    private String conCode;
    private String conType;
    private String conSup;
    private BigDecimal conMoneyTotal;
    private String conCreateUserName;
    private Date conCreateTime;
}
