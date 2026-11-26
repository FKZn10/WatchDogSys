package com.jpmsoft.fileUpload.pojo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * 接口日志表
 */
@Data
@TableName("SYS_LoggerInfo")
public class SysLoggerInfo {

    @TableId(type = IdType.INPUT)
    @ExcelProperty(value = "lId", index = 0)
    @JsonProperty("lId")
    private String lId;
    @ExcelProperty(value = "lClientip", index = 1)
    @JsonProperty("lClientip")
    private String lClientip;
    @ExcelProperty(value = "lServerip", index = 2)
    @JsonProperty("lServerip")
    private String lServerip;
    @ExcelProperty(value = "lUrl", index = 3)
    @JsonProperty("lUrl")
    private String lUrl;
    @ExcelProperty(value = "lRequesttype", index = 4)
    @JsonProperty("lRequesttype")
    private String lRequesttype;
    @ExcelProperty(value = "lRequestmode", index = 5)
    @JsonProperty("lRequestmode")
    private String lRequestmode;
    @ExcelProperty(value = "lResponsetime", index = 6)
    @JsonProperty("lResponsetime")
    private Integer lResponsetime;
    @ExcelProperty(value = "lHttpstatus", index = 7)
    @JsonProperty("lHttpstatus")
    private String lHttpstatus;
    @ExcelProperty(value = "lRequesttime", index = 8)
    @JsonProperty("lRequesttime")
    private Date lRequesttime;
    @ExcelProperty(value = "lCreateuId", index = 9)
    @JsonProperty("lCreateuId")
    private String lCreateuId;
    @ExcelProperty(value = "lCreateuName", index = 10)
    @JsonProperty("lCreateuName")
    private String lCreateuName;
    @ExcelProperty(value = "lRequestparameter", index = 11)
    @JsonProperty("lRequestparameter")
    private String lRequestparameter;
    @ExcelProperty(value = "lContent", index = 12)
    @JsonProperty("lContent")
    private String lContent;

//    @ExcelProperty(value = "lActionEnd", index = 13)
    @JsonProperty("lActionEnd")
    private String lActionEnd;

//    @ExcelProperty(value = "lFunModule", index = 14)
    @JsonProperty("lFunModule")
    private String lFunModule;

}
