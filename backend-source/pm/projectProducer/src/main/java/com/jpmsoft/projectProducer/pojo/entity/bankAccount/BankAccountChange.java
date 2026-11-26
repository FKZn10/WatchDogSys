package com.jpmsoft.projectProducer.pojo.entity.bankAccount;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 合同账户变更
 */
@Data
@TableName("Bank_Account_Change")
public class BankAccountChange {

    @TableId(type = IdType.INPUT)
    private String bacId;
    private String pId;
    private String conId;
    private String bacName;
    private String bacNu;
    private String bacBank;
    private String bacBankName;
    private String bacBankNu;
    private String baIdN;
    private String bacNameN;
    private String bacNuN;
    private String bacBankN;
    private String bacBankNameN;
    private String bacBankNuN;
    private String bacNameWage;
    private String bacNuWage;
    private String bacBankWage;
    private String bacBankNameWage;
    private String bacBankNuWage;
    private String baIdWageN;
    private String bacNameWageN;
    private String bacNuWageN;
    private String bacBankWageN;
    private String bacBankNameWageN;
    private String bacBankNuWageN;
    private String bacCreateUid;
    private String bacCreateUname;
    private Date bacCreateTime;
    private Integer bacState;
    private Date bacStateTime;
    private String teId;
}
