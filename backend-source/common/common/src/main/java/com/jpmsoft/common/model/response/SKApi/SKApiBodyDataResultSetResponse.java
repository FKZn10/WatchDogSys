package com.jpmsoft.common.model.response.SKApi;

import lombok.Data;

@Data
public class SKApiBodyDataResultSetResponse {
    public String BillsPayOrderNo;
    /**
     * 1.发起申请 2.待受理 3.受理中 4.受理拒绝 5.待支付 6.支付处理中
     * 7.支付失败 8.支付成功 9.部分支付成功 10.支付异常 11.已退款 12.已作废
     */
    public String PayStatus;
    public String PayStatusName;
    public String FailReasons;
    public String RelPaymentDate;
    public String AcceptType;
    public String PayAmount;
    public String TranistAmount;
    public String UnAcceptAmount;
    public String RefundAmount;
    public String PayAccountNo;
    public String PayAccountName;
    public String PayOrgCode;
    public String PayOrgName;
}
