package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

public class SecExpertCheckModel extends BaseFileRequest {

    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;

    private String exId;
    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;
    private String secId;

    private String secName;

    // 专家选取记录表id  评审类型  考核人  就是这个表的创建人  考核时间  创建时间
    private String srId;

    // 评审类型
    private String seMode;
    // 考核人
    private String examineUid;

    private String examineUname;
    //评标日期
    private Date ecAssessTime;
    //考核评分日期
    private Date ecTime;
    private String teId;


    private String expertCheckFile;

    private String expertCheckFileIds;

    private String seRemark;

    private String clientReviewerFullScoreExplanation;



    List<SecSelectionRecordDtlModel> secSelectionRecordDtls;

    public String getClientReviewerFullScoreExplanation() {
        return clientReviewerFullScoreExplanation;
    }

    public void setClientReviewerFullScoreExplanation(String clientReviewerFullScoreExplanation) {
        this.clientReviewerFullScoreExplanation = clientReviewerFullScoreExplanation;
    }

    public String getExpertCheckFile() {
        return expertCheckFile;
    }

    public void setExpertCheckFile(String expertCheckFile) {
        this.expertCheckFile = expertCheckFile;
    }

    public String getExpertCheckFileIds() {
        return expertCheckFileIds;
    }

    public void setExpertCheckFileIds(String expertCheckFileIds) {
        this.expertCheckFileIds = expertCheckFileIds;
    }

    public String getSeRemark() {
        return seRemark;
    }

    public void setSeRemark(String seRemark) {
        this.seRemark = seRemark;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public String getCreateUname() {
        return createUname;
    }

    public void setCreateUname(String createUname) {
        this.createUname = createUname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExId() {
        return exId;
    }

    public void setExId(String exId) {
        this.exId = exId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    @Override
    public String getSecId() {
        return secId;
    }

    @Override
    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSrId() {
        return srId;
    }

    public void setSrId(String srId) {
        this.srId = srId;
    }

    public String getSeMode() {
        return seMode;
    }

    public void setSeMode(String seMode) {
        this.seMode = seMode;
    }

    public String getExamineUid() {
        return examineUid;
    }

    public void setExamineUid(String examineUid) {
        this.examineUid = examineUid;
    }

    public String getExamineUname() {
        return examineUname;
    }

    public void setExamineUname(String examineUname) {
        this.examineUname = examineUname;
    }

    public Date getEcAssessTime() {
        return ecAssessTime;
    }

    public void setEcAssessTime(Date ecAssessTime) {
        this.ecAssessTime = ecAssessTime;
    }

    public Date getEcTime() {
        return ecTime;
    }

    public void setEcTime(Date ecTime) {
        this.ecTime = ecTime;
    }

    @Override
    public String getTeId() {
        return teId;
    }

    @Override
    public void setTeId(String teId) {
        this.teId = teId;
    }

    public List<SecSelectionRecordDtlModel> getSecSelectionRecordDtls() {
        return secSelectionRecordDtls;
    }

    public void setSecSelectionRecordDtls(List<SecSelectionRecordDtlModel> secSelectionRecordDtls) {
        this.secSelectionRecordDtls = secSelectionRecordDtls;
    }
}
