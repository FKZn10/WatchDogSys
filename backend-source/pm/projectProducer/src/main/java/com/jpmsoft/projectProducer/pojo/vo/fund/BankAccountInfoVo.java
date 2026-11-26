package com.jpmsoft.projectProducer.pojo.vo.fund;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

@Data
public class BankAccountInfoVo extends BaseFileDto {
    private String id;
    private String accountType;
    private String companyID;
    private String companyName;
    private String bankName;
    private String bankAccount;
    //工程专户
    private String pId;
    private String pName;
    private String projectName;
}
