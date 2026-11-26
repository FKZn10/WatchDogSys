package com.jpmsoft.projectProducer.pojo.dto.fund;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

@Data
public class BankAccountInfoDto extends BaseFileRequest {
    private String id;
    private String accountType;
    //工程专户
    private String pId;
    private String baCompanyID;
    private String baCompanyName;
    private String baBankName;
    private String baBankAccount;
    //数字人民币开户银行
    private String baDigitalRMBCompanyID;
    private String baDigitalRMBCompanyName;
    private String baDigitalRMBBankName ;
    private String baDigitalRMBAccount ;
}
