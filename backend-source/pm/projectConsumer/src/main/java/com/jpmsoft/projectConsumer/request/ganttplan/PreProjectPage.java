package com.jpmsoft.projectConsumer.request.ganttplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreProjectPage extends BaseModeRequest {
    @JsonProperty("pName")
    private String pName;     // 项目名称模糊匹配条件

    private String type;

    @JsonProperty("pCategory")
    private String pCategory;
    @JsonProperty("pCategoryList")
    private List<String> pCategoryList;
    @JsonProperty("pCompetentDepartmentNameList")
    private List<String> pCompetentDepartmentNameList;
    @JsonProperty("pProjectManagerNameList")
    private List<String> pProjectManagerNameList;
    @JsonProperty("pEngineerNameList")
    private List<String> pEngineerNameList;
}
