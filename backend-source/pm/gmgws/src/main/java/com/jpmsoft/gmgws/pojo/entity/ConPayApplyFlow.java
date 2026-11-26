package com.jpmsoft.gmgws.pojo.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Con_Pay_Apply_Flow")
public class ConPayApplyFlow {

    @TableField(value = "Cpaf_Id")
    private String cpafId;

    @TableField(value = "Cpa_Id")
    private String cpaId;

    @TableField(value = "P_Id")
    private String pId;

    @TableField(value = "Con_Id")
    private String conId;

    @TableField(value = "Cpaf_Apply_Details")
    private String cpafApplyDetails;

    @TableField(value = "Cpaf_Construction_Unit")
    private String cpafConstructionUnit;

    @TableField(value = "Cpaf_Serial_Number")
    private String cpafSerialNumber;

    @TableField(value = "Cpaf_Payee")
    private String cpafPayee;

    @TableField(value = "Cpaf_Deposit_Bank")
    private String cpafDepositBank;

    @TableField(value = "Cpaf_Bank_Account")
    private String cpafBankAccount;

    @TableField(value = "Cpaf_Contract_Content")
    private String cpafContractContent;

    @TableField(value = "Cpaf_Contract_Progress")
    private String cpafContractProgress;

    @TableField(value = "Cpaf_Total_Last")
    private Double cpafTotalLast;

    @TableField(value = "Cpaf_Apply")
    private Double cpafApply;

    @TableField(value = "Cpaf_Paid_This_Period")
    private Double cpafPaidThisPeriod;

    @TableField(value = "Cpaf_Application")
    private Double cpafApplication;

    @TableField(value = "Cpaf_Remaining_Amount")
    private Double cpafRemainingAmount;

    @TableField(value = "Cpaf_Total_Payment")
    private Double cpafTotalPayment;

    @TableField(value = "Cpaf_Apply_proportion")
    private Double cpafApplyProportion;

    @TableField(value = "Cpaf_Apply_Time")
    private java.sql.Timestamp cpafApplyTime;

    @TableField(value = "Cpaf_Apply_Uname")
    private String cpafApplyUname;

    @TableField(value = "Cpaf_Create_Time")
    private java.sql.Timestamp cpafCreateTime;

    @TableField(value = "Cpaf_Create_Uid")
    private String cpafCreateUid;

    @TableField(value = "State")
    private Integer state;

    @TableField(value = "State_Time")
    private java.sql.Timestamp stateTime;

    @TableField(value = "Con_Id_Sup")
    private String conIdSup;

    @TableField(value = "Cpaf_Historical")
    private Integer cpafHistorical;

    @TableField(value = "Cpaf_Compute")
    private Byte cpafCompute;

    @TableField(value = "Cpaf_New_Payee_Name")
    private String cpafNewPayeeName;

    @TableField(value = "Cpaf_Contract_Money")
    private Double cpafContractMoney;

    @TableField(value = "Te_Id")
    private String teId;
}
