package com.jpmsoft.projectProducer.pojo.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

/**
 * 概算合同工程量清单
 */
@Data
public class ConBoqBudgetDto extends BaseModeRequest {

    private static final long serialVersionUID = -7971187657897411793L;

    /**
     * 所属项目概算科目表ID
     */
    private String boqdBddId;
    private Integer boqState;
    @JsonProperty("pId")
    private String pId;
}
