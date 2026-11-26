package com.jpmsoft.common.model.requestAndResponse.JpmOa.fund;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceInfoDto extends BaseFileDto {
    private static final long serialVersionUID = -111732863160769164L;
    private String id;
    /**
     * 发票类型
     */
    private String invoiceType;
    /**
     * 发票号码
     */
    private String invoiceNumber;
    /**
     * 发票代码
     */
    private String invoiceCode;
    /**
     * 开票日期
     */
    private Date invoiceDate;
    /**
     * 销售方名称
     */
    private String sellerName;

    /**
     * 销售方纳税人识别号
     */
    private String sellerTaxPayerCode;
    /**
     * 销售方联系方式
     */
    private String sellerContact;
    /**
     * 销售方开户行
     */
    private String sellerBank;
    /**
     * 购方名称
     */
    private String buyerName;
    /**
     * 购方纳税人识别号
     */
    private String buyerTaxPayerCode;
    /**
     * 购方联系方式
     */
    private String buyerContact;
    /**
     * 购方开户行
     */
    private String buyerBank;
    /**
     * 发票校验码
     */
    private String invoiceCheckCode;

    /**
     * "是否成品油发票(Boolean true|false)
     */
    private String isOil;

    /**
     * 税额合计
     */
    private BigDecimal totalTax;

    /**
     * 价格合计
     */
    private BigDecimal totalPrice;
    /**
     * 价税合计
     */
    private BigDecimal totalPriceAndTax;
    /**
     * 数量合计
     */
    private BigDecimal totalQuantity;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 税局查验次数
     */
    private BigDecimal queryCount;

    /**
     * 备注
     */
    private String remark;
    /**
     * 特殊票种类型
     */
    private String specialInvoiceType;
    /**
     * 票种
     */
    private String invoiceKind;
    /**
     * 价税合计（中文大写）
     */
    private String totalPriceAndTaxCn;

    private String invoiceFile;
    private String invoiceFileIds;
    /**
     * 证明文件（批次id）
     */
    private String supportingDocument;
    //证明文件ids
    private String supportingDocumentFileIds;
}
