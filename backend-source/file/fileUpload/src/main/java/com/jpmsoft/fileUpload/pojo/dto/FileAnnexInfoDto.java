package com.jpmsoft.fileUpload.pojo.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FileAnnexInfoDto {


    @ExcelIgnore
    private String fileId;
    /**
     * 关联ID 前端传入
     */
    @ExcelIgnore
    private String fileRelationId;
    /**
     * 文件路径
     */
    @ExcelIgnore
    private String filePath;
    /**
     * 文件大小
     */
    @ExcelIgnore
    private Long fileSize;
    @ExcelIgnore
    private String fileSuffix;

    @JsonProperty("fileName")
    @ExcelProperty("文件名称")
    private String fileName;

    @JsonProperty("fileUploadTime")
    @ExcelProperty("文件上传时间")
    private Date fileUploadTime;
    @ExcelIgnore
    private String fileCreateUId;

    @JsonProperty("fileCreateUName")
    @ExcelProperty("上传人")
    private String fileCreateUName;
    @ExcelIgnore
    private String fileCode;
    @ExcelIgnore
    private String tableName;
    @ExcelIgnore
    private String tableCode;
    @ExcelIgnore
    private String columnName;
    @ExcelIgnore
    private String columnCode;

    @ExcelIgnore
    private List<String> fileUploadTimes;


    @ExcelIgnore
    @JsonProperty("page")
    private Integer page;

    @ExcelIgnore
    @JsonProperty("size")
    private Integer size;

}
