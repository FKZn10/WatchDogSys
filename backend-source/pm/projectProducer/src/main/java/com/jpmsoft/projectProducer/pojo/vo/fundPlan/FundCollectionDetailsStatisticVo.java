package com.jpmsoft.projectProducer.pojo.vo.fundPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/19 16:10
 */
@Data
public class FundCollectionDetailsStatisticVo implements Serializable {

    private static final long serialVersionUID = 1220529777713690601L;
    private BigDecimal fcdMoneySum;
    @JsonProperty("pInvTotalSum")
    private BigDecimal pInvTotalSum;
    private List<FundCollectionDetailsVo> data;
}
