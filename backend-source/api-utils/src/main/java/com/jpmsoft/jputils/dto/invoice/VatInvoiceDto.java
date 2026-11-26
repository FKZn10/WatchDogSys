package com.jpmsoft.jputils.dto.invoice;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VatInvoiceDto extends BaseInvoiceDto {
    private String InvoiceTypeOrg;//发票名称
    private String InvoiceType;//增值税发票的细分类型。 不同细分类型的发票输出：普通发票、专用发票、电子普通发票、电子专用发票、通行费电子普票、区块链发票、通用机打电子发票、电子发票(专用发票)、电子发票(普通发票)
    private String InvoiceTag;//增值税发票左上角标志。 包含：通行费、销项负数、代开、收购、成品油、其他
    private String InvoiceCodeConfirm;//发票代码的辅助校验码，一般业务情景可忽略
    private String InvoiceNumConfirm;//发票号码的辅助校验码，一般业务情景可忽略
    private String CheckCode;//校验码。增值税专票无此参数
    private String InvoiceNumDigit;//数电票号码。密码区部分的数电票号码，仅在纸质的数电票上出现
    private String InvoiceDate;//开票日期
    private String PurchaserName;//购方名称
    private String PurchaserRegisterNum;//购方纳税人识别号
    private String PurchaserAddress;//购方地址及电话
    private String PurchaserBank;//购方开户行及账号
    private String Password;//密码区
    private String Province;//省
    private String City;//市
    private String SheetNum;//联次信息。专票第一联到第三联分别输出：第一联：记账联、第二联：抵扣联、第三联：发票联；普通发票第一联到第二联分别输出：第一联：记账联、第二联：发票联
    private String Agent;//否代开
    private String OnlinePay;//子支付标识。仅区块链发票含有此参数
    private String SellerName;//销售方名称
    private String SellerRegisterNum;//销售方纳税人识别号
    private String SellerAddress;//销售方地址及电话
    private String SellerBank;//销售方开户行及账号
    private String TotalAmount;//合计金额
    private String TotalTax;//合计税额
    private String AmountInWords;//价税合计(大写)
    private String AmountInFiguers;//价税合计(小写)
    private String Payee;//收款人
    private String Checker;//复核
    private String NoteDrawer;//开票人
    private String Remarks;//备注
    private String CommodityName;//货物名称
    private String CommodityType;//规格型号
    private String CommodityUnit;//单位
    private String CommodityNum;//数量
    private String CommodityPrice;//单价
    private String CommodityAmount;//金额
    private String CommodityTaxRate;//税率
    private String CommodityTax;//税额
    private String CommodityPlateNum;//车牌号。仅通行费增值税电子普通发票含有此参数
    private String CommodityVehicleType;//类型。仅通行费增值税电子普通发票含有此参数
    private String CommodityStartDate;//通行日期起。仅通行费增值税电子普通发票含有此参数
    private String CommodityEndDate;//通行日期止。仅通行费增值税电子普通发票含有此参数
}
