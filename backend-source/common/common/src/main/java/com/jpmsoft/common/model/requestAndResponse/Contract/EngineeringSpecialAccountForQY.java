package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 企业账户收支明细
 */
@Data
public class EngineeringSpecialAccountForQY extends BaseFileRequest {

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
    private String esaQyType;
    /*
    发生日期
     */
    private Date esaQyDate;
    /*
    收付款单位
     */
    private String esaQyUnit;
    /*
    发生金额
     */
    private String esaQyAmount;
    /*
    账户余额
     */
    private BigDecimal esaQyAccountBalance;

    private String pCompetentDepartment;

    private BigDecimal amount;
}
