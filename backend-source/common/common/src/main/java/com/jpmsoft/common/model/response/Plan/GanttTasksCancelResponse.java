package com.jpmsoft.common.model.response.Plan;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GanttTasksCancelResponse {
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;          // pp.P_Name
    private String taskText;       // gt.Task_Text
    private String gsSection;      // gs.Gs_Section
    private String taskType;       // gt.Task_Type
    private Date planStart;       // gp.Plan_Start
    private Date planEnd;         // gp.Plan_End
    private String createUname;   // gto.create_uname
    private Date createTime;      // gto.Create_Time

    private String id;
    private String createUid;
    private Integer state;
    private Date stateTime;
    private String taskId;
    private String gtcExplanation;
}
