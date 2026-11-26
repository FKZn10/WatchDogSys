package com.jpmsoft.common.model.request.Plan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

/**
 * 进度计划
 */
public class GanttPlanRequest extends BaseFileRequest {

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
    /*****************/
    private String pName;
    private String conName;
    private List<String> planTypes;
    private List<String> excludeTypes;
    private List<GanttTasksRequest> ganttTasks;
    private List<GantSectionDTO> gantSections;
    private List<GanttLinksRequest> ganttLinks;
    /**
     * 1：正常未到期
     * 2：到期前30日历天内
     * 3：逾期任务
     * 4：逾期完成
     * 5：按期期完成
     * 6：暂停
     */
    private String queryType;
    /**
     * 项目计划开始年份筛选字段
     */
    private String pPlanStartYear;
    private String pCompetentDepartment;
    private String ptId;

    // 计划开始时间 范围查询
    private List<String> planStartDates;
    // 计划结束时间 范围查询
    private List<String> planEndDates;

    @ClassInfo(title = "项目名称",type="string",value = "pNameList",dbValue = " pro.p_name ",no = 1)
    @JsonProperty("pNameList")
    private List<String> pNameList;
    @ClassInfo(title = "分支机构",type="string",value = "pDepartmentNameList",dbValue = " d2.D_Name ",no = 3)
    @JsonProperty("pDepartmentNameList")
    private List<String> pDepartmentNameList;
    @ClassInfo(title = "项目管理单位",type="string",value = "pCompetentDepartmentNameList",dbValue = " prod.D_Name ",no = 2)
    @JsonProperty("pCompetentDepartmentNameList")
    private List<String> pCompetentDepartmentNameList;
    @ClassInfo(title = "建设模式",type="dictionary",value = "pBuildModelList",dbValue = " pro.p_Build_Model ",dictionary = "JPMsoft_JSMS",no = 4)
    @JsonProperty("pBuildModelList")
    private List<String> pBuildModelList;
    @ClassInfo(title = "创建人",type="string",value = "createUnameList",dbValue = " mn.plan_create_Uname ",no = 5)
    private List<String> createUnameList;
    @ClassInfo(title = "创建时间",type="date",value = "createTime",dbValue = " mn.plan_create_Time",no = 6)
    private String createTime;
    @ClassInfo(title = "流程状态",type="dictionary",value = "stateList",dbValue = "mn.plan_state",dictionary = "JPMsoft_LCZT",no = 7)
    private List<Integer> stateList;

    public List<String> getPlanStartDates() {
        return planStartDates;
    }

    public void setPlanStartDates(List<String> planStartDates) {
        this.planStartDates = planStartDates;
    }

    public List<String> getPlanEndDates() {
        return planEndDates;
    }

    public void setPlanEndDates(List<String> planEndDates) {
        this.planEndDates = planEndDates;
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

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

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


    public String getSecId() {
        return secId;
    }

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

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public List<String> getPlanTypes() {
        return planTypes;
    }

    public void setPlanTypes(List<String> planTypes) {
        this.planTypes = planTypes;
    }

    public List<String> getExcludeTypes() {
        return excludeTypes;
    }

    public void setExcludeTypes(List<String> excludeTypes) {
        this.excludeTypes = excludeTypes;
    }

    public List<GanttTasksRequest> getGanttTasks() {
        return ganttTasks;
    }

    public void setGanttTasks(List<GanttTasksRequest> ganttTasks) {
        this.ganttTasks = ganttTasks;
    }

    public List<GantSectionDTO> getGantSections() {
        return gantSections;
    }

    public void setGantSections(List<GantSectionDTO> gantSections) {
        this.gantSections = gantSections;
    }

    public List<GanttLinksRequest> getGanttLinks() {
        return ganttLinks;
    }

    public void setGanttLinks(List<GanttLinksRequest> ganttLinks) {
        this.ganttLinks = ganttLinks;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getpPlanStartYear() {
        return pPlanStartYear;
    }

    public void setpPlanStartYear(String pPlanStartYear) {
        this.pPlanStartYear = pPlanStartYear;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }

    public String getPtId() {
        return ptId;
    }

    public void setPtId(String ptId) {
        this.ptId = ptId;
    }

    public List<String> getpNameList() {
        return pNameList;
    }

    public void setpNameList(List<String> pNameList) {
        this.pNameList = pNameList;
    }

    public List<String> getpDepartmentNameList() {
        return pDepartmentNameList;
    }

    public void setpDepartmentNameList(List<String> pDepartmentNameList) {
        this.pDepartmentNameList = pDepartmentNameList;
    }

    public List<String> getpCompetentDepartmentNameList() {
        return pCompetentDepartmentNameList;
    }

    public void setpCompetentDepartmentNameList(List<String> pCompetentDepartmentNameList) {
        this.pCompetentDepartmentNameList = pCompetentDepartmentNameList;
    }

    public List<String> getpBuildModelList() {
        return pBuildModelList;
    }

    public void setpBuildModelList(List<String> pBuildModelList) {
        this.pBuildModelList = pBuildModelList;
    }

    public List<String> getCreateUnameList() {
        return createUnameList;
    }

    public void setCreateUnameList(List<String> createUnameList) {
        this.createUnameList = createUnameList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Integer> getStateList() {
        return stateList;
    }

    public void setStateList(List<Integer> stateList) {
        this.stateList = stateList;
    }
}
