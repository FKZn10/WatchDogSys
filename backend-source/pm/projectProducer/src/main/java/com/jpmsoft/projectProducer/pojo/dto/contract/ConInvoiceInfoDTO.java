package com.jpmsoft.projectProducer.pojo.dto.contract;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 发票签收-发票信息
 * @Author: 李文涛 aaron
 * @date: 2024/3/28 15:08
 */
@Data
public class ConInvoiceInfoDTO {
    private String id;
    private String isId;
    private String ciType;
    private String ciBusType;
    private BigDecimal ciCollectAmt;
    private BigDecimal ciTaxAmt;
    private BigDecimal ciCollectTaxAmt;
    private BigDecimal ciTaxRate;
    private String ciCode;
    private String ciNumber;
    private Date ciDate;
    private Integer ciSort;
    private String ciRemark;
}
