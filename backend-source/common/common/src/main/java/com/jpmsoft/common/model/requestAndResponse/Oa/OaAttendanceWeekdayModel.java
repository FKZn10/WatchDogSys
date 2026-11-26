package com.jpmsoft.common.model.requestAndResponse.Oa;

import com.jpmsoft.common.model.BaseModeRequest;

/**
 * 工作日设置
 */
public class OaAttendanceWeekdayModel extends BaseModeRequest {

    private String awId;
    private String agId;
    private String awWeek;
    private String awStartAm;
    private String awEndAm;
    private String awStartPm;
    private String awEndPm;
    private Float awHours;
    private Integer awOrder;
    private String awRemark;
    private String agName;

    public String getAwId() {
        return awId;
    }

    public void setAwId(String awId) {
        this.awId = awId;
    }

    public String getAgId() {
        return agId;
    }

    public void setAgId(String agId) {
        this.agId = agId;
    }

    public String getAwWeek() {
        return awWeek;
    }

    public void setAwWeek(String awWeek) {
        this.awWeek = awWeek;
    }

    public String getAwStartAm() {
        return awStartAm;
    }

    public void setAwStartAm(String awStartAm) {
        this.awStartAm = awStartAm;
    }

    public String getAwEndAm() {
        return awEndAm;
    }

    public void setAwEndAm(String awEndAm) {
        this.awEndAm = awEndAm;
    }

    public String getAwStartPm() {
        return awStartPm;
    }

    public void setAwStartPm(String awStartPm) {
        this.awStartPm = awStartPm;
    }

    public String getAwEndPm() {
        return awEndPm;
    }

    public void setAwEndPm(String awEndPm) {
        this.awEndPm = awEndPm;
    }

    public Float getAwHours() {
        return awHours;
    }

    public void setAwHours(Float awHours) {
        this.awHours = awHours;
    }

    public Integer getAwOrder() {
        return awOrder;
    }

    public void setAwOrder(Integer awOrder) {
        this.awOrder = awOrder;
    }

    public String getAwRemark() {
        return awRemark;
    }

    public void setAwRemark(String awRemark) {
        this.awRemark = awRemark;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }
}
