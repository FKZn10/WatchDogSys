package com.jpmsoft.common.model.requestAndResponse.Contract;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ConPayReceiptDTO {
    private String id;
    private String accountType;
    private String accountName;
    private String accountNo;
    private String accountBank;
    private BigDecimal receiptAmount;
    private String changeAccountDescription;
    private String payId;
    private String accId;
    private Date payCreateTime;
}
