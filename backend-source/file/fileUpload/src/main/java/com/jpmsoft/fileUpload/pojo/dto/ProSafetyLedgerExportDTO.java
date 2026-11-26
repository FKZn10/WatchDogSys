package com.jpmsoft.fileUpload.pojo.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProSafetyLedgerExportDTO {

    @ExcelIgnore
    @JsonProperty("pId")
    private String pId;


    @JsonProperty("index")
    @ExcelProperty("序号")
    private Integer index;  //序号

    @JsonProperty("pCode")
    @ExcelProperty("项目编号")
    @ColumnWidth(60)
    private String pCode; //项目编号

    @JsonProperty("pName")
    @ExcelProperty("项目名称")
    @ColumnWidth(60)
    private String pName;
    @JsonProperty("contractName")
    @ExcelProperty("合同名称")
    @ColumnWidth(60)
    private String contractName;
    @JsonProperty("constructionUnitName")
    @ExcelProperty("施工单位名称")
    @ColumnWidth(60)
    private String constructionUnitName;
    @JsonProperty("pType")
    @ExcelProperty("项目类别")
    @ColumnWidth(20)
    private String pType;  //项目类别


    @JsonProperty("pProjectStatus")
    @ExcelProperty("工程进展状态")
    @ColumnWidth(20)
    private String pProjectStatus;  // 工程进展状态

    @ExcelProperty("总投资额（万元）")
    @JsonProperty("pInvTotal")
    @ColumnWidth(20)
    private BigDecimal pInvTotal;  //总投资额（万元）

    @JsonProperty("constructionConMoney")
    @ExcelProperty("施工合同额（万元）")
    @ColumnWidth(20)
    private BigDecimal constructionConMoney;//施工合同额（万元）

    /**
     * 报告份数
     */
    @JsonProperty("qsPlanTaskCount")
    @ExcelProperty("报告份数")
    @ColumnWidth(10)
    private Integer qsPlanTaskCount;

    /**
     * 问题数量
     */
    @JsonProperty("qsRecordCount")
    @ExcelProperty("问题数量")
    @ColumnWidth(10)
    private Integer qsRecordCount = 0;
    /**
     * 整改数量
     */
    @JsonProperty("qsFinishCount")
    @ExcelProperty("整改数量")
    @ColumnWidth(10)
    private Integer qsFinishCount = 0;
    /**
     * 闭合率
     */
    @JsonProperty("qsFinishRate")
    @ExcelIgnore
    private BigDecimal qsFinishRate;
    /**
     * 逾期整改数量
     */
    @JsonProperty("qsFinishOverdueCountStr")
    @ExcelProperty("逾期整改数量")
    @ColumnWidth(10)
    private String qsFinishOverdueCountStr ;
    /**
     * 二次整改数量
     */
    @JsonProperty("qsFinishOnTimeCountStr")
    @ExcelProperty("二次整改数量")
    @ColumnWidth(10)
    private String qsFinishOnTimeCountStr ;
    /**
     * 闭合率
     */
    @JsonProperty("qsFinishRateStr")
    @ExcelProperty("总闭合率")
    @ColumnWidth(10)
    private String qsFinishRateStr;
    /**
     * 逾期整改率
     */
    @JsonProperty("qsFinishOverdueRateStr")
    @ExcelProperty("逾期整改率")
    @ColumnWidth(10)
    private String qsFinishOverdueRateStr;
    /**
     * 二次整改率
     */
    @JsonProperty("qsFinishOnTimeRateStr")
    @ExcelProperty("二次整改率")
    @ColumnWidth(10)
    private String qsFinishOnTimeRateStr;
}