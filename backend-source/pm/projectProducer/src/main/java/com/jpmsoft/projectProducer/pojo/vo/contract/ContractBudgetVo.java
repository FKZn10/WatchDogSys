package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/26 14:11
 */
@Data
public class ContractBudgetVo extends ConContractResponse {
    private String bddName;
    private String bddCode;
    private String bddUnit;
    private Float bddCount;
    private BigDecimal bddPrice;
    private BigDecimal bddTotal;
}
