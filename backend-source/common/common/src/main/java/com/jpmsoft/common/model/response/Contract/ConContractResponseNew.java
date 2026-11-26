package com.jpmsoft.common.model.response.Contract;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ConContractResponseNew {
    List<ConContractResponse> data;
    BigDecimal totalMoney;
}
