package com.jpmsoft.projectProducer.pojo.dto.fundPlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 太仓拨付表子表
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:00
 */
@Data
public class FundAllocationDtlDTO implements Serializable {

    private static final long serialVersionUID = -4702562112624152044L;
    private String id;
    @JsonProperty("pId")
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


    private String conName;
    private BigDecimal conMoneyTotal;
    @JsonProperty("pName")
    private String pName;
}
