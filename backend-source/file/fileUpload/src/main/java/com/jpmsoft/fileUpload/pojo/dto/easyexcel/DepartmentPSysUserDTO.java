package com.jpmsoft.fileUpload.pojo.dto.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DepartmentPSysUserDTO implements Serializable {

    private static final long serialVersionUID = -1275162418757279234L;
    @ExcelProperty(index = 0, value = "序号")
    private Integer sort;
    @JsonProperty("uName")
    @ExcelProperty(index = 1, value = "姓名")
    private String uName;
    @JsonProperty("uMtel")
    @ExcelProperty(index = 2, value = "手机号")
    private String uMtel;
    @JsonProperty("projectName")
    @ExcelProperty(index = 3, value = "所属项目")
    private String projectName;
    @JsonProperty("cName")
    @ExcelProperty(index = 4, value = "所属单位")
    private String cName;
    @JsonProperty("pdName")
    @ExcelProperty(index = 5, value = "项目部门")
    private String pdName;
    @JsonProperty("ppName")
    @ExcelProperty(index = 6, value = "项目岗位")
    private String ppName;
    @JsonProperty("uOffice")
    @ExcelProperty(index = 7, value = "办公室")
    private String uOffice;

}
