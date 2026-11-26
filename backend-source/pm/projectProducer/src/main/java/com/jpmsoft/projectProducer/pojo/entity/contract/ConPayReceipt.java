package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("Con_Pay_Receipt")
public class ConPayReceipt {
    @TableId(type = IdType.INPUT)
    private String id;
    private String accountType;
    private String accountName;
    private String accountNo;
    private String accountBank;
    private BigDecimal receiptAmount;
    private String changeAccountDescription;
    private String payId;
    private String accId;
}