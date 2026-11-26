package com.jpmsoft.common.model.requestAndResponse.InvestmentControl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yb.Sun
 * @date 2024/10/25 15:27
 */
@Data
public class InvestmentEstimateBreakdownDetailsModel {
    private String id;
    //投资估算分解ID
    private String isbId;
    //费用类型名称
    private String isbdFeeTypeName;
    //类目
    private String isbdType;
    //标段类型
    private String isbdSectionType;
    //标段名称/费用名称
    private String isbdSectionName;
    //金额
    private BigDecimal isbdAmount;
    //备注
    private String isbdRemark;
    private Integer actionDisplay;
    private BigDecimal sort;

    private String changeType;
    // 序号
    private String levelCode;

    // 这个应该要加一个上级节点
    @JsonProperty("ParentId")
    private String parentId;

    // 工程量
    private BigDecimal workMeasure;

    // 单位
    private String unit;

    // 单价
    private String unitPrice;

    private List<InvestmentEstimateBreakdownDetailsModel> childNodes = new ArrayList<>();

    private Integer depth;

    // 占用金额
    private BigDecimal amountOccupied;
    // 余额
    private BigDecimal amountBalance;

    // 估算概算标识
    private String estimateBudgetFlag;

    /**
     * 来源标识：01估概算，02扩展
     */
    private String subjectSourceFlag;
}
