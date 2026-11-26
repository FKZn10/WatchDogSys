package com.jpmsoft.common.model.export.response;

import java.util.Date;

/**
 * 根据项目id集合、办理时间（区间查询）、计划名称（模糊查询）
 * 获取满足条件的进度计划办理信息，
 * 返回信息包含：项目所属地区、项目所属地区统建室、项目名称、进度办理记录
 */
public class GanttTasksProcessModel {

    private String planName;
    private String taskDoc;
    private String taskText;
    private String taskUname;
    private Integer taskState;
    private Float taskProgress;
    private String pName;
    private String pEachDistrict;
    private String dName;
    private Date gtpDate;
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
    private String fileRelationId;
    private String  pConsName;
    private String  pConsAbbName;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getTaskDoc() {
        return taskDoc;
    }

    public void setTaskDoc(String taskDoc) {
        this.taskDoc = taskDoc;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getTaskUname() {
        return taskUname;
    }

    public void setTaskUname(String taskUname) {
        this.taskUname = taskUname;
    }

    public Integer getTaskState() {
        return taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }

    public Float getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(Float taskProgress) {
        this.taskProgress = taskProgress;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpEachDistrict() {
        return pEachDistrict;
    }

    public void setpEachDistrict(String pEachDistrict) {
        this.pEachDistrict = pEachDistrict;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
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

    public String getFileRelationId() {
        return fileRelationId;
    }

    public void setFileRelationId(String fileRelationId) {
        this.fileRelationId = fileRelationId;
    }

    public String getpConsName() {
        return pConsName;
    }

    public void setpConsName(String pConsName) {
        this.pConsName = pConsName;
    }

    public String getpConsAbbName() {
        return pConsAbbName;
    }

    public void setpConsAbbName(String pConsAbbName) {
        this.pConsAbbName = pConsAbbName;
    }
}
