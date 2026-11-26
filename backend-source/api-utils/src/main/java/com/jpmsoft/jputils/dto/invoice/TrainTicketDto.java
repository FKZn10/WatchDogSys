package com.jpmsoft.jputils.dto.invoice;

import lombok.Data;

@Data
public class TrainTicketDto {
     private String ServiceType;//发票消费类型。火车票此字段固定输出：交通
     private String ticket_num;//车票号
     private String starting_station;//始发站
     private String train_num;//车次号
     private String destination_station;//到达站
     private String date;//出发日期
     private String ticket_rates;//车票金额
     private String seat_category;//席别
     private String name;//乘客姓名
     private String ID_card;//身份证号
     private String serial_number;//序列号
     private String sales_station;//售站
     private String time;//时间
     private String seat_num;//座位号
     private String Waiting_area;//候检区
     private String word;//识别结果字符串，以上各字段均包含
}
