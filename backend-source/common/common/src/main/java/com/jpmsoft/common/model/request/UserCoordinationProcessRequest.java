package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseModeRequest;


/**
 * 我的事务(PM) 反馈记录
 */
public class UserCoordinationProcessRequest extends BaseModeRequest {

    private String cpName;
    private String pName;
    private String cpActName;
    private String cpFileTitle;
    private String cpSendUser;
    private String cpUser;

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getCpActName() {
        return cpActName;
    }

    public void setCpActName(String cpActName) {
        this.cpActName = cpActName;
    }

    public String getCpFileTitle() {
        return cpFileTitle;
    }

    public void setCpFileTitle(String cpFileTitle) {
        this.cpFileTitle = cpFileTitle;
    }

    public String getCpSendUser() {
        return cpSendUser;
    }

    public void setCpSendUser(String cpSendUser) {
        this.cpSendUser = cpSendUser;
    }

    public String getCpUser() {
        return cpUser;
    }

    public void setCpUser(String cpUser) {
        this.cpUser = cpUser;
    }
}
