package com.jpmsoft.fileUpload.pojo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
@Data
public class FileAnnexInfoWithFileVo {
    @JsonProperty("fileId")
    @ExcelProperty("id")
    @ColumnWidth(40)
    private String fileId;
    /**
     * 关联ID 前端传入
     */
    @JsonProperty("fileRelationId")
    @ExcelProperty("关联ID")
    @ColumnWidth(40)
    private String fileRelationId;
    /**
     * 文件路径
     */
    @JsonProperty("filePath")
    @ExcelProperty("文件路径")
    @ColumnWidth(40)
    private String filePath;
    /**
     * 文件大小
     */
    @JsonProperty("fileSize")
    @ExcelProperty("文件大小")
    @ColumnWidth(40)
    private Long fileSize;
    @JsonProperty("fileSuffix")
    @ExcelProperty("文件后缀")
    @ColumnWidth(40)
    private String fileSuffix;
    @JsonProperty("fileSize")
    @ExcelProperty("文件名称")
    @ColumnWidth(40)
    private String fileName;
    @JsonProperty("fileUploadTime")
    @ExcelProperty("文件上传时间")
    @ColumnWidth(40)
    private Date fileUploadTime;
    @JsonProperty("fileCreateUName")
    @ExcelProperty("文件上传人员")
    @ColumnWidth(40)
    private String fileCreateUName;
    @JsonProperty("fileCode")
    @ExcelProperty("文档编码")
    @ColumnWidth(40)
    private String fileCode;
    @JsonProperty("code")
    @ExcelProperty("编码")
    @ColumnWidth(40)
    private String code;
}