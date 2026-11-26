package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;
import java.util.List;

/**
 * 我的事务(PM) 已办流程
 */
public class UserTaskProcessinRequest extends BaseModeRequest {

    private String tpName;
    private String pName;
    private String conName;
    private String tpFileTitle;
    private String tpSendName;
    private String tpStarttime;
    private String tpEndtime;
    private String tCode;
    private String minType;
    private List<String> minDids;
    private List<String> minUids;
    private Date timeStartMin;
    private String year;
    public String getTpSendName() {
        return tpSendName;
    }

    public void setTpSendName(String tpSendName) {
        this.tpSendName = tpSendName;
    }

    public String getTpStarttime() {
        return tpStarttime;
    }

    public void setTpStarttime(String tpStarttime) {
        this.tpStarttime = tpStarttime;
    }

    public String getTpEndtime() {
        return tpEndtime;
    }

    public void setTpEndtime(String tpEndtime) {
        this.tpEndtime = tpEndtime;
    }

    public String getTpFileTitle() {
        return tpFileTitle;
    }

    public void setTpFileTitle(String tpFileTitle) {
        this.tpFileTitle = tpFileTitle;
    }

    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getMinType() {
        return minType;
    }

    public void setMinType(String minType) {
        this.minType = minType;
    }

    public List<String> getMinDids() {
        return minDids;
    }

    public void setMinDids(List<String> minDids) {
        this.minDids = minDids;
    }

    public List<String> getMinUids() {
        return minUids;
    }

    public void setMinUids(List<String> minUids) {
        this.minUids = minUids;
    }

    public Date getTimeStartMin() {
        return timeStartMin;
    }

    public void setTimeStartMin(Date timeStartMin) {
        this.timeStartMin = timeStartMin;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
