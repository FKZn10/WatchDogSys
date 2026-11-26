package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jjz
 */
@Data
public class ConEcAndVisFeeModel extends BaseFileRequest {


    private String conId;

    @JsonProperty("pName")
    private String pName;

    // 工程项目名称
    @JsonProperty("pConName")
    private String pConName;

    private String conName;

    private String type;

    // 预算金额
    private BigDecimal budgetAmount;

    // 结算注意事项
    private String remark;

    // 审批表编号
    private String approvalId;

    private Integer state;

    // 是否已办理审批 01 已审批 02 未审批
     private String approvedFlag;

     // 区分 01 暂列金 02 暂估价
    private String dataSeparateType;
}
