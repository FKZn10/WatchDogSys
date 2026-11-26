package com.jpmsoft.common.model.requestAndResponse.Oa;


import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;
import java.util.List;

/**
 * 考勤组
 */
public class OaAttendanceGroupModel extends ProjectCommonRes {

    private String agId;
    private String agName;
    private String pId;
    private Integer agScope;
    private String agLongitude;
    private String agLatitude;
    private String agRemark;
    private String agCreateUid;
    private String agCreateUname;
    private Date agCreateTime;
    private Integer agState;

    private Integer memberCount;
    //建设单位主管部门
    private String pConsName;
    private String pConsAbbName;
    private String pCompDepName;
    private List<OaAttendanceMemberModel> attendanceMembers;
    private List<OaAttendanceWeekdayModel> attendanceWeekdays;


    public String getAgId() {
        return agId;
    }

    public void setAgId(String agId) {
        this.agId = agId;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Integer getAgScope() {
        return agScope;
    }

    public void setAgScope(Integer agScope) {
        this.agScope = agScope;
    }

    public String getAgLongitude() {
        return agLongitude;
    }

    public void setAgLongitude(String agLongitude) {
        this.agLongitude = agLongitude;
    }

    public String getAgLatitude() {
        return agLatitude;
    }

    public void setAgLatitude(String agLatitude) {
        this.agLatitude = agLatitude;
    }

    public String getAgRemark() {
        return agRemark;
    }

    public void setAgRemark(String agRemark) {
        this.agRemark = agRemark;
    }

    public String getAgCreateUid() {
        return agCreateUid;
    }

    public void setAgCreateUid(String agCreateUid) {
        this.agCreateUid = agCreateUid;
    }

    public String getAgCreateUname() {
        return agCreateUname;
    }

    public void setAgCreateUname(String agCreateUname) {
        this.agCreateUname = agCreateUname;
    }

    public Date getAgCreateTime() {
        return agCreateTime;
    }

    public void setAgCreateTime(Date agCreateTime) {
        this.agCreateTime = agCreateTime;
    }

    public Integer getAgState() {
        return agState;
    }

    public void setAgState(Integer agState) {
        this.agState = agState;
    }

    public List<OaAttendanceMemberModel> getAttendanceMembers() {
        return attendanceMembers;
    }

    public void setAttendanceMembers(List<OaAttendanceMemberModel> attendanceMembers) {
        this.attendanceMembers = attendanceMembers;
    }

    public List<OaAttendanceWeekdayModel> getAttendanceWeekdays() {
        return attendanceWeekdays;
    }

    public void setAttendanceWeekdays(List<OaAttendanceWeekdayModel> attendanceWeekdays) {
        this.attendanceWeekdays = attendanceWeekdays;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
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

    public String getpCompDepName() {
        return pCompDepName;
    }

    public void setpCompDepName(String pCompDepName) {
        this.pCompDepName = pCompDepName;
    }
}
