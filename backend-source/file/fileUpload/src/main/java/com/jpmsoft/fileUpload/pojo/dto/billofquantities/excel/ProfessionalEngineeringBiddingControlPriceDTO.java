package com.jpmsoft.fileUpload.pojo.dto.billofquantities.excel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.List;

/**
 * 专业工程招标控制价
 */
@Data
public class ProfessionalEngineeringBiddingControlPriceDTO {

    // 表单名称
    private String sheetName;

    // 表单下标
    private Integer sheetIndex;

    // 工程名称
    private String sectionName;

    // 条目
    private List<ProfessionalEngineeringBiddingControlPriceEntryDTO> entryList;

    public String getTaxRate() {
        if (CollUtil.isEmpty(entryList)) {
            return null;
        }
        for (ProfessionalEngineeringBiddingControlPriceEntryDTO entry : entryList) {
            String feeName = entry.getFeeName();
            if ("增值税".equals(feeName)) {
                return StrUtil.isBlank(entry.getFeeRate()) ? "0" : entry.getFeeRate();
            }
        }
        return "0";
    }

    public String getTax() {
        if (CollUtil.isEmpty(entryList)) {
            return null;
        }
        for (ProfessionalEngineeringBiddingControlPriceEntryDTO entry : entryList) {
            String feeName = entry.getFeeName();
            if ("增值税".equals(feeName)) {
                return entry.getAmount();
            }
        }
        return "0";
    }

    public String getRegulatoryFeeRate() {
        if (CollUtil.isEmpty(entryList)) {
            return null;
        }
        for (ProfessionalEngineeringBiddingControlPriceEntryDTO entry : entryList) {
            String feeName = entry.getFeeName();
            if ("规费".equals(feeName)) {
                return StrUtil.isBlank(entry.getFeeRate()) ? "0" : entry.getFeeRate();
            }
        }
        return "0";
    }

    public String getRegulatoryFee() {
        if (CollUtil.isEmpty(entryList)) {
            return null;
        }
        for (ProfessionalEngineeringBiddingControlPriceEntryDTO entry : entryList) {
            String feeName = entry.getFeeName();
            if ("规费".equals(feeName)) {
                return entry.getAmount();
            }
        }
        return "0";
    }
}
