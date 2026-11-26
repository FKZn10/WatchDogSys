package com.jpmsoft.jputils.response;

import lombok.Data;

import java.util.List;

/**
 * type 字段说明：
 * type类型	说明
 * vat_invoice	增值税专用发票
 * taxi_receipt	出租车票
 * train_ticket	火车票
 * quota_invoice	定额发票
 * air_ticket	飞机行程单
 * roll_normal_invoice	增值税普通发票（卷票）
 * printed_invoice	机打发票
 * bus_ticket	汽车票
 * toll_invoice	过路过桥费发票
 * ferry_ticket	船票
 * motor_vehicle_invoice	机动车销售发票
 * used_vehicle_invoice	二手车发票
 * taxi_online_ticket	网约车行程单
 * limit_invoice	限额发票
 * shopping_receipt	购物小票
 * pos_invoice	POS小票
 * others	其他
 *
 * 增值税发票：type 的返回结果为 vat_invoice，即“增值税发票”时，识别结果的返回字段如下：
 * 字段名	说明
 * ServiceType	发票消费类型。不同消费类型输出：餐饮、电器设备、通讯、服务、日用品食品、医疗、交通、其他
 * InvoiceTypeOrg	发票名称
 * InvoiceType	增值税发票的细分类型。 不同细分类型的发票输出：普通发票、专用发票、电子普通发票、电子专用发票、通行费电子普票、区块链发票、通用机打电子发票、电子发票(专用发票)、电子发票(普通发票)
 * InvoiceTag	增值税发票左上角标志。 包含：通行费、销项负数、代开、收购、成品油、其他
 * InvoiceCode	发票代码
 * InvoiceNum	发票号码
 * InvoiceCodeConfirm	发票代码的辅助校验码，一般业务情景可忽略
 * InvoiceNumConfirm	发票号码的辅助校验码，一般业务情景可忽略
 * CheckCode	校验码。增值税专票无此参数
 * InvoiceNumDigit	数电票号码。密码区部分的数电票号码，仅在纸质的数电票上出现
 * InvoiceDate	开票日期
 * PurchaserName	购方名称
 * PurchaserRegisterNum	购方纳税人识别号
 * PurchaserAddress	购方地址及电话
 * PurchaserBank	购方开户行及账号
 * Password	密码区
 * Province	省
 * City	市
 * SheetNum	联次信息。专票第一联到第三联分别输出：第一联：记账联、第二联：抵扣联、第三联：发票联；普通发票第一联到第二联分别输出：第一联：记账联、第二联：发票联
 * Agent	否代开
 * OnlinePay	子支付标识。仅区块链发票含有此参数
 * SellerName	销售方名称
 * SellerRegisterNum	销售方纳税人识别号
 * SellerAddress	销售方地址及电话
 * SellerBank	销售方开户行及账号
 * TotalAmount	合计金额
 * TotalTax	合计税额
 * AmountInWords	价税合计(大写)
 * AmountInFiguers	价税合计(小写)
 * Payee	收款人
 * Checker	复核
 * NoteDrawer	开票人
 * Remarks	备注
 * CommodityName	货物名称
 * CommodityType	规格型号
 * CommodityUnit	单位
 * CommodityNum	数量
 * CommodityPrice	单价
 * CommodityAmount	金额
 * CommodityTaxRate	税率
 * CommodityTax	税额
 * CommodityPlateNum	车牌号。仅通行费增值税电子普通发票含有此参数
 * CommodityVehicleType	类型。仅通行费增值税电子普通发票含有此参数
 * CommodityStartDate	通行日期起。仅通行费增值税电子普通发票含有此参数
 * CommodityEndDate	通行日期止。仅通行费增值税电子普通发票含有此参数
 *
 * 出租车票：type 的返回结果为 taxi_receipt，即“出租车票”时，识别结果的返回字段如下
 * 字段名	说明
 * ServiceType	发票消费类型。出租车票此字段固定输出：交通
 * InvoiceCode	发票代号
 * InvoiceNum	发票号码
 * TaxiNum	车牌号
 * Date	日期
 * Time	上下车时间
 * PickupTime	上车时间
 * DropoffTime	下车时间
 * Fare	金额
 * FuelOilSurcharge	燃油附加费
 * CallServiceSurcharge	叫车服务费
 * TotalFare	总金额
 * Location	开票城市
 * Province	省
 * City	市
 * PricePerkm	单价
 * Distance	里程
 *
 * 火车票：type 的返回结果为 train_ticket，即“火车票”时，识别结果的返回字段如下
 * 字段名	说明
 * ServiceType	发票消费类型。火车票此字段固定输出：交通
 * ticket_num	车票号
 * starting_station	始发站
 * train_num	车次号
 * destination_station	到达站
 * date	出发日期
 * ticket_rates	车票金额
 * seat_category	席别
 * name	乘客姓名
 * ID_card	身份证号
 * serial_number	序列号
 * sales_station	售站
 * time	时间
 * seat_num	座位号
 * Waiting_area	候检区
 * word	识别结果字符串，以上各字段均包含
 *
 * 定额发票：type 的返回结果为 quota_invoice，即“定额发票”时，识别结果的返回字段如下
 * 字段名	说明
 * ServiceType	发票消费类型。定额发票此字段固定输出：交通
 * invoice_code	发票代码
 * invoice_number	发票号码
 * invoice_rate	金额
 * invoice_rate_in_figure	金额小写
 * invoice_rate_in_word	金额大写
 * Province	省
 * City	市
 * Location	发票所在地
 * invoice_type	发票名称
 *
 * 飞机行程单：type 的返回结果为 air_ticket，即“飞机行程单”时，识别结果的返回字段如下
 * 字段名	说明
 * ServiceType	发票消费类型。飞机行程单此字段固定输出：交通
 * name	姓名
 * starting_station	始发站
 * destination_station	目的站
 * flight	航班号
 * date	日期
 * ticket_number	电子客票号码
 * fare	票价
 * dev_fund	民航发展基金/基建费
 * oil_money	燃油附加费
 * other_tax	其他税费
 * ticket_rates	合计金额
 * start_date	填开日期
 * id_no	身份证号
 * carrier	承运人
 * time	时间
 * issued_by	订票渠道
 * serial_number	印刷序号
 * insurance	保险费
 * fare_basis	客票级别
 * class	座位等级
 * agent_code	销售单位号
 * endorsement	签注
 * allow	免费行李
 * ck	验证码
 * effective_date	客票生效日期
 * expiration_date	有效期截止日期
 *
 * 卷票：type 的返回结果为 roll_normal_invoice，即“卷票”时，识别结果的返回字段如下
 * 字段名	说明
 * InvoiceType	发票名称
 * InvoiceCode	发票代码
 * InvoiceNum	发票号码
 * MachineNum	机打号码。仅增值税卷票含有此参数
 * MachineCode	机器编号。仅增值税卷票含有此参数
 * InvoiceDate	开票日期
 * PurchaserName	购方名称
 * PurchaserRegisterNum	购方纳税人识别号
 * SellerName	销售方名称
 * SellerRegisterNum	销售方纳税人识别号
 * TotalTax	价税合计
 * AmountInWords	合计金额(大写)
 * AmountInFiguers	合计金额(小写)
 * Payee	收款人
 * CheckCode	校验码。增值税专票无此参数
 * Province	省
 * City	市
 * CommodityName	货物名称
 * CommodityNum	数量
 * CommodityPrice	单价
 * CommodityAmount	金额
 *
 * 机打发票：type 的返回结果为 printed_invoice，即“机打发票”时，识别结果的返回字段如下
 * 字段名	说明
 * ServiceType	发票消费类型。不同消费类型输出：餐饮、电器设备、通讯、服务、日用品食品、医疗、交通、其他
 * InvoiceType	发票类型
 * InvoiceCode	发票代码
 * InvoiceNum	发票号码
 * InvoiceDate	开票日期
 * AmountInFiguers	合计金额小写
 * AmountInWords	合计金额大写
 * MachineNum	机打号码
 * CheckCode	校验码
 * SellerName	销售方名称
 * SellerRegisterNum	销售方纳税人识别号
 * PurchaserName	购买方名称
 * PurchaserRegisterNum	购买方纳税人识别号
 * TotalTax	合计税额
 * Province	省
 * City	市
 * Time	时间
 * SheetNum	联次
 * CommodityName	商品名称
 * CommodityUnit	商品单位
 * CommodityPrice	商品单价
 * CommodityNum	商品数量
 * CommodityAmount	商品金额
 *
 * 汽车票：type 的返回结果为 bus_ticket，即“汽车票”时，识别结果的返回字段如下
 * 字段名	说明
 * ServiceType	发票消费类型。汽车票此字段固定输出：交通
 * InvoiceCode	发票代码
 * InvoiceNum	发票号码
 * Date	日期
 * Time	时间
 * ExitStation	出发站
 * Amount	金额
 * IdCard	身份证号
 * ArrivalStation	到达站
 * Name	姓名
 * InvoiceTime	开票日期
 *
 * 过路过桥费：type 的返回结果为 toll_invoice，即“过路过桥费”时，识别结果的返回字段如下
 * 字段名	说明
 * ServiceType	发票消费类型。过路过桥费此字段固定输出：交通
 * InvoiceCode	发票代码
 * InvoiceNum	发票号码
 * Entrance	入口
 * Exit	出口
 * OutDate	日期
 * OutTime	时间
 * TotalAmount	金额
 * Province	省
 * City	市
 *
 * 船票：type 的返回结果为 toll_invoice，即“船票”时，识别结果的返回字段如下
 * 字段名	说明
 * ServiceType	发票消费类型。船票此字段固定输出：交通
 * InvoiceType	发票类型
 * InvoiceCode	发票代码
 * InvoiceNum	发票号码
 * ExitStation	出发地点
 * ArrivalStation	到达地点
 * Amount	总金额
 * Date	开票日期
 * MoneyType	金额类型
 * BarCode	条码
 * BarCodeNum	条码编号
 * City	市
 * Province	省
 * InvoiceTitle	发票抬头，这里指该张船票的运行公司名
 * QrCode	二维码
 * Time	出发时间
 * TicketTime	制票时间
 * TicketDate	制票日期
 * PassengerName	乘客姓名
 * IdCard	乘客身份证号
 *
 * 机动车销售发票：type 的返回结果为 motor_vehicle_invoice，即“机动车销售发票”时，识别结果的返回字段如下
 * 字段名	说明
 * date	开票日期
 * fapiao-daima	发票代码/机打代码
 * fapiao-haoma	发票号码/机打号码
 * printed-daima	机打代码
 * printed-haoma	机打号码
 * machine-num	机器编号
 * buyer-name	购买方名称
 * payer-tax-num	购买方身份证号码/组织机构代码
 * car-class	车辆类型
 * car-model	厂牌型号
 * product-location	产地
 * certificate-num	合格证号
 * engine-num	发动机号码
 * vin-num	车架号码
 * price-tax-big	价税合计
 * price-tax-small	价税合计小写
 * saler	销货单位名称
 * saler-phone	销货单位电话
 * saler-tax-num	销货单位纳税人识别号
 * saler-bank-num	销货单位账号
 * saler-address	销货单位地址
 * saler-bank	销货单位开户银行
 * tax-rate	税率
 * tax	税额
 * tax-jiguan	主管税务机关
 * tax-jiguan-daima	主管税务机关代码
 * price	不含税价格
 * limit-mount	限乘人数
 * toonage	吨位
 * sheet-num	联次
 * drawer	开票人
 * remarks	备注
 * import-certificate-num	进口证明书号
 * tax-payment-voucher-no	完整凭税编号
 * inspection-form-num	商检单号
 *
 * 二手车销售发票：type 的返回结果为 used_vehicle_invoice，即“二手车销售发票”时，识别结果的返回字段如下
 * 字段名	说明
 * invoice_code	发票代码
 * invoice_num	发票号码
 * date	开票日期
 * tax_code	税控码
 * buyer	买方
 * buyer_id	买方身份证号
 * buyer_station	买方地址
 * buyer_tel	买方电话
 * saler	卖方
 * saler_id	卖方身份证号
 * saler_station	卖方地址
 * saler_tel	卖方电话
 * car_plate	车牌号
 * car_certificate	登记证号
 * car_class	车辆类型
 * vin_num	车架号
 * model	厂牌型号
 * to_station	转入地车管所名称
 * big_price	车价合计大写
 * small_price	车价合计小写
 * car_market	二手车市场
 * tax_num	纳税人识别号
 * tax_location	纳税人地址
 * tax_tel	纳税人电话
 * sheet_num	联次
 *
 *
 * 网约车行程单：type 的返回结果为 taxi_online_ticket，即“网约车行程单”时，识别结果的返回字段如下
 * 字段名	说明
 * ServiceType	发票消费类型。网约车行程单此字段固定输出：交通
 * service_provider	服务商
 * start_time	行程开始时间
 * destination_time	行程结束时间
 * phone	行程人手机号
 * application_date	申请日期
 * total_fare	总金额
 * item_num	行程信息中包含的行程数量
 * items	行程信息
 * item_id	行程信息的对应序号
 * item_provider	行程信息的对应服务商
 * pickup_time	上车时间
 * pickup_date	上车日期
 * car_type	车型
 * distance	里程
 * start_place	起点
 * destination_place	终点
 * city	城市
 * fare	金额
 */
@Data
public class InvoiceDataResponse {
    private Integer resultCount;
    private List<InvoiceDataInfoResponse> results;
    private Integer pdfSize;
}
