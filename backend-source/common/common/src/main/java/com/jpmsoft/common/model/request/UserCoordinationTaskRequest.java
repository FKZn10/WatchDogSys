package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseModeRequest;

/**
 * 我的事务(PM) 待反馈流程
 */
public class UserCoordinationTaskRequest extends BaseModeRequest {


    private String ctName;
    private String pName;
    private String conName;
    private String ctSendUser;
    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName;
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

    public String getCtSendUser() {
        return ctSendUser;
    }

    public void setCtSendUser(String ctSendUser) {
        this.ctSendUser = ctSendUser;
    }
}
