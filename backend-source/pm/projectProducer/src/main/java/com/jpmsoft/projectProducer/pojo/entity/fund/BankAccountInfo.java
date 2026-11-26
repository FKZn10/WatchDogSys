package com.jpmsoft.projectProducer.pojo.entity.fund;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("Bank_Account_Info")
public class BankAccountInfo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -783959611110063214L;
    private String id;
    private String accountType;
    private String pId;
    //工程专户
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
