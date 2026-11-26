package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 基建投资月报清单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:02
 */
@Data
@TableName("Inv_Infrastructure_Month_Dtl")
public class InvInfrastructureMonthDtl {

    @TableId(type = IdType.INPUT)
    private String id;
    private String imId;
    @JsonProperty("pId")
    private String pId;
    private BigDecimal imdSurplusFunds;
    private BigDecimal imdCollectCountry;
    private BigDecimal imdCollectSchool;
    private BigDecimal imdCollectGovernment;
    private BigDecimal imdCollectDonate;
    private BigDecimal imdCollectOther;
    private BigDecimal imdPayCountry;
    private BigDecimal imdPaySchool;
    private BigDecimal imdPayGovernment;
    private BigDecimal imdPayDonate;
    private BigDecimal imdPayOther;
    private BigDecimal imdOtherFunds;
    private BigDecimal imdReturnFunds;
    private BigDecimal imdYearBalance;
    private BigDecimal imdPlanFunds;
    private String imdRemark;
    private Integer imdSort;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String imdSchoolName;
    private BigDecimal imdCollectTotal;
    private BigDecimal imdPayTotal;
}
