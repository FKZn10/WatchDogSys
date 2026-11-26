package com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel;

import lombok.Data;

import java.util.List;

/**
 * 招标控制价dto
 */
@Data
public class BiddingControlPriceDTO {

    // 表单名称
    private String sheetName;

    // 表单下标
    private Integer sheetIndex;

    // 工程名称
    private String sectionName;

    // 条目列表
    private List<BiddingControlPriceEntryDTO> entryList;

    // 合计金额
    private String totalPrice;

}
