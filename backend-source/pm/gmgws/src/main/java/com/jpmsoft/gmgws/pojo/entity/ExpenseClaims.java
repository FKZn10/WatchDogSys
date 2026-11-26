package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 经费报销事项
 */
@Data
@TableName("Expense_Claims")
public class ExpenseClaims {


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

    @TableField("Er_Id")
    private String erId;

    @TableField("Ec_Use")
    private String ecUse;

    @TableField("Ec_Reimbursement_Amount")
    private BigDecimal ecReimbursementAmount;

    @TableField("Ec_Con_Id")
    private String ecConId;

    @TableField("Ec_Money")
    private BigDecimal ecMoney;

    @TableField("Ec_Remark")
    private String ecRemark;

}
