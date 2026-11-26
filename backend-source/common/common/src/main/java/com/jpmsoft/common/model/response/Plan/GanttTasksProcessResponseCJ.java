package com.jpmsoft.common.model.response.Plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GanttTasksProcessResponseCJ {
    @JsonProperty("pName")
    private String pName;            // pp.P_Name
    private String taskText;         // gt.Task_Text
    private String gsSection;        // gs.Gs_Section
    private String taskType;         // gt.Task_Type
    private Date taskStartDate;     // gt.Task_Start_Date
    private Date taskEndDate;       // gt.Task_End_Date
    private Date planStart;         // gp.Plan_Start
    private Date planEnd;           // gp.Plan_End
    private String createUname;     // gto.create_uname
    private Date createTime;        // gto.Create_Time

}