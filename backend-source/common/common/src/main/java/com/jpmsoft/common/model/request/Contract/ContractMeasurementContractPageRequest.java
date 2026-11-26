package com.jpmsoft.common.model.request.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 合同计量计价合同选择数据源分页请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ContractMeasurementContractPageRequest extends BasePageEntity {
    @JsonProperty("pId")
    private String pId;
}
