package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("Settlement_Audit_Detail_Change")
public class SettlementAuditDetailChange {

    @TableId(type = IdType.INPUT)
    private String id;
    private String settlementAuditChangeId;
    private String contractId;
    private String contractName;
    private BigDecimal contractTotalMoney;
    private String settlementFirstId;
    private BigDecimal sfaSettlementFirstPrice;
    private BigDecimal settlementAuditPrice;
    private Integer delFlag;
    private Integer status;
}
