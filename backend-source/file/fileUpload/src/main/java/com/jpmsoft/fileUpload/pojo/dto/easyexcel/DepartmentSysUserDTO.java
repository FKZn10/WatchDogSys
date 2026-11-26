package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DepartmentSysUserDTO implements Serializable {

    private static final long serialVersionUID = -1275162418757279234L;
    @ExcelProperty(index = 0, value = "序号")
    private Integer sort;
    @JsonProperty("uName")
    @ExcelProperty(index = 1, value = "姓名")
    private String uName;
//    @JsonProperty("uIdcard")
//    @ExcelProperty(index = 1, value = "身份证号")
//    private String uIdcard;
    @JsonProperty("uMtel")
    @ExcelProperty(index = 2, value = "手机号")
    private String uMtel;
    @JsonProperty("dName")
    @ExcelProperty(index = 3, value = "所属部门")
    private String dName;
    @JsonProperty("pName")
    @ExcelProperty(index = 4, value = "岗位")
    private String pName;
    @JsonProperty("uOffice")
    @ExcelProperty(index = 5, value = "办公室")
    private String uOffice;

}
