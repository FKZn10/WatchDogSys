package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
经费报销
 */
@TableName("Expense_Reimbursement")
@Data
public class ExpenseReimbursement {

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

    @TableField("Er_Department")
    private String erDepartment;

    @TableField("Er_Applicantr")
    private String erApplicant;

    @TableField("Er_Category")
    private Integer erCategory;

    @TableField("Er_Money_Total")
    private BigDecimal erMoneyTotal;
}
