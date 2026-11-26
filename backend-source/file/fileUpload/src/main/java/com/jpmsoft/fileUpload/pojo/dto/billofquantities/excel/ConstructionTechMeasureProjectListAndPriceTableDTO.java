package com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel;

import lombok.Data;

import java.util.List;

/**
 * 施工技术措施项目清单与计价表
 */
@Data
public class ConstructionTechMeasureProjectListAndPriceTableDTO {

    // 表单名称
    private String sheetName;

    // 表单下标
    private Integer sheetIndex;

    private String engineeringName;

    private List<ListAndPriceTableEntryDTO> entryList;

}
