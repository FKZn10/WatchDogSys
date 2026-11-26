package com.jpmsoft.common.model.request;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.List;

/**
 * 我的事务(PM) 抄送流程
 */
public class UserCarbonCopyRequest extends BaseModeRequest {

    //抄送标题
    private String ccTitle;
    private List<String> ccTitles;
    //项目名称
    private String pName;
    private List<String> pNames;
    //合同名称
    private String conName;
    private List<String> conNames;
    //发送人
    private String ccCreateName;
    //抄送创建时间
    private String ccSendStartTime;
    private String ccSendEndTime;
    private String ccReadStartTime;
    private String ccReadEndTime;
    //阅读状态
    private String ccRead;
    public  String pmType;
    private List<String>  filecreateusernames;
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

    public String getPmType() {
        return pmType;
    }

    public void setPmType(String pmType) {
        this.pmType = pmType;
    }

    public List<String> getCcTitles() {
        return ccTitles;
    }

    public void setCcTitles(List<String> ccTitles) {
        this.ccTitles = ccTitles;
    }

    public List<String> getpNames() {
        return pNames;
    }

    public void setpNames(List<String> pNames) {
        this.pNames = pNames;
    }

    public List<String> getConNames() {
        return conNames;
    }

    public void setConNames(List<String> conNames) {
        this.conNames = conNames;
    }

    public String getCcReadStartTime() {
        return ccReadStartTime;
    }

    public void setCcReadStartTime(String ccReadStartTime) {
        this.ccReadStartTime = ccReadStartTime;
    }

    public String getCcReadEndTime() {
        return ccReadEndTime;
    }

    public void setCcReadEndTime(String ccReadEndTime) {
        this.ccReadEndTime = ccReadEndTime;
    }

    public List<String> getFilecreateusernames() {
        return filecreateusernames;
    }

    public void setFilecreateusernames(List<String> filecreateusernames) {
        this.filecreateusernames = filecreateusernames;
    }
}
