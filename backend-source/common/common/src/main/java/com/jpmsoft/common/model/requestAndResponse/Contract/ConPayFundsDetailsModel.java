package com.jpmsoft.common.model.requestAndResponse.Contract;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ConPayFundsDetailsModel {
    private String id;
    private String payId;
    private BigDecimal payFundAccountMoney;
    private String payFundAccount;
    private String payFundAccountNum;
}
