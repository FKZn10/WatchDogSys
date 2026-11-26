package com.jpmsoft.common.model.requestAndResponse.Plan;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.common.model.request.Plan.GanttLinksTemplateDTO;
import com.jpmsoft.common.model.request.Plan.GanttTasksTemplateDTO;

import java.util.Date;
import java.util.List;

/**
 * 进度计划模板
 */
public class GanttPlanTemplateModel extends BaseModeRequest {

    private String ptId;
    private String ptName;
    private String ptPType;
    private String ptPlanType;
    private String ptFile;
    private String ptGantt;
    private String ptRemark;
    private Integer ptState;
    private String ptCreateUid;
    private String ptCreateUname;
    private Date ptCreateTime;
    private String ptEditUid;
    private String ptEditUname;
    private Date ptEditTime;
    private Integer ptAutoPlan;
    private String ptTemplateType;
    private String ptFileIds;

    /**
     * 查询指定的计划类型集合
     */
    private List<String> ptPlanTypes;
    private List<String> excludeTypes;
    private List<GanttLinksTemplateDTO> ganttLinksTemplates;
    private List<GanttTasksTemplateDTO> ganttTasksTemplates;

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

    public String getPtPType() {
        return ptPType;
    }

    public void setPtPType(String ptPType) {
        this.ptPType = ptPType;
    }

    public String getPtPlanType() {
        return ptPlanType;
    }

    public void setPtPlanType(String ptPlanType) {
        this.ptPlanType = ptPlanType;
    }

    public String getPtFile() {
        return ptFile;
    }

    public void setPtFile(String ptFile) {
        this.ptFile = ptFile;
    }

    public String getPtGantt() {
        return ptGantt;
    }

    public void setPtGantt(String ptGantt) {
        this.ptGantt = ptGantt;
    }

    public String getPtRemark() {
        return ptRemark;
    }

    public void setPtRemark(String ptRemark) {
        this.ptRemark = ptRemark;
    }

    public Integer getPtState() {
        return ptState;
    }

    public void setPtState(Integer ptState) {
        this.ptState = ptState;
    }

    public String getPtCreateUid() {
        return ptCreateUid;
    }

    public void setPtCreateUid(String ptCreateUid) {
        this.ptCreateUid = ptCreateUid;
    }

    public String getPtCreateUname() {
        return ptCreateUname;
    }

    public void setPtCreateUname(String ptCreateUname) {
        this.ptCreateUname = ptCreateUname;
    }

    public Date getPtCreateTime() {
        return ptCreateTime;
    }

    public void setPtCreateTime(Date ptCreateTime) {
        this.ptCreateTime = ptCreateTime;
    }

    public String getPtEditUid() {
        return ptEditUid;
    }

    public void setPtEditUid(String ptEditUid) {
        this.ptEditUid = ptEditUid;
    }

    public String getPtEditUname() {
        return ptEditUname;
    }

    public void setPtEditUname(String ptEditUname) {
        this.ptEditUname = ptEditUname;
    }

    public Date getPtEditTime() {
        return ptEditTime;
    }

    public void setPtEditTime(Date ptEditTime) {
        this.ptEditTime = ptEditTime;
    }

    public Integer getPtAutoPlan() {
        return ptAutoPlan;
    }

    public void setPtAutoPlan(Integer ptAutoPlan) {
        this.ptAutoPlan = ptAutoPlan;
    }

    public String getPtTemplateType() {
        return ptTemplateType;
    }

    public void setPtTemplateType(String ptTemplateType) {
        this.ptTemplateType = ptTemplateType;
    }

    public String getPtFileIds() {
        return ptFileIds;
    }

    public void setPtFileIds(String ptFileIds) {
        this.ptFileIds = ptFileIds;
    }

    public List<String> getPtPlanTypes() {
        return ptPlanTypes;
    }

    public void setPtPlanTypes(List<String> ptPlanTypes) {
        this.ptPlanTypes = ptPlanTypes;
    }

    public List<String> getExcludeTypes() {
        return excludeTypes;
    }

    public void setExcludeTypes(List<String> excludeTypes) {
        this.excludeTypes = excludeTypes;
    }

    public List<GanttLinksTemplateDTO> getGanttLinksTemplates() {
        return ganttLinksTemplates;
    }

    public void setGanttLinksTemplates(List<GanttLinksTemplateDTO> ganttLinksTemplates) {
        this.ganttLinksTemplates = ganttLinksTemplates;
    }

    public List<GanttTasksTemplateDTO> getGanttTasksTemplates() {
        return ganttTasksTemplates;
    }

    public void setGanttTasksTemplates(List<GanttTasksTemplateDTO> ganttTasksTemplates) {
        this.ganttTasksTemplates = ganttTasksTemplates;
    }
}
