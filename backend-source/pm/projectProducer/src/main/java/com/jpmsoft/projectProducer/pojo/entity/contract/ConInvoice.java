package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 合同收款发票
 *
 * @Author: 李文涛 aaron
 * @date: 2024/1/19 8:54
 */
@Data
@TableName("Con_Invoice")
public class ConInvoice extends BaseEntity {

    private String pId;
    private String conId;
    private String ciType;
    private String ciCode;
    private String ciStatus;
    private String ciIncomeStatus;
    private String ciPayerId;
    private String ciPayerUnit;
    private BigDecimal ciXxAmt;
    private BigDecimal ciXxTaxAmt;
    private BigDecimal ciKpAmt;
    private BigDecimal ciKpTaxAmt;
    private String ciDxTaxAmt;
    private String ciRemark;
    private String teId;
}
