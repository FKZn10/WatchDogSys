package com.jpmsoft.jputils.dto.invoice;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RollNormaInvoiceDto extends BaseInvoiceDto {
     private String InvoiceType;//发票名称
     private String MachineNum;//机打号码。仅增值税卷票含有此参数
     private String MachineCode;//机器编号。仅增值税卷票含有此参数
     private String InvoiceDate;//开票日期
     private String PurchaserName;//购方名称
     private String PurchaserRegisterNum;//购方纳税人识别号
     private String SellerName;//销售方名称
     private String SellerRegisterNum;//销售方纳税人识别号
     private String TotalTax;//价税合计
     private String AmountInWords;//合计金额(大写)
     private String AmountInFiguers;//合计金额(小写)
     private String Payee;//收款人
     private String CheckCode;//校验码。增值税专票无此参数
     private String Province;//省
     private String City;//市
     private String CommodityName;//货物名称
     private String CommodityNum;//数量
     private String CommodityPrice;//单价
     private String CommodityAmount;//金额
}
