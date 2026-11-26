package com.jpmsoft.common.model.response.Contract;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 多项目监理合同项目经费号
 *
 * @author tianhelin
 * @date 2025/9/8
 */
@Data
public class ConContractMultiFundingResponse {
    private String id;
    private String ccmId;
    private String fundingNo;
    private BigDecimal amount;
    private Integer orderNo;
}
