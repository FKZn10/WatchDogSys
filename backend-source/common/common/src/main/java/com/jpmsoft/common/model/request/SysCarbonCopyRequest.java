package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseModeRequest;

/**
 * 流程管理 抄送记录
 */
public class SysCarbonCopyRequest extends BaseModeRequest {

    //抄送标题
    private String ccTitle;
    //项目名称
    private String pName;
    //合同名称
    private String conName;
    //发送人
    private String ccCreateName;
    //抄送创建时间
    private String ccSendStartTime;
    private String ccSendEndTime;
    //阅读状态
    private String ccRead;
    private String ccUserName;

    private String tCode;

    public String getCcUserName() {
        return ccUserName;
    }

    public void setCcUserName(String ccUserName) {
        this.ccUserName = ccUserName;
    }

    public String getCcTitle() {
        return ccTitle;
    }

    public void setCcTitle(String ccTitle) {
        this.ccTitle = ccTitle;
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

    public String getCcCreateName() {
        return ccCreateName;
    }

    public void setCcCreateName(String ccCreateName) {
        this.ccCreateName = ccCreateName;
    }

    public String getCcSendStartTime() {
        return ccSendStartTime;
    }

    public void setCcSendStartTime(String ccSendStartTime) {
        this.ccSendStartTime = ccSendStartTime;
    }

    public String getCcSendEndTime() {
        return ccSendEndTime;
    }

    public void setCcSendEndTime(String ccSendEndTime) {
        this.ccSendEndTime = ccSendEndTime;
    }

    public String getCcRead() {
        return ccRead;
    }

    public void setCcRead(String ccRead) {
        this.ccRead = ccRead;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }
}
