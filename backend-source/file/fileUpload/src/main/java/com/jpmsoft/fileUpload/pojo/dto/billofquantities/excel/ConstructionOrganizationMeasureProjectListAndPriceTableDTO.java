package com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 施工组织（总价）措施项目清单与计价表
 */
@Data
public class ConstructionOrganizationMeasureProjectListAndPriceTableDTO {

    // 表单名称
    private String sheetName;

    // 表单下标
    private Integer sheetIndex;

    private String engineeringName;

    private List<ConstructionOrganizationMeasureProjectListAndPriceTableEntryDTO> entryList;

    public String getMeasureFeeRate() {
        if (CollUtil.isEmpty(entryList)) {
            return null;
        }
        BigDecimal feeRateSum = new BigDecimal(0);
        for (ConstructionOrganizationMeasureProjectListAndPriceTableEntryDTO entry : entryList) {
            String index = entry.getIndex();
            if (StrUtil.isBlank(index)){
                continue;
            }
            if (index.contains(".")){
                continue;
            }
            String feeRate = entry.getFeeRate();
            if (StrUtil.isBlank(feeRate)){
                continue;
            }
            feeRateSum =  feeRateSum.add(new BigDecimal(feeRate));
        }
        return feeRateSum.toString();
    }

}
