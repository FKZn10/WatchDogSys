package com.jpmsoft.projectProducer.pojo.vo.contract;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContractLedgerForWJTVO {

    private String contractId;

    private String contractName;

    private String projectName;

    private String projectId;

    private String contractCode;

    private String contractType;

    // 合同金额
    private BigDecimal contractMoneyTotal;

    // 合同费率
    private BigDecimal contractRate;

    // 合同金额或费率：合同金额、费率、其他金额
    private String contractMoneyRate;

    private String contractPartyAId;

    private String contractPartyAName;

    private String contractPartyBName;

    private String contractPartyBId;

    // 签订日期
    private String contractSignDate;

    // 累计支付
    private BigDecimal totalPay = BigDecimal.ZERO;

    // 累计发票
    private BigDecimal totalInvoice = BigDecimal.ZERO;

    // 累计变更
    private BigDecimal totalChange = BigDecimal.ZERO;

    private String createUid;

    private String createUname;

    private String createTime;

    private Integer state;

    // 合同规模（元）：合同金额->合同金额、费率->估算价
    private BigDecimal contractScale;

    private BigDecimal contractMoneyTentative;

}
