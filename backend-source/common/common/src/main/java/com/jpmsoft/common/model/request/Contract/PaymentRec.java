package com.jpmsoft.common.model.request.Contract;

import lombok.Data;

@Data
public class PaymentRec {
    public String ReceiveOrgName;
    public String ReceiveOrgCode;
    public String ReceiveAccountNo;
    public String ReceiveAccountName;
    public String ReceiveAreaNameOfProvince;
    public String ReceiveAreaNameOfCity;
    public String ReceiveBranchCode;
    public String ReceiveBranchName;
    public String ReceiveNature="1";
    public String ReceiveCurrencyCode="CNY";
    public String ReceiveCurrencyName="人民币";
}
