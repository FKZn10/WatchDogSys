package com.jpmsoft.projectConsumer.pojo.dto.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金收款明细单
 * @author: 李文涛 aaron
 * @date: 2022/10/17 14:32
 */
@Data
public class FundCollectionDetailsDto extends BaseFileDto implements Serializable {

    private static final long serialVersionUID = 3794591192075673788L;

    @JsonProperty("pId")
    private String pId;
    private String faId;
    private BigDecimal fcdMoney;
    private Date fcdDate;
    private BigDecimal fcdTotal;
    private Float fcdRate;
    private String fcdRemark;
    private Integer fcdBatchNo;
    private BigDecimal fcdReceivedAmt;

    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pBuildModel")
    private String pBuildModel;

}
