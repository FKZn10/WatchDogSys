package com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel;

import lombok.Data;

/**
 * 招标控制价条目dto
 */
@Data
public class BiddingControlPriceEntryDTO {

    // 序号
    private String index;

    // 工程名称
    private String sectionName;

    // 金额
    private String moneyAmount;

    // 暂估价
    private Double provisionalEstimatePrice;

    // 安全文明施工基本费
    private Double constructionBaseFee;

    // 规费
    private Double regulatoryFee;

    // 税金
    private Double tax;

}
