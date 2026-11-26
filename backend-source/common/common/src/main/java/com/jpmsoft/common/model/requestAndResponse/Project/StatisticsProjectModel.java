package com.jpmsoft.common.model.requestAndResponse.Project;

import java.util.List;
import java.util.Map;

public class StatisticsProjectModel {

    private Integer projectCount;
    private List<Map<String, Object>> stages;

    public Integer getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(Integer projectCount) {
        this.projectCount = projectCount;
    }

    public List<Map<String, Object>> getStages() {
        return stages;
    }

    public void setStages(List<Map<String, Object>> stages) {
        this.stages = stages;
    }
}
