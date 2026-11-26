package com.jpmsoft.gmgws.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ContractStatisticsVo {

    private String id;

    private String createUid;

    private String createUname;

    private Date createTime;

    private Integer state;

    private Date stateTime;

    private String conId;

    private BigDecimal csMoney;

    private BigDecimal csSettlemenDowngrading;

    private String csPaymentMethod;

    private String conName;

    private String conPartyBName;

    @JsonProperty("pName")
    private String pName;

    private String conCode;
}
