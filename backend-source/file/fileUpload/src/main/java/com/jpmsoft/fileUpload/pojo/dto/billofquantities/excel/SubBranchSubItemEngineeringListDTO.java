package com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel;

import lombok.Data;

import java.util.List;

/**
 * 分部分项工程清单
 */
@Data
public class SubBranchSubItemEngineeringListDTO {

    // 表单名称
    private String sheetName;

    // 表单下标
    private Integer sheetIndex;

    // 工程名称
    private String engineeringName;

    // 条目列表
    private List<ListAndPriceTableEntryDTO> entryList;

}
