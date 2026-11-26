package com.jpmsoft.projectProducer.pojo.vo.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/17 14:40
 */
@Data
public class FundCollectionDetailsVo extends ProjectCommonRes implements Serializable {

    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String faId;
    private BigDecimal fcdMoney;
    private Date fcdDate;
    private BigDecimal fcdTotal;
    private Float fcdRate;
    private String fcdRemark;
    private Integer fcdBatchNo;
    private BigDecimal fcdReceivedAmt;

    private String faCode;
    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;
}
