package com.jpmsoft.fileUpload.pojo.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QualitySafetyStatisticFowYWVO {

    @ExcelIgnore
    @JsonProperty("qstId")
    private String qstId;

    @JsonProperty("qstName")
    @ExcelProperty("问题名称")
    @ColumnWidth(80)
    private String qstName;


    @JsonProperty("qstPath")
    @ExcelProperty("问题全路径")
    @ColumnWidth(80)
    private String qstPath;
    /**
     * 严重程度：
     * 1： 空 或者 /
     * 2：严重     等同于重大问题，qstIsImportant=1
     * 3：较严重
     * 4：一般
     * 5：轻微
     */
    @JsonProperty("severityLevelName")
    @ExcelProperty("严重程度")
    @ColumnWidth(20)
    private String severityLevelName;
    @JsonProperty("count")
    @ExcelProperty("问题数量")
    @ColumnWidth(20)
    private Integer count;

    @JsonProperty("qsFinish")
    @ExcelProperty("整改数量")
    @ColumnWidth(20)
    private Integer qsFinish;
    @JsonProperty("qsFinishRateStr")
    @ExcelProperty("整改率")
    @ColumnWidth(20)
    private String qsFinishRateStr;


}