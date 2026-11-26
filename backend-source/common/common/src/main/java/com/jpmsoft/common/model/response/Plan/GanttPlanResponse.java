package com.jpmsoft.common.model.response.Plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.request.Plan.GantSectionDTO;

import java.util.Date;
import java.util.List;

/**
 * 进度计划
 */
public class GanttPlanResponse extends ProjectCommonRes {

    private String planId;
    private String planName;
    private String pId;
    private String conId;
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
    private Boolean planIsSec;
    private String ptId;
    /****************************/
    private String ptName;
    private Boolean planChanging;
    private String dName;
    private String conName;
    private String secName;
    private List<Object> ganttTasks;
    private List<Object> ganttLinks;
    private List<GantSectionDTO> gantSections;

    private Integer overdueCount;
    private Integer temporaryCount;
    private String  pProjectManager;
    private String pProjectManagerName;

    @JsonProperty("pCategory")
    private String pCategory;

    @JsonProperty("pDepartment")
    private String pDepartment;

    @JsonProperty("pDepartmentName")
    private String pDepartmentName;

    private Integer alreadyOverCount;

    private Integer underApprovalCount;

    private Integer mustHandleCount;

    private Integer notStartCount;

    private int milestoneCount;

    private int firstLevelCount;

    private int secondLevelCount;

    public int getMilestoneCount() {
        return milestoneCount;
    }

    public void setMilestoneCount(int milestoneCount) {
        this.milestoneCount = milestoneCount;
    }

    public int getFirstLevelCount() {
        return firstLevelCount;
    }

    public void setFirstLevelCount(int firstLevelCount) {
        this.firstLevelCount = firstLevelCount;
    }

    public int getSecondLevelCount() {
        return secondLevelCount;
    }

    public void setSecondLevelCount(int secondLevelCount) {
        this.secondLevelCount = secondLevelCount;
    }

    public Integer getNotStartCount() {
        return notStartCount;
    }

    public void setNotStartCount(Integer notStartCount) {
        this.notStartCount = notStartCount;
    }

    public Integer getAlreadyOverCount() {
        return alreadyOverCount;
    }

    public void setAlreadyOverCount(Integer alreadyOverCount) {
        this.alreadyOverCount = alreadyOverCount;
    }

    public Integer getUnderApprovalCount() {
        return underApprovalCount;
    }

    public void setUnderApprovalCount(Integer underApprovalCount) {
        this.underApprovalCount = underApprovalCount;
    }

    public Integer getMustHandleCount() {
        return mustHandleCount;
    }

    public void setMustHandleCount(Integer mustHandleCount) {
        this.mustHandleCount = mustHandleCount;
    }

    public String getpDepartmentName() {
        return pDepartmentName;
    }

    public void setpDepartmentName(String pDepartmentName) {
        this.pDepartmentName = pDepartmentName;
    }

    public String getpDepartment() {
        return pDepartment;
    }

