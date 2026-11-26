package com.jpmsoft.common.model.requestAndResponse.Oa;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

/**
 * 考勤地点
 */
public class OaAttendanceLocationModel extends BaseModeRequest {

    private String alId;
    private String agId;
    private String alName;
    private Integer alScope;
    private String alLongitude;
    private String alLatitude;
    private String alRemark;
    private String alCreateUid;
    private String alCreateUname;
    private Date alCreateTime;
    private Integer alState;
    private String agName;

    public String getAlId() {
        return alId;
    }

    public void setAlId(String alId) {
        this.alId = alId;
    }

    public String getAgId() {
        return agId;
    }

    public void setAgId(String agId) {
        this.agId = agId;
    }

    public String getAlName() {
        return alName;
    }

    public void setAlName(String alName) {
        this.alName = alName;
    }

    public Integer getAlScope() {
        return alScope;
    }

    public void setAlScope(Integer alScope) {
        this.alScope = alScope;
    }

    public String getAlLongitude() {
        return alLongitude;
    }

    public void setAlLongitude(String alLongitude) {
        this.alLongitude = alLongitude;
    }

    public String getAlLatitude() {
        return alLatitude;
    }

    public void setAlLatitude(String alLatitude) {
        this.alLatitude = alLatitude;
    }

    public String getAlRemark() {
        return alRemark;
    }

    public void setAlRemark(String alRemark) {
        this.alRemark = alRemark;
    }

    public String getAlCreateUid() {
        return alCreateUid;
    }

    public void setAlCreateUid(String alCreateUid) {
        this.alCreateUid = alCreateUid;
    }

    public String getAlCreateUname() {
        return alCreateUname;
    }

    public void setAlCreateUname(String alCreateUname) {
        this.alCreateUname = alCreateUname;
    }

    public Date getAlCreateTime() {
        return alCreateTime;
    }

    public void setAlCreateTime(Date alCreateTime) {
        this.alCreateTime = alCreateTime;
    }

    public Integer getAlState() {
        return alState;
    }

    public void setAlState(Integer alState) {
        this.alState = alState;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }
}
