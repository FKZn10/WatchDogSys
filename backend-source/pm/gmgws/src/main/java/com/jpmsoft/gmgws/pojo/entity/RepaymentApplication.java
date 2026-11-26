package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 还款申请
 */
@Data
@TableName("Repayment_Application")
public class RepaymentApplication {
    @TableId(value = "id")
    private String id;

    @TableField("Create_Uid")
    private String createUid;

    @TableField("Create_Uname")
    private String createUname;

    @TableField("Create_Time")
    private LocalDateTime createTime;

    @TableField("State")
    private Integer state;

    @TableField("State_Time")
    private LocalDateTime stateTime;

    @TableField("La_Id")
    private String laId;

    @TableField("Ra_Aggregate_Loan")
    private BigDecimal raAggregateLoan;

    @TableField("Ra_Last_Paid")
    private BigDecimal raLastPaid;

    @TableField("Ra_Last_Unpaid")
    private BigDecimal raLastUnpaid;

    @TableField("Ra_Total_Deduction")
    private BigDecimal raTotalDeduction;

    @TableField("Ra_Transferor")
    private String raTransferor;

    @TableField("Ra_Transferor_Bank")
    private String raTransferorBank;

    @TableField("Ra_Transferor_Money")
    private BigDecimal raTransferorMoney;

    @TableField("Ra_Transferor_Time")
    private LocalDateTime raTransferorTime;

    @TableField("Ra_Cash_Money")
    private BigDecimal raCashMoney;

    @TableField("Ra_Cash_Time")
    private LocalDateTime raCashTime;

    @TableField("Ra_Total_Repayment")
    private BigDecimal raTotalRepayment;

    @TableField("Ra_This_Unpaid")
    private BigDecimal raThisUnpaid;
}
