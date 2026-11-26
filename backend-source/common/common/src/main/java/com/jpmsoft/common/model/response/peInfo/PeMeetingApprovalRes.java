package com.jpmsoft.common.model.response.peInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 考核评分上会呈批
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 10:05
 */
public class PeMeetingApprovalRes extends BaseFileRequest implements Serializable {


    private static final long serialVersionUID = -3952279981977315582L;

    private String maId;
    private String maTitle;
    private String maAssessmentType;
    private String maYear;
    private String maQuarter;
    private String maMinutesMeeting;
    private String maRemarks;
    private String maCreateUname;
    private String maCreateUid;
    private Date maCreateTime;
    private Integer state;
    private Date stateTime;

    private List<PeInfoModel> peInfoModels;

    public String getMaId() {
        return maId;
    }

    public void setMaId(String maId) {
        this.maId = maId;
    }

    public String getMaTitle() {
        return maTitle;
    }

    public void setMaTitle(String maTitle) {
        this.maTitle = maTitle;
    }

    public String getMaAssessmentType() {
        return maAssessmentType;
    }

    public void setMaAssessmentType(String maAssessmentType) {
        this.maAssessmentType = maAssessmentType;
    }

    public String getMaYear() {
        return maYear;
    }

    public void setMaYear(String maYear) {
        this.maYear = maYear;
    }

    public String getMaQuarter() {
        return maQuarter;
    }

    public void setMaQuarter(String maQuarter) {
        this.maQuarter = maQuarter;
    }

    public String getMaMinutesMeeting() {
        return maMinutesMeeting;
    }

    public void setMaMinutesMeeting(String maMinutesMeeting) {
        this.maMinutesMeeting = maMinutesMeeting;
    }

    public String getMaRemarks() {
        return maRemarks;
    }

    public void setMaRemarks(String maRemarks) {
        this.maRemarks = maRemarks;
    }

    public String getMaCreateUname() {
        return maCreateUname;
    }

    public void setMaCreateUname(String maCreateUname) {
        this.maCreateUname = maCreateUname;
    }

    public String getMaCreateUid() {
        return maCreateUid;
    }

    public void setMaCreateUid(String maCreateUid) {
        this.maCreateUid = maCreateUid;
    }

    public Date getMaCreateTime() {
        return maCreateTime;
    }

    public void setMaCreateTime(Date maCreateTime) {
        this.maCreateTime = maCreateTime;
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

    public List<PeInfoModel> getPeInfoModels() {
        return peInfoModels;
    }

    public void setPeInfoModels(List<PeInfoModel> peInfoModels) {
        this.peInfoModels = peInfoModels;
    }
}
