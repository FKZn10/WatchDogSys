package com.jpmsoft.common.model.export.request;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.List;

/**
 * 进度计划导出
 */
public class GanttPlanExportRequest extends BaseModeRequest {

    private List<String>  pIds;
    private String planName;
    private List<String> gtpDate;
    private String planType;
    private List<String> planIds;


    public List<String> getpIds() {
        return pIds;
    }

    public void setpIds(List<String> pIds) {
        this.pIds = pIds;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public List<String> getGtpDate() {
        return gtpDate;
    }

    public void setGtpDate(List<String> gtpDate) {
        this.gtpDate = gtpDate;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public List<String> getPlanIds() {
        return planIds;
    }

    public void setPlanIds(List<String> planIds) {
        this.planIds = planIds;
    }
}
