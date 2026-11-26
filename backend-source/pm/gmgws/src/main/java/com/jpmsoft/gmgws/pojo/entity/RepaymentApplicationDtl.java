package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/*
还款 子表
 */
@Data
@TableName("Repayment_Application_Dtl")
public class RepaymentApplicationDtl {

    @TableId(value = "id")
    private String id;

    @TableField("Ra_Id")
    private String raId;

    @TableField("Er_Id")
    private String erId;

    @TableField("Rad_Deduction_Last")
    private BigDecimal radDeductionLast;

    @TableField("Rad_Deduction_This")
    private BigDecimal radDeductionThis;

    @TableField("Rad_Er_Money")
    private BigDecimal radErMoney;
}
