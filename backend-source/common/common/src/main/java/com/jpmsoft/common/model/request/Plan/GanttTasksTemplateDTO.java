package com.jpmsoft.common.model.request.Plan;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 模板任务
 */
@Data
public class GanttTasksTemplateDTO extends BaseModeRequest {

    private String taskId;
    private String ptId;
    private String taskParent;
    private Date taskStartDate;
    private Date taskEndDate;
    private String taskText;
    private Float taskProgress;
    private Float taskDuration;
    private Date taskFinishTime;
    private String taskDoc;
    private String taskUid;
    private String taskUname;
    private String taskType;
    private Integer taskState;
    private String taskPriority;
    private String taskPreTaskId;
    private String taskOutlineLevel;
    private String taskRemark;
    private String taskMode;
    private Integer taskSort;
    private Integer taskMilestone;
    private String taskStartTCode;
    private String taskStartConditions;
    private String taskEndTCode;
    private String taskEndConditions;
    private Boolean taskMust;
    @JsonProperty("pProjectStage")
    private String pProjectStage;
    private String conExecution;
    private String taskImportance;
    private String cplId;

    private List<GanttTasksTemplateDTO> childNodes;
}
