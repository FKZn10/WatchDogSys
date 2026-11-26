package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 合同收款单位明细变更
 */
@Data
@TableName("Con_Payee_Dtl_Change")
public class ConPayeeDtlChange {

    @TableId(type = IdType.INPUT)
    private String cpdId;
    private String conChangeId;
    private String cpdCName;
    private String cpdCId;
    private String cpdBank;
    private String cpdBankAccount;
    private String cpdCreateUid;
    private Date cpdCreateTime;
    private String cpdCreateUname;
    private String cpdAccountBankName;
    private String cpdAccountBankNum;
}
