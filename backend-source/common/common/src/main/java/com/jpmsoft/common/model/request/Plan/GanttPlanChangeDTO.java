package com.jpmsoft.common.model.request.Plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 进度计划变更
 */
@Data
public class GanttPlanChangeDTO extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String planId;
    private String planName;
    @JsonProperty("pId")
    private String pId;
    private String conId;
    private String planDId;
    private String planType;
    private Date planStart;
    private Date planEnd;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String planCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String planCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date planCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer planState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date planStateTime;
    private String planRemark;
    private String planGantt;
    private Date planActualStart;
    private Date planActualEnd;
    private Integer getPlanEnableState;
    private String planEndUid;
    private String planEndUname;
    private Integer planFinishState;
    private String secId;
    private String planPmUid;
    private String planPmUname;
    private String planApmUid;
    private String planApmUname;
    private String planCueUid;
    private String planCueUname;
    private String planSeUid;
    private String planSeUname;
    private String planPartyBId;
    private String planLeadUid;
    private String planLeadUname;
    private String planNumber;
    private String planSource;
    private String planLevel;
    private String ptId;
    private Boolean planIsSec;
    /***********************************************/

    @JsonProperty("pName")
    private String pName;
    private String conName;
    private List<GanttTasksChangeDTO> ganttTasks;
    private List<GanttTasksChangeDTO> ganttTasksRecords;
    private List<GanttLinksChangeDTO> ganttLinks;
    private  List<GantSectionDTO> gantSections;
}
