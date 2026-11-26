package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/4 11:13
 */
@Data
public class SecDocControlDto extends BaseFileDto implements Serializable {

    private static final long serialVersionUID = 4248462552896447515L;

    private String secId;
    private String dcPreparedBy;
    private BigDecimal dcPreparedPrice;
    private BigDecimal dcConstructPrice;
    private BigDecimal dcEconomicIndicator;
    private BigDecimal dcNonCompetitive;
    private String dcBoqDoc;
    private String dcConsultUnitDoc;
    private String dcFilingReceipt;
    private String dcScope;
    private BigDecimal dcAmount;
    private String dcDeterminType;
    private String dcPriceMode;
    private BigDecimal dcUpperLimit;
    private String dcFeatures;
    private String dcExecutiveStandard;
    private String dcSourceFunds;
    private String dcPerformanceGuaranteeType;
    private BigDecimal dcPerformanceBond;
    private Float dcPerfBondRatio;
    private String dcRemark;


    private String dcBoqDocIds;
    private String dcConsultUnitDocIds;
    private String dcFilingReceiptIds;
    private String secName;

}
