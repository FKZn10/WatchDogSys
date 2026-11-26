package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同支付计划变更记录
 */
@Data
@TableName("Con_Contract_Pay_Plan_Record")
public class ConContractPayPlanRecord {

    @TableId(type = IdType.INPUT)
    private String cppId;
    private String conRecordId;
    private String cppName;
    private String cppCode;
    private String cppClause;
    private BigDecimal cppMoney;
    private Float cppRatio;
    private Float cppRatioPayTo;
    private String cppRemark;
    private Integer cppSort;
    private Date cppCreateTime;
}
