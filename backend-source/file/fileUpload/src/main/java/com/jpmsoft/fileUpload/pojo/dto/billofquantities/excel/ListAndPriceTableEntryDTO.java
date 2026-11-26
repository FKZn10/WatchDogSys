package com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel;

import lombok.Data;

/**
 * 清单与计价表条目
 */
@Data
public class ListAndPriceTableEntryDTO {

    // 序号
    private String index;

    // 项目名称
    private String projectName;

    // 项目编码
    private String projectCode;

    // 项目特征
    private String projectFeature;

    // 计量单位
    private String measureUnit;

    // 工程量
    private String engineeringQuantity;

    // 综合单价
    private String comprehensiveUnitPrice;

    // 合价
    private String combinePrice;

    // 备注
    private String remark;

}
