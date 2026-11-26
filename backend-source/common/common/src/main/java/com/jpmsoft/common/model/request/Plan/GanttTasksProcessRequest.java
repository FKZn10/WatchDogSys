package com.jpmsoft.common.model.request.Plan;


import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 任务完成情况
 */
public class GanttTasksProcessRequest extends BaseFileRequest {

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
    private String gtpImageProgress;
    private String gtpScenePhotos;
    private String gtpLiveVideo;
    private String gtpSignatureConfirm;
    private String fileId;
    private int gtpUploadTimes;

    /****************/
    private String conId;
    private String planDId;
    private String gtpScenePhotosIds;
    private String gtpLiveVideoIds;
    private String gtpSignatureConfirmIds;

    public int getGtpUploadTimes() {return gtpUploadTimes;}

    public void setGtpUploadTimes(int gtpUploadTimes) {this.gtpUploadTimes = gtpUploadTimes;}

    public Date getGtpEndDate() {return gtpEndDate;}

    public void setGtpEndDate(Date gtpEndDate) {this.gtpEndDate = gtpEndDate;}

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

    public String getGtpScenePhotosIds() {
        return gtpScenePhotosIds;
    }

    public void setGtpScenePhotosIds(String gtpScenePhotosIds) {
        this.gtpScenePhotosIds = gtpScenePhotosIds;
    }

    public String getGtpLiveVideoIds() {
        return gtpLiveVideoIds;
    }

    public void setGtpLiveVideoIds(String gtpLiveVideoIds) {
        this.gtpLiveVideoIds = gtpLiveVideoIds;
    }

    public String getGtpSignatureConfirmIds() {
        return gtpSignatureConfirmIds;
    }

    public void setGtpSignatureConfirmIds(String gtpSignatureConfirmIds) {
        this.gtpSignatureConfirmIds = gtpSignatureConfirmIds;
    }
}
