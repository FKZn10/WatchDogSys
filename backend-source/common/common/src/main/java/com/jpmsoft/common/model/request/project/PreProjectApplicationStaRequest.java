package com.jpmsoft.common.model.request.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PreProjectApplicationStaRequest {

    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;

    @JsonProperty("range")
    private String range;  //范围 周  月   为空就默认用月

    @JsonProperty("rangeTimes")
    private List<String> rangeTimes;  //指定时间

    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;  //分管部门

    private String rangeTimeStart;
    private String rangeTimeEnd;

    private Integer page;

    private Integer size;

    @JsonProperty("fileModuleName")
    private String fileModuleName;  //模块名
}
