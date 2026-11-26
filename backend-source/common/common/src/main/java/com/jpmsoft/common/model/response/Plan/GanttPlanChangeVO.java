package com.jpmsoft.common.model.response.Plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.request.Plan.GantSectionDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 进度计划变更
 */
@Data
public class GanttPlanChangeVO extends ProjectCommonRes {

    private String planId;
    private String planName;
    private String planDId;
    private String planType;
    private Date planStart;
    private Date planEnd;
    private String planCreateUid;
    private String planCreateUname;
    private Integer planState;
    private String planRemark;
    private String planGantt;
    private Date planStateTime;
    private Date planCreateTime;
    private Date planActualStart;
    private Date planActualEnd;
    private Integer getPlanEnableState;
    private String planEndUid;
    private String planEndUname;
    private Integer planFinishState;
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
    /****************************/
    @JsonProperty("pProjectManager")
    private String pProjectManager;
    @JsonProperty("pProjectManagerName")
    private String pProjectManagerName;
    private String ptName;
    @JsonProperty("dName")
    private String dName;
    private String conName;
    private String secName;
    private List<GanttTasksChangeVO> ganttTasks;
    private List<GanttTasksChangeVO> ganttTasksRecords;
    private List<GanttLinksChangeVO> ganttLinks;
    private List<GantSectionDTO> gantSections;

    @JsonProperty("pCategory")
    private String pCategory;

}
