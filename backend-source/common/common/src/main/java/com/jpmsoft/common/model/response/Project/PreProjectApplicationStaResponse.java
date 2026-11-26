package com.jpmsoft.common.model.response.Project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PreProjectApplicationStaResponse {
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;

    @JsonProperty("dId")
    private String dId;
    private String pCompetentDepartmentName;  //分管部门

    private Integer fileCount;  //项目数据条数

    private Integer fileModuleCount;  //模块条数

    private String fileModuleName; //模块名称

    private Integer fileCountSum;  //项目数据条数汇总
    private Integer fileModuleCountSum;  //模块条数汇总

}
