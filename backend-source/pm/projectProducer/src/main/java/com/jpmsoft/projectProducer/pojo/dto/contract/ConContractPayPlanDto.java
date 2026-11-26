package com.jpmsoft.projectProducer.pojo.dto.contract;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ConContractPayPlanDto {
    private String cppId;
    private String conId;
    private String cppName;
    private String cppCode;
    private String cppClause;
    private BigDecimal cppMoney;
    private Float cppRatio;
    private Float cppRatioPayTo;
    private String cppRemark;
    private Integer cppSort;
    private Date cppCreateTime;
    private String cppExplain;

    private String cppConJdlx;
    private String cppZftjUnit;
    private Integer cppZftjNumber;
    private String cppZftjLx;
    private Boolean cppIsCompleted;
    private String cppBasics;
    private String cppMode;
    private String cppConditionValue;
    private String cppConditionType;
    /**
     *  是否已申报
     */
    private Boolean cppStatus;

    // 标段类型
    private String isbdSectionType;

    // 估算价
    private BigDecimal isbdAmount;

    private String pddThisNode;


    /**
     * 实际付款日期
     */
    private Date cppPayDate;

    /**
     * 实际付款金额
     */
    private BigDecimal cppPayAmount;

    /**
     * 资金来源
     */
    private String cppPaySource;


}
