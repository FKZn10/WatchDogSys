package com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel;

import lombok.Data;

/**
 * 专业工程招标控制价条目
 */
@Data
public class ProfessionalEngineeringBiddingControlPriceEntryDTO {

    // 序号
    private String index;

    // 费用名称
    private String feeName;

    // 计算公式
    private String formula;

    // 费率
    private String feeRate;

    // 金额
    private String amount;

    // 备注
    private String remark;

}
