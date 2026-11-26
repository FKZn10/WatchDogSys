package com.jpmsoft.common.model.request.Contract;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 多项目合同支付项目经费号
 *
 * @author tianhelin
 * @date 2025/9/11
 */
@Data
public class ConPayMultiFundingRequest {
    private String id;
    private String cpmId;
    private String fundingNo;
    private BigDecimal amount;
    private Integer orderNo;
}
