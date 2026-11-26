package com.jpmsoft.projectProducer.pojo.dto.ganttPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/4/19 16:37
 */
@Data
public class GanttTasksCjDTO extends BaseModeRequest {

    @JsonProperty("pId")
    private String pId;
    @ClassInfo(title = "任务名称", type = "string", value = "taskTextList", dbValue = " mn.Task_Text ", no = 1)
    private List<String> taskTextList;
    @ClassInfo(title = "任务标段", type = "string", value = "gsSectionList", dbValue = " gs.Gs_Section ", no = 2)
    private List<String> gsSectionList;
    @ClassInfo(title = "任务分类", type = "string", value = "taskTypeList", dbValue = " mn.Task_Type ", no = 3)
    private List<String> taskTypeList;
    /**
     * 未开始 1
     * 办理中 2
     * 已取消 3
     * 逾期 4
     * 逾期完成 5
     * 正常完成 6
     */
//    @ClassInfo(title = "任务状态", type = "dictionary", value = "taskStatusList", dbValue = "state")
    private List<Integer> taskStatusList;
    @ClassInfo(title = "计划开始时间", type = "date", value = "taskStartDate", dbValue = " mn.Task_Start_Date", no = 5)
    private Date taskStartDate;
    @ClassInfo(title = "计划结束时间", type = "date", value = "taskEndDate", dbValue = " mn.Task_End_Date", no = 6)
    private Date taskEndDate;
    @ClassInfo(title = "实际开始时间", type = "date", value = "taskBeginTime", dbValue = " mn.Task_Begin_Time", no = 7)
    private Date taskBeginTime;
    @ClassInfo(title = "实际结束时间", type = "date", value = "taskFinishTime", dbValue = " mn.Task_Finish_Time", no = 8)
    private Date taskFinishTime;

    private List<String> taskImportance;

    private String planId;
}
