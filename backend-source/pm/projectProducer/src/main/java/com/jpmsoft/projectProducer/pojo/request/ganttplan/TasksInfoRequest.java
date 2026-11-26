package com.jpmsoft.projectProducer.pojo.request.ganttplan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.util.List;

@Data
public class TasksInfoRequest extends BaseModeRequest {
    @JsonProperty("pId")
    private String pId;
    // 进度id
    private String planId;
    // 项目名称
    @JsonProperty("pName")
    private String pName;
    // 任务名称
    private String taskText;
    // 任务分级
    private String taskImportance;
    //任务分类
    @JsonProperty("taskType")
    private List<String> taskType;
    /**
     * 查询类型 1 临期 2 逾期
     */
    private String queryType;
    /**
     * 排除取消的数据
     */
    private boolean exclusionComCancel = false;
    /**
     * 排除有完成时间的数据
     * 即已完成数据
     */
    private boolean exclusionFinish = false;

    /**
     * 标段筛选
     */
    private List<String> gsIds;

    /**
     * 分级筛选
     */
    private List<String> taskImportances;

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
