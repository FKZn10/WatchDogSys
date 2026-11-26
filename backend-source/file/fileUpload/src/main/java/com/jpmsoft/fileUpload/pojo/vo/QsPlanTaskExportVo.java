package com.jpmsoft.fileUpload.pojo.vo;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class QsPlanTaskExportVo {


    @ExcelIgnore
    @JsonProperty("id")
    private String id;

    @JsonProperty("ptCode")
    @ExcelProperty("流程编码")
    @ColumnWidth(60)
    private String ptCode;

    @ExcelProperty("项目名称")
    @ColumnWidth(60)
    @JsonProperty("pName")
    private String pName;

    @ExcelProperty("检查类型")
    @ColumnWidth(60)
    @JsonProperty("ptType")
    private String ptType;



    @ExcelIgnore
    @JsonProperty("ptEvaluationTime")
    private Date ptEvaluationTime;

    @ExcelProperty("检查日期")
    @ColumnWidth(40)
    @JsonProperty("ptEvaluationDate")
    private String ptEvaluationDate;


    @ExcelProperty("检查人员")
    @ColumnWidth(40)
    @JsonProperty("ptGroupMemberName")
    private String ptGroupMemberName;


    @ExcelProperty("施工单位")
    @ColumnWidth(60)
    @JsonProperty("ptContractorName")
    private String ptContractorName;


    @ExcelProperty("监理单位")
    @ColumnWidth(60)
    @JsonProperty("ptSupervisorName")
    private String ptSupervisorName;

    @JsonProperty("state")
    @ExcelIgnore
    private Integer state;

    @ExcelProperty("流程状态")
    @ColumnWidth(60)
    @JsonProperty("stateName")
    private String stateName;


    @ExcelProperty("问题数量")
    @ColumnWidth(20)
    @JsonProperty("qsRecordCount")
    private Integer qsRecordCount = 0;

    @ExcelProperty("整改数量")
    @ColumnWidth(20)
    @JsonProperty("qsFinishCount")
    private Integer qsFinishCount = 0;

    @JsonProperty("qsReplyRate")
    @ExcelIgnore
    private BigDecimal qsReplyRate = BigDecimal.ZERO;

    /**
     * 要求整改时间
     */
    @ExcelProperty("要求整改时间")
    @ColumnWidth(20)
    @JsonProperty("ptRectifyRequireTimeDate")
    private String ptRectifyRequireTimeDate;
    @ExcelIgnore
    private Date ptRectifyRequireTime;
    /**
     * 实际整改时间
     */
    @ExcelProperty("实际整改时间")
    @ColumnWidth(20)
    @JsonProperty("ptRectifyActualTimeDate")
    private String ptRectifyActualTimeDate;
    @ExcelIgnore
    private Date ptRectifyActualTime;
    //超期天数
    @ExcelProperty("超期天数")
    @ColumnWidth(20)
    @JsonProperty("overdueDays")
    private BigDecimal overdueDays;
    @ExcelProperty("回复率（%）")
    @ColumnWidth(20)
    @JsonProperty("qsReplyRatelv")
    private String qsReplyRatelv;

    @ExcelIgnore
    @JsonProperty("qsFinishRate")
    private BigDecimal qsFinishRate = BigDecimal.ZERO;

    @ExcelProperty("闭合率（%）")
    @ColumnWidth(20)
    @JsonProperty("qsFinishRatelv")
    private String qsFinishRatelv;


}
