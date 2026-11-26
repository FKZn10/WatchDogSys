package com.jpmsoft.jputils.dto.invoice;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusTicketDto extends BaseInvoiceDto{
     private String Date;//日期
     private String Time;//时间
     private String ExitStation;//出发站
     private String Amount;//金额
     private String IdCard;//身份证号
     private String ArrivalStation;//到达站
     private String Name;//姓名
     private String InvoiceTime;//开票日期
}
