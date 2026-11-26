package com.jpmsoft.common.model.response;

import com.jpmsoft.common.model.BaseModeRequest;

/**
 * 用户建设程序办理统计
 */
public class UserProjectCpResponse extends BaseModeRequest {

    private String uId;
    private String uName;
    private Integer userCpCount;
    private Integer cpDtlCount;
    private Integer completeCount;
    private Integer unfinishedCount;
    private Integer overTimeCount;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public Integer getUserCpCount() {
        return userCpCount;
    }

    public void setUserCpCount(Integer userCpCount) {
        this.userCpCount = userCpCount;
    }

    public Integer getCpDtlCount() {
        return cpDtlCount;
    }

    public void setCpDtlCount(Integer cpDtlCount) {
        this.cpDtlCount = cpDtlCount;
    }

    public Integer getCompleteCount() {
        return completeCount;
    }

    public void setCompleteCount(Integer completeCount) {
        this.completeCount = completeCount;
    }

    public Integer getUnfinishedCount() {
        return unfinishedCount;
    }

    public void setUnfinishedCount(Integer unfinishedCount) {
        this.unfinishedCount = unfinishedCount;
    }

    public Integer getOverTimeCount() {
        return overTimeCount;
    }

    public void setOverTimeCount(Integer overTimeCount) {
        this.overTimeCount = overTimeCount;
    }
}
