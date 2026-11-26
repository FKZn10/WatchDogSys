package com.jpmsoft.jputils.dto.invoice;

import lombok.Data;

@Data
public class AirTicketDto {
     private String ServiceType;//发票消费类型。飞机行程单此字段固定输出：交通
     private String name;//姓名
     private String starting_station;//始发站
     private String destination_station;//目的站
     private String flight;//航班号
     private String date;//日期
     private String ticket_number;//电子客票号码
     private String fare;//票价
     private String dev_fund;//民航发展基金/基建费
     private String oil_money;//燃油附加费
     private String other_tax;//其他税费
     private String ticket_rates;//合计金额
     private String start_date;//填开日期
     private String id_no;//身份证号
     private String carrier;//承运人
     private String time;//时间
     private String issued_by;//订票渠道
     private String serial_number;//印刷序号
     private String insurance;//保险费
     private String fare_basis;//客票级别
     private String agent_code;//销售单位号
     private String endorsement;//签注
     private String allow;//免费行李
     private String ck;//验证码
     private String effective_date;//客票生效日期
     private String expiration_date;//有效期截止日期
}
