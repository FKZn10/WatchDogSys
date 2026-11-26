package com.jpmsoft.projectProducer.pojo.entity.fundPlan;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金收款明细单
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/17 14:31
 */
@Data
@TableName("Fund_Collection_Details")
public class FundCollectionDetails extends BaseEntity {

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
    private String teId;
}
