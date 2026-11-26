package com.jpmsoft.projectProducer.pojo.request.ganttplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
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
    private String pName;          // 项目名称模糊匹配条件

    private String type;

    @JsonProperty("pCategory")
    private String pCategory;
    @JsonProperty("pCategoryList")
    private List<String> pCategoryList;
    @JsonProperty("pCompetentDepartmentNameList")
    @ClassInfo(title = "项目管理单位", type = "string", value = "pCompetentDepartmentNameList", dbValue = " prod.D_Name ")
    private List<String> pCompetentDepartmentNameList;
    @JsonProperty("pProjectManagerNameList")
    @ClassInfo(title = "项目经理", type = "string", value = "pProjectManagerNameList", dbValue = " su1.U_Name ")
    private List<String> pProjectManagerNameList;
    @JsonProperty("pEngineerNameList")
    @ClassInfo(title = "集团工程部经办人", type = "string", value = "pEngineerNameList", dbValue = " su2.U_Name ")
    private List<String> pEngineerNameList;
}
