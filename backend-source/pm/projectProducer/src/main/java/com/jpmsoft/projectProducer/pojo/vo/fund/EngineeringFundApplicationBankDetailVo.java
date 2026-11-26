package com.jpmsoft.projectProducer.pojo.vo.fund;

import lombok.Data;

@Data
public class EngineeringFundApplicationBankDetailVo {
    private String id;
    private String efaId;
    private String baId;
    private Integer accountType;
    private String companyID;
    private String companyName;
    private String bankName;
    private String bankAccount;
    //工程专户
    private String pId;
    private String pName;
}
