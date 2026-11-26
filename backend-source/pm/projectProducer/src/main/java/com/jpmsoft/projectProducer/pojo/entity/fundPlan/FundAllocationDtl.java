package com.jpmsoft.projectProducer.pojo.entity.fundPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 太仓拨付表子表
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:00
 */
@Data
@TableName("Fund_Allocation_Dtl")
public class FundAllocationDtl {

    @TableId(type = IdType.INPUT)
    private String id;
    private String pId;
    private String conId;
    private BigDecimal fadAuditPrice;
    private BigDecimal fadPayableThisTime;
    private BigDecimal fadActualPayment;
    private BigDecimal fadCumulativeDisbursement;
    private String fadCompetentDepartment;
    private String fadFirstParty;
    private BigDecimal fadHistoricalPayment;
    private String fadContractPeriod;
    private String faId;

    private String payId;

}
