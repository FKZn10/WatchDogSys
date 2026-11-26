package com.jpmsoft.projectProducer.pojo.dto.contract;

import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import lombok.Data;

import java.util.List;

/**
 * 概算合同
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/26 14:19
 */
@Data
public class ContractBudgetDto extends ConContractRequest {

    private String bddName;
    private String bddCode;
    private List<String> excludeCategory;
}
