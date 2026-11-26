package com.jpmsoft.jputils.dto.invoice;

import lombok.Data;

@Data
public class TaxiReceiptDto {
     private String TaxiNum;//车牌号
     private String Date;//日期
     private String Time;//上下车时间
     private String PickupTime;//上车时间
     private String DropoffTime;//下车时间
     private String Fare;//金额
     private String FuelOilSurcharge;//燃油附加费
     private String CallServiceSurcharge;//叫车服务费
     private String TotalFare;//总金额
     private String Location;//开票城市
     private String Province;//省
     private String City;//市
     private String PricePerkm;//单价
     private String Distance;//里程
}
