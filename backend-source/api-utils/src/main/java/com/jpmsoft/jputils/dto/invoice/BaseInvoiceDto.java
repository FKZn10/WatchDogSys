package com.jpmsoft.jputils.dto.invoice;

import lombok.Data;

@Data
public class BaseInvoiceDto {
    private String ServiceType;//发票消费类型。不同消费类型输出：餐饮、电器设备、通讯、服务、日用品食品、医疗、交通、其他
    private String InvoiceCode;//发票代码
    private String InvoiceNum;//发票号码
}
