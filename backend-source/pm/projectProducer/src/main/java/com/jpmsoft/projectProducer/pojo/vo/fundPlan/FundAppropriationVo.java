package com.jpmsoft.projectProducer.pojo.vo.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资金拨付申请
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/16 17:11
 */
@Data
public class FundAppropriationVo extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -7072747303401215677L;
    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String fpId;
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

    @JsonProperty("cName")
    private String cName;
    @JsonProperty("cAbbName")
    private String cAbbName;
    @JsonProperty("dName")
    private String dName;
    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;
    private String fpCode;
    private List<FundAppropriationDtlVo> fundAppropriationDtls;
}
