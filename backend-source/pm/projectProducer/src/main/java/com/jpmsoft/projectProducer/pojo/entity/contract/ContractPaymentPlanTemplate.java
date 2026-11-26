package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Contract_Payment_Plan_Template")
public class ContractPaymentPlanTemplate {
    @TableId(type = IdType.INPUT)
    private String id;
    private String contractType;

    private String contractName;
    private BigDecimal contractAmountBelow;

    private BigDecimal contractAmountUpper;


    private Date createTime;

    private Date updateTime;
}
