package com.jpmsoft.common.model.requestAndResponse.Oa;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 补卡申请
 */

public class OaAttendanceSupplementModel extends BaseFileRequest {

    private String asId;
    private String asUid;
    private String asUname;
    private Date asTime;
    private String asAbnormalState;
    private String asAbnormalTime;
    private String asRemark;
    private Date asCreateTime;
    private Integer asState;
    private Date asStateTime;


    public String getAsId() {
        return asId;
    }

    public void setAsId(String asId) {
        this.asId = asId;
    }

    public String getAsUid() {
        return asUid;
    }

    public void setAsUid(String asUid) {
        this.asUid = asUid;
    }

    public String getAsUname() {
        return asUname;
    }

    public void setAsUname(String asUname) {
        this.asUname = asUname;
    }

    public Date getAsTime() {
        return asTime;
    }

    public void setAsTime(Date asTime) {
        this.asTime = asTime;
    }

    public String getAsAbnormalState() {
        return asAbnormalState;
    }

    public void setAsAbnormalState(String asAbnormalState) {
        this.asAbnormalState = asAbnormalState;
    }

    public String getAsAbnormalTime() {
        return asAbnormalTime;
    }

    public void setAsAbnormalTime(String asAbnormalTime) {
        this.asAbnormalTime = asAbnormalTime;
    }

    public String getAsRemark() {
        return asRemark;
    }

    public void setAsRemark(String asRemark) {
        this.asRemark = asRemark;
    }

    public Date getAsCreateTime() {
        return asCreateTime;
    }

    public void setAsCreateTime(Date asCreateTime) {
        this.asCreateTime = asCreateTime;
    }

    public Integer getAsState() {
        return asState;
    }

    public void setAsState(Integer asState) {
        this.asState = asState;
    }

    public Date getAsStateTime() {
        return asStateTime;
    }

    public void setAsStateTime(Date asStateTime) {
        this.asStateTime = asStateTime;
    }
}
