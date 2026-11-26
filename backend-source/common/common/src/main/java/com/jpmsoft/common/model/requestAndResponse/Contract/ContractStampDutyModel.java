package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 获取合同印花税明细
 */
@Data
public class ContractStampDutyModel extends BaseModeRequest implements Serializable{

    private String pId;
    private String pName;
    private String conId;
    private String conName;
    private String unitConCode;
    private String conType;
    private String conCode;
    private String signDate;
    private String conSecondParty;
    private BigDecimal conMoney;
    private BigDecimal taxableAmount;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private String type;
   }
