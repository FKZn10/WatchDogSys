package com.jpmsoft.jputils.dto.invoice;

import lombok.Data;

@Data
public class PrintedInvoiceDto {
     private String InvoiceDate;//开票日期
     private String AmountInFiguers;//合计金额小写
     private String AmountInWords;//合计金额大写
     private String MachineNum;//机打号码
     private String CheckCode;//校验码
     private String SellerName;//销售方名称
     private String SellerRegisterNum;//销售方纳税人识别号
     private String PurchaserName;//购买方名称
     private String PurchaserRegisterNum;//购买方纳税人识别号
     private String TotalTax;//合计税额
     private String Province;//省
     private String City;//市
     private String Time;//时间
     private String SheetNum;//联次
     private String CommodityName;//商品名称
     private String CommodityUnit;//商品单位
     private String CommodityPrice;//商品单价
     private String CommodityNum;//商品数量
     private String CommodityAmount;//商品金额
}
