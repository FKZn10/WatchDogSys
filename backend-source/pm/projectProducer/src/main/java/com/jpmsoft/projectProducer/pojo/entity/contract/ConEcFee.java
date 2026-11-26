package com.jpmsoft.projectProducer.pojo.entity.contract;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 变更费用申报
 */
@Data
@TableName("Con_EC_Fee")
public class ConEcFee {
    @TableId(type = IdType.INPUT)
    private String ecfId;
    private String ecId;
    private String ecfContractor;
    private String ecfSupervisor;
    private String ecfCode;
    private String ecfImplementation;
    private String ecfGrade;
    private Integer ecfApplyMoneyType;
    private BigDecimal ecfApplyMoney;
    private Integer ecfDurationType;
    private Integer ecfDuration;
    private String ecfRemark;
    private String ecfCreateUid;
    private String ecfCreateUname;
    private Date ecfCreateTime;
    private Integer ecfState;
    private Date ecfStateTime;
    private String ecfAnnex;
    private Float ecfProportion;
    private String ecfPm;
    private String ecfApplyUnit;
    private String ecfType;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String ecfContent;
    private BigDecimal ecfApplyMoneyTotal;
    private String teId;
    private String ecfChangeType;
    private String dcoId;
    private BigDecimal ecfPreliminaryAmount;
}
