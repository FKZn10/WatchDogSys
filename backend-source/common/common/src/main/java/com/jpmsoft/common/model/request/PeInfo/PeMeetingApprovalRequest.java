package com.jpmsoft.common.model.request.PeInfo;

import com.jpmsoft.common.model.BaseFileRequest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 考核评分上会呈批
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 10:05
 */
public class PeMeetingApprovalRequest extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -2322857991533546805L;

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

    private String maMinutesMeetingIds;
    private List<String> peIds;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMaMinutesMeetingIds() {
        return maMinutesMeetingIds;
    }

    public void setMaMinutesMeetingIds(String maMinutesMeetingIds) {
        this.maMinutesMeetingIds = maMinutesMeetingIds;
    }

    public List<String> getPeIds() {
        return peIds;
    }

    public void setPeIds(List<String> peIds) {
        this.peIds = peIds;
    }
}
