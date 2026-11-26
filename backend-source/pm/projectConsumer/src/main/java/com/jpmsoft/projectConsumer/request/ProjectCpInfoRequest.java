package com.jpmsoft.projectConsumer.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCpInfoRequest {
    private List<String> cplIds;
    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
    @JsonProperty("pDepartmentName")
    private String pDepartmentName;
    @JsonProperty("pName")
    private String pName;
    private Integer size;
    private Integer page;
    private String cpdName;
    private String fileName;
    @JsonProperty("pBuildModel")
    private String pBuildModel;
    private String createUname;
    private String fileApprovalTime;
    private String createTime;
}