    public void setpDepartment(String pDepartment) {
        this.pDepartment = pDepartment;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public String getConId() {
        return conId;
    }

    @Override
    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getPlanDId() {
        return planDId;
    }

    public void setPlanDId(String planDId) {
        this.planDId = planDId;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public Date getPlanStart() {
        return planStart;
    }

    public void setPlanStart(Date planStart) {
        this.planStart = planStart;
    }

    public Date getPlanEnd() {
        return planEnd;
    }

    public void setPlanEnd(Date planEnd) {
        this.planEnd = planEnd;
    }

    public String getPlanCreateUid() {
        return planCreateUid;
    }

    public void setPlanCreateUid(String planCreateUid) {
        this.planCreateUid = planCreateUid;
    }

    public String getPlanCreateUname() {
        return planCreateUname;
    }

    public void setPlanCreateUname(String planCreateUname) {
        this.planCreateUname = planCreateUname;
    }

    public Integer getPlanState() {
        return planState;
    }

    public void setPlanState(Integer planState) {
        this.planState = planState;
    }

    public String getPlanRemark() {
        return planRemark;
    }

    public void setPlanRemark(String planRemark) {
        this.planRemark = planRemark;
    }

    public String getPlanGantt() {
        return planGantt;
    }

    public void setPlanGantt(String planGantt) {
        this.planGantt = planGantt;
    }

    public Date getPlanStateTime() {
        return planStateTime;
    }

    public void setPlanStateTime(Date planStateTime) {
        this.planStateTime = planStateTime;
    }

    public Date getPlanCreateTime() {
        return planCreateTime;
    }

    public void setPlanCreateTime(Date planCreateTime) {
        this.planCreateTime = planCreateTime;
    }

    public Date getPlanActualStart() {
        return planActualStart;
    }

    public void setPlanActualStart(Date planActualStart) {
        this.planActualStart = planActualStart;
    }

    public Date getPlanActualEnd() {
        return planActualEnd;
    }

    public void setPlanActualEnd(Date planActualEnd) {
        this.planActualEnd = planActualEnd;
    }

    public Integer getGetPlanEnableState() {
        return getPlanEnableState;
    }

    public void setGetPlanEnableState(Integer getPlanEnableState) {
        this.getPlanEnableState = getPlanEnableState;
    }

    public String getPlanEndUid() {
        return planEndUid;
    }

    public void setPlanEndUid(String planEndUid) {
        this.planEndUid = planEndUid;
    }

    public String getPlanEndUname() {
        return planEndUname;
    }

    public void setPlanEndUname(String planEndUname) {
        this.planEndUname = planEndUname;
    }

    public Integer getPlanFinishState() {
        return planFinishState;
    }

    public void setPlanFinishState(Integer planFinishState) {
        this.planFinishState = planFinishState;
    }

    @Override
    public String getSecId() {
        return secId;
    }

    @Override
    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getPlanPmUid() {
        return planPmUid;
    }

    public void setPlanPmUid(String planPmUid) {
        this.planPmUid = planPmUid;
    }

    public String getPlanPmUname() {
        return planPmUname;
    }

    public void setPlanPmUname(String planPmUname) {
        this.planPmUname = planPmUname;
    }

    public String getPlanApmUid() {
        return planApmUid;
    }

    public void setPlanApmUid(String planApmUid) {
        this.planApmUid = planApmUid;
    }

    public String getPlanApmUname() {
        return planApmUname;
    }

    public void setPlanApmUname(String planApmUname) {
        this.planApmUname = planApmUname;
    }

    public String getPlanCueUid() {
        return planCueUid;
    }

    public void setPlanCueUid(String planCueUid) {
        this.planCueUid = planCueUid;
    }

    public String getPlanCueUname() {
        return planCueUname;
    }

    public void setPlanCueUname(String planCueUname) {
        this.planCueUname = planCueUname;
    }

    public String getPlanSeUid() {
        return planSeUid;
    }

    public void setPlanSeUid(String planSeUid) {
        this.planSeUid = planSeUid;
    }

    public String getPlanSeUname() {
        return planSeUname;
    }

    public void setPlanSeUname(String planSeUname) {
        this.planSeUname = planSeUname;
    }

    public String getPlanPartyBId() {
        return planPartyBId;
    }

    public void setPlanPartyBId(String planPartyBId) {
        this.planPartyBId = planPartyBId;
    }

    public String getPlanLeadUid() {
        return planLeadUid;
    }

    public void setPlanLeadUid(String planLeadUid) {
        this.planLeadUid = planLeadUid;
    }

    public String getPlanLeadUname() {
        return planLeadUname;
    }

    public void setPlanLeadUname(String planLeadUname) {
        this.planLeadUname = planLeadUname;
    }

    public Boolean getPlanIsSec() {
        return planIsSec;
    }

    public void setPlanIsSec(Boolean planIsSec) {
        this.planIsSec = planIsSec;
    }

    public String getPtId() {
        return ptId;
    }

    public void setPtId(String ptId) {
        this.ptId = ptId;
    }

    public String getPtName() {
        return ptName;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
    }

    public Boolean getPlanChanging() {
        return planChanging;
    }

    public void setPlanChanging(Boolean planChanging) {
        this.planChanging = planChanging;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public List<Object> getGanttTasks() {
        return ganttTasks;
    }

    public void setGanttTasks(List<Object> ganttTasks) {
        this.ganttTasks = ganttTasks;
    }

    public List<Object> getGanttLinks() {
        return ganttLinks;
    }

    public void setGanttLinks(List<Object> ganttLinks) {
        this.ganttLinks = ganttLinks;
    }

    public List<GantSectionDTO> getGantSections() {
        return gantSections;
    }

    public void setGantSections(List<GantSectionDTO> gantSections) {
        this.gantSections = gantSections;
    }

    public Integer getOverdueCount() {
        return overdueCount;
    }

    public void setOverdueCount(Integer overdueCount) {
        this.overdueCount = overdueCount;
    }

    public Integer getTemporaryCount() {
        return temporaryCount;
    }

    public void setTemporaryCount(Integer temporaryCount) {
        this.temporaryCount = temporaryCount;
    }

    public String getpProjectManager() {
        return pProjectManager;
    }

    public void setpProjectManager(String pProjectManager) {
        this.pProjectManager = pProjectManager;
    }

    public String getpProjectManagerName() {
        return pProjectManagerName;
    }

    public void setpProjectManagerName(String pProjectManagerName) {
        this.pProjectManagerName = pProjectManagerName;
    }
}
