package com.jpmsoft.common.model.requestAndResponse.Design;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 竣工图设计
 */
public class DesignCompletedModel extends ProjectCommonRes {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String dcId;
    private String pId;
    private String secId;
    private String dcCategory;
    private String dcContent;
    private Date dcEditTime;
    private String dcEditor;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String dcCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String dcCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date dcCreateTime;
    private String dcConstruUnit;
    private String dcConstruPm;
    private String dcSuperviUnit;
    private String dcSuperviPm;
    private String dcDesignUnit;
    private String dcDesignPm;

    private String dcConstruName;
    private String dcSuperviName;
    private String dcDesignName;
    private String secName;
    //单位简称
    private String dcConstruAbbName;
    private String dcSuperviAbbName;
    private String dcDesignAbbName;

    public String getDcId() {
        return dcId;
    }

    public void setDcId(String dcId) {
        this.dcId = dcId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getDcCategory() {
        return dcCategory;
    }

    public void setDcCategory(String dcCategory) {
        this.dcCategory = dcCategory;
    }

    public String getDcContent() {
        return dcContent;
    }

    public void setDcContent(String dcContent) {
        this.dcContent = dcContent;
    }

    public Date getDcEditTime() {
        return dcEditTime;
    }

    public void setDcEditTime(Date dcEditTime) {
        this.dcEditTime = dcEditTime;
    }

    public String getDcEditor() {
        return dcEditor;
    }

    public void setDcEditor(String dcEditor) {
        this.dcEditor = dcEditor;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public String getDcCreateUname() {
        return dcCreateUname;
    }

    public void setDcCreateUname(String dcCreateUname) {
        this.dcCreateUname = dcCreateUname;
    }

    public String getDcCreateUid() {
        return dcCreateUid;
    }

    public void setDcCreateUid(String dcCreateUid) {
        this.dcCreateUid = dcCreateUid;
    }

    public Date getDcCreateTime() {
        return dcCreateTime;
    }

    public void setDcCreateTime(Date dcCreateTime) {
        this.dcCreateTime = dcCreateTime;
    }

    public String getDcConstruUnit() {
        return dcConstruUnit;
    }

    public void setDcConstruUnit(String dcConstruUnit) {
        this.dcConstruUnit = dcConstruUnit;
    }

    public String getDcConstruPm() {
        return dcConstruPm;
    }

    public void setDcConstruPm(String dcConstruPm) {
        this.dcConstruPm = dcConstruPm;
    }

    public String getDcSuperviUnit() {
        return dcSuperviUnit;
    }

    public void setDcSuperviUnit(String dcSuperviUnit) {
        this.dcSuperviUnit = dcSuperviUnit;
    }

    public String getDcSuperviPm() {
        return dcSuperviPm;
    }

    public void setDcSuperviPm(String dcSuperviPm) {
        this.dcSuperviPm = dcSuperviPm;
    }

    public String getDcDesignUnit() {
        return dcDesignUnit;
    }

    public void setDcDesignUnit(String dcDesignUnit) {
        this.dcDesignUnit = dcDesignUnit;
    }

    public String getDcDesignPm() {
        return dcDesignPm;
    }

    public void setDcDesignPm(String dcDesignPm) {
        this.dcDesignPm = dcDesignPm;
    }

    public String getDcConstruName() {
        return dcConstruName;
    }

    public void setDcConstruName(String dcConstruName) {
        this.dcConstruName = dcConstruName;
    }

    public String getDcSuperviName() {
        return dcSuperviName;
    }

    public void setDcSuperviName(String dcSuperviName) {
        this.dcSuperviName = dcSuperviName;
    }

    public String getDcDesignName() {
        return dcDesignName;
    }

    public void setDcDesignName(String dcDesignName) {
        this.dcDesignName = dcDesignName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getDcConstruAbbName() {
        return dcConstruAbbName;
    }

    public void setDcConstruAbbName(String dcConstruAbbName) {
        this.dcConstruAbbName = dcConstruAbbName;
    }

    public String getDcSuperviAbbName() {
        return dcSuperviAbbName;
    }

    public void setDcSuperviAbbName(String dcSuperviAbbName) {
        this.dcSuperviAbbName = dcSuperviAbbName;
    }

    public String getDcDesignAbbName() {
        return dcDesignAbbName;
    }

    public void setDcDesignAbbName(String dcDesignAbbName) {
        this.dcDesignAbbName = dcDesignAbbName;
    }
}
