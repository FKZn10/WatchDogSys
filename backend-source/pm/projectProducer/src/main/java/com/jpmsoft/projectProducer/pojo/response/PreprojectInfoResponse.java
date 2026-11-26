package com.jpmsoft.projectProducer.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreprojectInfoResponse {
    @JsonProperty("pId")
    private String pId;
    private String planId;
    @JsonProperty("pName")
    private String pName; // 项目名
    private int lqCount;  // 临期任务数
    @JsonIgnore
    private int lcbLq;
    @JsonIgnore
    private int yjLq;
    @JsonIgnore
    private int ejLq;
    private int yqCount;  // 逾期任务数

    @JsonProperty("pCompetentDepartmentName")
    private String pCompetentDepartmentName;
    @JsonProperty("pCompetentDepartment")
    private String pCompetentDepartment;
    @JsonProperty("pCategory")
    private String pCategory;
    @JsonProperty("pProjectManager")
    private String pProjectManager;
    @JsonProperty("pProjectManagerName")
    private String pProjectManagerName;
    @JsonProperty("pEngineer")
    private String pEngineer;
    @JsonProperty("pEngineerName")
    private String pEngineerName;

    // 应完成数量
    private int ywcCount;
    // 应完成未完成数量
    private int wwcCount;
    // 应完成未完成率 逾期率（当前应完成且未完成的数量/应完成的总数）
    private double wwcRate;
}
