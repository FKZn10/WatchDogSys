package com.jpmsoft.jputils.dto.invoice;

import lombok.Data;

@Data
public class TollInvoiceDto extends BaseInvoiceDto {
 private String Entrance;//入口
 private String Exit;//出口
 private String OutDate;//日期
 private String OutTime;//时间
 private String TotalAmount;//金额
 private String Province;//省
 private String City;//市
}
