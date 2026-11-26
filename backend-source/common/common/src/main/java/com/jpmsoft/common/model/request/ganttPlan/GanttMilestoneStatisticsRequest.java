package com.jpmsoft.common.model.request.ganttPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author tianhelin
 * @date 2025/5/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GanttMilestoneStatisticsRequest extends BasePageEntity {
    @JsonProperty("pName")
    @ClassInfo(title = "项目名称", type = "string", value = "pName", dbValue = "mn.P_Name")
    private List<String> pName;

    @ClassInfo(title = "标段名称", type = "string", value = "gsName", dbValue = "mn.Gs_Name")
    private List<String> gsName;

    @JsonProperty("pCategory")
    @ClassInfo(title = "项目分类", type = "dictionary", value = "pCategory", dbValue = "mn.P_Category", dictionary = "JPMsoft_NDTZXMFL")
    private List<String> pCategory;

    @JsonProperty("pCompetentDepartmentName")
    @ClassInfo(title = "项目管理单位", type = "string", value = "pCompetentDepartmentName", dbValue = "d1.D_Name")
    private List<String> pCompetentDepartmentName;

    @JsonProperty("pDepartmentName")
    @ClassInfo(title = "事业部/分支机构", type = "string", value = "pDepartmentName", dbValue = "d2.D_Name")
    private List<String> pDepartmentName;

    @JsonProperty("pProjectStage")
    @ClassInfo(title = "项目阶段", type = "dictionary", value = "pProjectStage", dbValue = "mn.P_Project_Stage", dictionary = "JPMsoft_XMJDZT")
    private List<String> pProjectStage;

    @JsonProperty("pInvTotal")
    @ClassInfo(title = "总投资", type = "money", value = "pInvTotal", dbValue = "mn.P_Inv_Total / 10000")
    private List<BigDecimal> pInvTotal;

    @ClassInfo(title = "立项时间", type = "date", value = "setTime", dbValue = "mn.set_time")
    private Date setTime;

    @ClassInfo(title = "立项时间状态", type = "dictionary", value = "setTimeStatus", dbValue = "mn.Set_Time_Status", dictionary = "JPMsoft_LCBJDZT")
    private List<String> setTimeStatus;

    @ClassInfo(title = "开工时间", type = "date", value = "startTime", dbValue = "mn.Start_Time")
    private Date startTime;

    @ClassInfo(title = "开工时间状态", type = "dictionary", value = "startTimeStatus", dbValue = "mn.Start_Time_Status", dictionary = "JPMsoft_LCBJDZT")
    private List<String> startTimeStatus;

    @ClassInfo(title = "完工时间", type = "date", value = "finishTime", dbValue = "mn.Finish_Time")
    private Date finishTime;

    @ClassInfo(title = "完工时间状态", type = "dictionary", value = "finishTimeStatus", dbValue = "mn.Finish_Time_Status", dictionary = "JPMsoft_LCBJDZT")
    private List<String> finishTimeStatus;

    @ClassInfo(title = "竣工验收时间", type = "date", value = "completeTime", dbValue = "mn.Complete_Time")
    private Date completeTime;

    @ClassInfo(title = "竣工验收时间状态", type = "dictionary", value = "completeTimeStatus", dbValue = "mn.Complete_Time_Status", dictionary = "JPMsoft_LCBJDZT")
    private List<String> completeTimeStatus;

    @ClassInfo(title = "竣工备案时间", type = "date", value = "completeRecordsTime", dbValue = "mn.Complete_Records_Time")
    private Date completeRecordsTime;

    @ClassInfo(title = "竣工备案时间状态", type = "dictionary", value = "completeRecordsTimeStatus", dbValue = "mn.Complete_Records_Time_Status", dictionary = "JPMsoft_LCBJDZT")
    private List<String> completeRecordsTimeStatus;

    @JsonProperty("pRemoveTime")
    @ClassInfo(title = "工程移交时间", type = "date", value = "pRemoveTime", dbValue = "mn.P_Remove_Time")
    private Date pRemoveTime;

    @JsonProperty("pRemoveTimeStatus")
    @ClassInfo(title = "工程移交时间状态", type = "dictionary", value = "pRemoveTimeStatus", dbValue = "mn.P_Remove_Time_Status", dictionary = "JPMsoft_LCBJDZT")
    private List<String> pRemoveTimeStatus;

    @ClassInfo(title = "决算上交时间", type = "date", value = "accountSubmitTime", dbValue = "mn.Account_Submit_Time")
    private Date accountSubmitTime;

    @ClassInfo(title = "决算上交时间状态", type = "dictionary", value = "accountSubmitTimeStatus", dbValue = "mn.Account_Submit_Time_Status", dictionary = "JPMsoft_LCBJDZT")
    private List<String> accountSubmitTimeStatus;

    @ClassInfo(title = "决算批复时间", type = "date", value = "accountReplyTime", dbValue = "mn.Account_Reply_Time")
    private Date accountReplyTime;

    @ClassInfo(title = "决算批复时间状态", type = "dictionary", value = "accountReplyTimeStatus", dbValue = "mn.Account_Reply_Time_Status", dictionary = "JPMsoft_LCBJDZT")
    private List<String> accountReplyTimeStatus;

//    @ClassInfo(title = "备注", type = "string", value = "remark", dbValue = "r.Remark")
    private List<String> remark;

    private String icmReportingYear;
    private String icmReportingMonth;

    /**
     * 逾期筛选
     */
    private List<String> delinquent;

    /**
     * 是否计划外：00-年度计划，01-计划外，02-导出
     */
    private String isPlan;
}
