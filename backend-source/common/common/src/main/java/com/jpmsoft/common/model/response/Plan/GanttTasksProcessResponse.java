package com.jpmsoft.common.model.response.Plan;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 任务完成情况
 */

public class GanttTasksProcessResponse extends ProjectCommonRes {
    private String gtpId;
    private String taskId;
    private Date gtpDate;
    private Date gtpEndDate;
    private Float gtpRate;
    private String gtpRemark;
    private String gtpCreateUid;
    private String gtpCreateUname;
    private Date gtpCreateTime;
    private Integer gtpState;
    private Date gtpStateTime;
    private String pId;
    private String planName;
    private String gtpImageProgress;
    private String gtpScenePhotos;
    private String gtpLiveVideo;
    private String gtpSignatureConfirm;
    private String fileId;
    private String taskEndTCode;
    private GanttTasksResponse ganttTasks;
    private Integer gtpUploadTimes;

    @JsonProperty("pCategory")
    private String pCategory;

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public Date getGtpEndDate() {return gtpEndDate;}

    public void setGtpEndDate(Date gtpEndDate) {this.gtpEndDate = gtpEndDate;}

    public String getGtpId() {
        return gtpId;
    }

    public void setGtpId(String gtpId) {
        this.gtpId = gtpId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getGtpDate() {
        return gtpDate;
    }

    public void setGtpDate(Date gtpDate) {
        this.gtpDate = gtpDate;
    }

    public Float getGtpRate() {
        return gtpRate;
    }

    public void setGtpRate(Float gtpRate) {
        this.gtpRate = gtpRate;
    }

    public String getGtpRemark() {
        return gtpRemark;
    }

    public void setGtpRemark(String gtpRemark) {
        this.gtpRemark = gtpRemark;
    }

    public String getGtpCreateUid() {
        return gtpCreateUid;
    }

    public void setGtpCreateUid(String gtpCreateUid) {
        this.gtpCreateUid = gtpCreateUid;
    }

    public String getGtpCreateUname() {
        return gtpCreateUname;
    }

    public void setGtpCreateUname(String gtpCreateUname) {
        this.gtpCreateUname = gtpCreateUname;
    }

    public Date getGtpCreateTime() {
        return gtpCreateTime;
    }

    public void setGtpCreateTime(Date gtpCreateTime) {
        this.gtpCreateTime = gtpCreateTime;
    }

    public Integer getGtpState() {
        return gtpState;
    }

    public void setGtpState(Integer gtpState) {
        this.gtpState = gtpState;
    }

    public Date getGtpStateTime() {
        return gtpStateTime;
    }

    public void setGtpStateTime(Date gtpStateTime) {
        this.gtpStateTime = gtpStateTime;
    }


    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public GanttTasksResponse getGanttTasks() {
        return ganttTasks;
    }

    public void setGanttTasks(GanttTasksResponse ganttTasks) {
        this.ganttTasks = ganttTasks;
    }

    public Integer getGtpUploadTimes() {
        return gtpUploadTimes;
    }

    public void setGtpUploadTimes(Integer gtpUploadTimes) {
        this.gtpUploadTimes = gtpUploadTimes;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getGtpImageProgress() {
        return gtpImageProgress;
    }

    public void setGtpImageProgress(String gtpImageProgress) {
        this.gtpImageProgress = gtpImageProgress;
    }

    public String getGtpScenePhotos() {
        return gtpScenePhotos;
    }

    public void setGtpScenePhotos(String gtpScenePhotos) {
        this.gtpScenePhotos = gtpScenePhotos;
    }

    public String getGtpLiveVideo() {
        return gtpLiveVideo;
    }

    public void setGtpLiveVideo(String gtpLiveVideo) {
        this.gtpLiveVideo = gtpLiveVideo;
    }

    public String getGtpSignatureConfirm() {
        return gtpSignatureConfirm;
    }

    public void setGtpSignatureConfirm(String gtpSignatureConfirm) {
        this.gtpSignatureConfirm = gtpSignatureConfirm;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTaskEndTCode() {
        return taskEndTCode;
    }

    public void setTaskEndTCode(String taskEndTCode) {
        this.taskEndTCode = taskEndTCode;
    }
}
