package com.jpmsoft.gmgws.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


//财政支付统计
@Data
@TableName("Financial_Payment")
public class FinancialPayment  {


    @TableId(type = IdType.INPUT)
    private String id;

    private String createUid;

    private String createUname;

    private Date createTime;

    private Integer state;

    private Date stateTime;


    private String fpApplicantName;  //申请单位名称



    private String fpApplicantCode; //申请单位编号

    private String  fpBatchNumber;  //  批次号



    private String  fpApplicationNumber ; //申请单号

    private String   fpUseFunds ; //资金用途

    private String   fpSectoralEc ;  //部门经济分类

    private BigDecimal  fpAmountApplied  ;//申请金额

    private String  fpCollectingUnit ;//收款单位

    private String  fpBankDeposit; // 开户行

    private String   fpBankNumber; // 银行账号

    private String  fpIndexSummary;  //指标摘要;

    private String   fpGovernmentEc; //政府经济分类

    private String   fpFunctionalAccount;  //功能科目

    private String  fpNatureFunds; // 资金性质


    private String  fpSourceFunds; //资金来源


    private String fpBusinessType; //业务类型


    private String fpSettlementMethod; //结算方式


    private String fpPaymentMethod;  //支付方式

}
