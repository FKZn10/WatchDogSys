package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.fileUpload.easyexcel.annotations.ExcelValid;
import lombok.Data;

@Data
public class SysUserVo {
    @ExcelProperty(index = 0, value = "序号")
    private Integer index;

    @ExcelValid
    @JsonProperty("uName")
    @ExcelProperty(index = 1, value = "姓名")
    private String uName;

    @JsonProperty("cName")
    @ExcelProperty(index = 2, value = "所属单位")
    private String cName;

    @JsonProperty("uType")
    @ExcelProperty(index = 3, value = "用户类型")
    private String uType;

    @JsonProperty("uMtel")
    @ExcelProperty(index = 4, value = "手机")
    private String uMtel;

    @JsonProperty("uState")
    @ExcelProperty(index = 5, value = "用户状态")
    private String uState;
}
