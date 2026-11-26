package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 集团账户收支明细
 */
@Data
public class EngineeringSpecialAccountForJT extends BaseFileRequest {

    /*
    项目ID
     */
    private String projectId;
    /*
    项目名称
     */
    private String pName;
    /*
    类型
     */
    private String esaJtType;
    /*
    发生日期
     */
    private Date esaJtDate;
    /*
    收付款单位
     */
    private String esaJtUnit;
    /*
    发生金额
     */
    private String esaJtAmount;
    /*
    账户余额
     */
    private BigDecimal esaJtAccountBalance;

    private BigDecimal amount;
}
