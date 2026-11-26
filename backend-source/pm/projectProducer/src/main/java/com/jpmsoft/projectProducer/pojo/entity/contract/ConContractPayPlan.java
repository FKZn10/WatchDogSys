package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同支付计划
 */
@Data
@TableName("Con_Contract_Pay_Plan")
public class ConContractPayPlan {

    @TableId(type = IdType.INPUT)
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

    //是否申请
    private String cppShouldApply;

    /**
     * 实际付款日期
     */
    @TableField("cpp_pay_date")
    private Date cppPayDate;

    /**
     * 实际付款金额
     */
    @TableField("cpp_pay_amount")
    private BigDecimal cppPayAmount;

    /**
     * 资金来源
     */
    @TableField("cpp_pay_source")
    private String cppPaySource;

}