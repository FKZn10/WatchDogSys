package com.jpmsoft.fileUpload.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysDownloadLogExportDto {
    @ExcelIgnore
    @JsonProperty("id")
    private String id;
    @ExcelIgnore
    private String dlUid;

    @JsonProperty("dlFileName")
    @ExcelProperty("文件名称")
    private String dlFileName;

    @JsonProperty("dlUname")
    @ExcelProperty("下载人")
    private String dlUname;
    @ExcelIgnore
    private String dlFileId;


    @ColumnWidth(value = 20)
    @JsonProperty("dlTime")
    @ExcelProperty("下载时间")
    private Date dlTime;
    @ExcelIgnore
    private String dlFormId;


    @ExcelIgnore
    private List<String> dlTimes;
}
