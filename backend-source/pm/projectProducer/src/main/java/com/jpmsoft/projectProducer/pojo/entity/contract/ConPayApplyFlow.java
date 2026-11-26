package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付资金申请详情
 */
@TableName(value = "Con_Pay_Apply_Flow")
@Data
public class ConPayApplyFlow {
    @TableId(value = "Cpaf_Id", type = IdType.INPUT)
    public String cpafId;
    public String cpaId;
    public String pId;
    public String conId;
    //申请资金详情
    public String cpafApplyDetails;
    //建设单位
    public String cpafConstructionUnit;
    //流水号
    public String cpafSerialNumber;
    //收款单位
    public String cpafPayee;

    private String payId;
    //开户银行
    public String cpafDepositBank;
    //银行账号
    public String cpafBankAccount;
    //合同内容
    public String cpafContractContent;
    //合同进度
    public String cpafContractProgress;
    //截至上次累计已支付金额
    public BigDecimal cpafTotalLast;
    //本期应付金额
    public BigDecimal cpafApply;
    //本期已支付金额
    public BigDecimal cpafPaidThisPeriod;
    //本次申请金额
    public BigDecimal cpafApplication;
    //本期剩余未支付金额
    public BigDecimal cpafRemainingAmount;
    //截至本次累计已支付金额
    public BigDecimal cpafTotalPayment;
    //支付比例
    public BigDecimal cpafApplyProportion;
    public Date cpafApplyTime;
    public String cpafApplyUname;
    public Date cpafCreateTime;
    public String cpafCreateUid;
    public Integer state;
    public Date stateTime;
    //补充合同id（多个）
    public String conIdSup;
    public Integer cpafHistorical;
    private Boolean cpafCompute;

    private String cpafCreateUname;
    //收款单位名称
    public String cpafNewPayeeName;
    //合同金额
    public BigDecimal cpafContractMoney;
    private String teId;

    public BigDecimal cpafAmountInvoice;
    private String cpafInvoiceCode;
    public BigDecimal cpafAuditPrice;
    public BigDecimal cpafAmountIssued;
    public BigDecimal cpafAmountDisbursement;
}
