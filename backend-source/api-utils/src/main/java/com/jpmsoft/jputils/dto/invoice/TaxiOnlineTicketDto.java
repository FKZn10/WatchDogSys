package com.jpmsoft.jputils.dto.invoice;

import lombok.Data;

@Data
public class TaxiOnlineTicketDto {
  private String ServiceType;//发票消费类型。网约车行程单此字段固定输出：交通
  private String service_provider;//服务商
  private String start_time;//行程开始时间
  private String destination_time;//行程结束时间
  private String phone;//行程人手机号
  private String application_date;//申请日期
  private String total_fare;//总金额
  private String item_num;//行程信息中包含的行程数量
  private String items;//行程信息
  private String item_id;//行程信息的对应序号
  private String item_provider;//行程信息的对应服务商
  private String pickup_time;//上车时间
  private String pickup_date;//上车日期
  private String car_type;//车型
  private String distance;//里程
  private String start_place;//起点
  private String destination_place;//终点
  private String city;//城市
  private String fare;//金额
}
