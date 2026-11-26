package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 单位工程汇总表
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/25 13:56
 */
@Data
@TableName("Con_Unit_Project_Summary")
public class ConUnitProjectSummary {
    @TableId(type = IdType.INPUT)
    private String id;
    private String boqdId;
    private String meaId;
    private BigDecimal upsSubItemAmt;
    private BigDecimal upsUnitPriceAmt;
    private BigDecimal upsTotalPriceAmt;
    private BigDecimal upsStepAmt;
    private BigDecimal upsOtherItemAmt;
    private BigDecimal upsTax;
    private BigDecimal upsFee;
    private BigDecimal upsOtherAmount;
    private BigDecimal upsCompleted;
    private Float upsTaxesRatio;
    private Float upsFeesRatio;
    private Float upsRatio;
    private Float upsOtherRatio;
    private String upsFormula;
    private String upsRemark;
    private Date createTime;
    private String createUid;
    private String createUname;
}
