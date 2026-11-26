package com.jpmsoft.fileUpload.pojo.dto;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysLoggerInfoDto {



    @ExcelIgnore
    @JsonProperty("lId")
    @ExcelProperty("lId")
    private String lId;
    @JsonProperty("lClientip")
    @ExcelProperty("客户端IP")
    private String lClientip;
    @JsonProperty("lServerip")
    @ExcelProperty("服务端IP")
    private String lServerip;
    @JsonProperty("lUrl")
    @ExcelProperty("URL")
    private String lUrl;

    @JsonProperty("interfaceName")
    @ExcelProperty("接口名称")
    private String interfaceName;
    @JsonProperty("lRequesttype")
    @ExcelProperty("请求类型")
    private String lRequesttype;
    @JsonProperty("lRequestmode")
    @ExcelProperty("请求方式")
    private String lRequestmode;
    @JsonProperty("lResponsetime")
    @ExcelProperty("请求耗时(ms)")
    private Integer lResponsetime;
    @JsonProperty("lHttpstatus")
    @ExcelProperty("HTTP状态码")
    private String lHttpstatus;
    @JsonProperty("lRequesttime")
    @ExcelProperty("请求时间")
    private Date lRequesttime;
    @JsonProperty("lCreateuId")
    @ExcelProperty("请求人ID")
    private String lCreateuId;
    @JsonProperty("lCreateuName")
    @ExcelProperty("请求创建人")
    private String lCreateuName;
    @JsonProperty("lRequestparameter")
    @ExcelProperty("数据参数")
    private String lRequestparameter;

    @JsonProperty("lContent")
    @ExcelProperty("lContent")
    private String lContent;

    @JsonProperty("lActionEnd")
    @ExcelProperty("操作端")
    private String lActionEnd;

    //    @ExcelProperty(value = "lFunModule", index = 14)


    @JsonProperty("powerName")
    @ExcelProperty("所属模块")
    private String powerName;

    @JsonProperty("lFunModule")
    @ExcelProperty("所属模块编码")
    private String lFunModule;


    @ExcelIgnore
    @JsonProperty("lRequesttimes")
    private List<String> lRequesttimes;
}
