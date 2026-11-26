package com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel;

import lombok.Data;

/**
 * 施工组织（总价）措施项目清单与计价表条目
 */
@Data
public class ConstructionOrganizationMeasureProjectListAndPriceTableEntryDTO {

    private String index;

    private String projectName;

    private String calculateBase;

    private String feeRate;

    private String amount;

    private String remark;

}
