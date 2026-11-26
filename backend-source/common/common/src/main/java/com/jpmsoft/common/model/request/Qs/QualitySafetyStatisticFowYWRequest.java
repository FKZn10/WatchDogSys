package com.jpmsoft.common.model.request.Qs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QualitySafetyStatisticFowYWRequest {

    private Integer pageNum;

    private Integer pageSize;

    private String projectName;
    private String pName;

    private String projectId;

    // 指定问题节点下
    private String rootTypeNodeId;

    private String ptType;

    private String qstPath;


    @JsonProperty("qstId")
    private String qstId;

    private List<String> qstIds;
    private List<Integer> severityLevels;
    private Integer state;

    @JsonProperty("pType")
    private String pType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ptEvaluationTimeFilterStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ptEvaluationTimeFilterEnd;

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}