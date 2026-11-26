package com.jpmsoft.projectProducer.pojo.entity.fundPlan;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金拨付申请
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 17:11
 */
@Data
@TableName("Fund_Appropriation")
public class FundAppropriation extends BaseEntity {

    private String fpId;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("cId")
    private String cId;
    private String faCode;
    private String faPayBasis;
    private String faPayExplain;
    private BigDecimal faInvestmentCompleted;
    private BigDecimal faInvestmentActual;
    private BigDecimal faInvestmentTotal;
    private BigDecimal faPayTotal;
    private Float faPayTotalRatio;
    private Float faDemandRatioTotal;
    private BigDecimal faReceived;
    private BigDecimal faDemand;
    private BigDecimal faDemandTotal;
    private Date faDate;
    private Integer faYear;
    private Integer faMonth;
    private String teId;
}
