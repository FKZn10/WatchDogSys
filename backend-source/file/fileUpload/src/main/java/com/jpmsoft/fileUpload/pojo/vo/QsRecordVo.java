package com.jpmsoft.fileUpload.pojo.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QsRecordVo  {

    @JsonProperty("index")
    @ExcelProperty("序号")
    @ColumnWidth(10)
    private Integer  index;

    @JsonProperty("ptCode")
    @ExcelProperty("流程编码")
    @ColumnWidth(20)
    private String  ptCode;
    @JsonProperty("pName")
    @ExcelProperty("项目名称")
    @ColumnWidth(60)
    private String  pName;

    @JsonProperty("ptType")
    @ExcelProperty("检查类型")
    @ColumnWidth(20)
    private String  ptType;


    @JsonProperty("ptGroupMemberName")
    @ExcelProperty("检查人员")
    @ColumnWidth(20)
    private String  ptGroupMemberName;

    @JsonProperty("ptEvaluationTime")
    @ExcelIgnore
    private Date ptEvaluationTime;

    @JsonProperty("ptEvaluationTimep")
    @ExcelProperty("检查时间")
    @ColumnWidth(20)
    private String ptEvaluationTimep;

    @JsonProperty("qsrRectifyRequireTime")
    @ExcelIgnore
    private Date  qsrRectifyRequireTime;

    @JsonProperty("qsrRectifyRequireTimep")
    @ExcelProperty("要求完成时间")
    @ColumnWidth(20)
    private String  qsrRectifyRequireTimep;


    @JsonProperty("stateName")
    @ExcelProperty("流程状态")
    @ColumnWidth(20)
    private String  stateName;

    @JsonProperty("qsrRectifyStatuss")
    @ExcelProperty("整改状态")
    @ColumnWidth(20)
    private String  qsrRectifyStatuss;


    @JsonProperty("ptCategory")
    @ExcelIgnore
    private String  ptCategory;

    @JsonProperty("ptEvaluationTimes")
    @ExcelIgnore
    private List<String> ptEvaluationTimes;
    @JsonProperty("ptEvaluationTimeStr")
    @ExcelIgnore
    private String ptEvaluationTimeStr;
    @JsonProperty("ptEvaluationTimeEnd")
    @ExcelIgnore
    private String ptEvaluationTimeEnd;

    @JsonProperty("qsrRectifyStatus")
    @ExcelIgnore
    private String  qsrRectifyStatus;

    @JsonProperty("state")
    @ExcelIgnore
    private Integer state;



}
