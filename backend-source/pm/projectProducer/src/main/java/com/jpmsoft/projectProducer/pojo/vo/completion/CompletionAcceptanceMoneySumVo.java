package com.jpmsoft.projectProducer.pojo.vo.completion;

import com.jpmsoft.common.model.response.Construction.CompletionAcceptanceResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/18 15:55
 */
@Data
public class CompletionAcceptanceMoneySumVo implements Serializable {
    private BigDecimal caConMoneySum = BigDecimal.ZERO;
    private BigDecimal caSettlementMoneySum = BigDecimal.ZERO;
    private List<CompletionAcceptanceResponse> data;
}
