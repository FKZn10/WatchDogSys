package com.jpmsoft.jputils.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yb.Sun
 * @date 2025/1/20 15:20
 */
@Data
public class InvoiceInfoRequest {
    /**
     * 发票代码(电子发票普通或增值税专用发票不填，其他类型发票必填)
     * Reimburse_Invoice_Code
     */
    private String fpdm;
    /**
     * 发票号码
     * Reimburse_Invoice_Number
     */
    private String fphm;

    /**
     * 开票日期
     * Reimburse_Invoice_Time
     */
    private Date kprq;
    //不含税金额：发票类型(专票)、(货运票)、(机动车票)、(二手车票)必传
    //Reimburse_Invoice_No_Tax_Amount
    private BigDecimal noTaxAmount;

    //校验码后6位 发票类型(普票)、(电子票)、(卷式发票)、(通行费)必传
    //Reimburse_Invoice_Check_Code
    private String checkCode;
    //价税合计：电子发票（普通或增值税专用）发票必填，其他类型发票不填
    //Reimburse_Invoice_Amount
    private BigDecimal jshj;

}
