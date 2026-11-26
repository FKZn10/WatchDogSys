package com.jpmsoft.jputils.dto.invoice;

import lombok.Data;

@Data
public class QuotaInvoiceDto {
     private String ServiceType;//发票消费类型。定额发票此字段固定输出：交通
     private String invoice_code;//发票代码
     private String invoice_number;//发票号码
     private String invoice_rate;//金额
     private String invoice_rate_in_figure;//金额小写
     private String invoice_rate_in_word;//金额大写
     private String Province;//省
     private String City;//市
     private String Location;//发票所在地
     private String invoice_type;//发票名称
}
