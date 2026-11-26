package com.jpmsoft.fileUpload.pojo.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 10:23
 */
@Data
public class SysLoginLogVo implements Serializable {


    private static final long serialVersionUID = 7765233683269017727L;

    @ExcelIgnore
    @JsonProperty("sllId")
    private String sllId;

    @ExcelIgnore
    @JsonProperty("uId")
    private String uId;

    @JsonProperty("uName")
    @ExcelProperty("登录用户")
    private String uName;
    @JsonProperty("uAccount")
    @ExcelProperty("登录账号")
    private String uAccount;

    @JsonProperty("sllIp")
    @ExcelProperty("登录IP")
    private String sllIp;
    @JsonProperty("sllLoginType")
    @ExcelProperty("登录方式")
    private String sllLoginType;

    @JsonProperty("sllLoginTime")
    @ColumnWidth(value = 20)
    @ExcelProperty("登录时间")
    private Date sllLoginTime;

    
}
