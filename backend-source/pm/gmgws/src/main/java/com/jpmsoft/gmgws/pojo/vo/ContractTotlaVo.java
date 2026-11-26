package com.jpmsoft.gmgws.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class ContractTotlaVo {

    private String pId;

    private BigDecimal conMoneyTotal;

    private String pName;
    private String conId;

    private String conProjectId;

    private String contractName;
    //  private String projectName;
    private String contractCode;
    private BigDecimal contractAmt;
    private String companyName;
    private BigDecimal paymentAmtTotal;
    private String fkjindu;
    private BigDecimal settlement;

    private List<Map<String, Object>> list;

    private String pCalculateRoughlyAmount;

    private String cpafApplyDetails;

    //文号
    private String ippDocNumber;

    private String cumulativeAmount;

    private String conCode;
    private String conType;

    private String conCreateUserId;

    private String conCreateUserName;


}
