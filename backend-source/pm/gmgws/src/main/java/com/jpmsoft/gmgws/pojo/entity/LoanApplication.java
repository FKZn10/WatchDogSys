package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 借款申请
 */
@Data
@TableName("Loan_Application")
public class LoanApplication {


    @TableField("id")
    private String id;
    @TableField("La_Department")
    private String laDepartment;

    @TableField("La_Borrower")
    private String laBorrower;

    @TableField("La_Reason_Borrowing")
    private String laReasonBorrowing;

    @TableField("La_Borrower_Money")
    private Float laBorrowerMoney;

    @TableField("La_Receiving_Bank")
    private String laReceivingBank;

    @TableField("La_Receivables_Account")
    private String laReceivablesAccount;

    @TableField("La_Remark")
    private String laRemark;

    @TableField("State")
    private Integer state;

    @TableField("State_Time")
    private LocalDateTime stateTime;

    @TableField("Create_Uid")
    private String createUid;

    @TableField("Create_Uname")
    private String createUname;

    @TableField("Create_Time")
    private LocalDateTime createTime;
}
