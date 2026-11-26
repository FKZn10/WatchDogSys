package com.jpmsoft.projectConsumer.pojo.vo.contract;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class SettlementAuditDetailVo {

    private String id;
    private String settlementAuditId;
    private String contractId;
    private String contractName;
    private BigDecimal contractTotalMoney;
    private String settlementFirstId;
    private BigDecimal sfaSettlementFirstPrice;
    private BigDecimal settlementAuditPrice;
    private Integer delFlag;
    private Integer status;
}
