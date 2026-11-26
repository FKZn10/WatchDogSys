package com.jpmsoft.common.model.requestAndResponse.Oa;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 项目问题详情（清单）
 */

public class OaProblemDtlModel extends ProjectCommonRes {

    private String pmdId;
    private String pmId;
    private String pmdTitle;
    private String pmdType;
    private String pmdContent;
    private String pmdSolution;
    private Integer pmdStatus;
    private Date pmdCreateTime;
    private String pmdDutyUid;
    private String pmdDutyUname;
    private String pmdCode;

    private String pmTitle;
    private String pmCode;
    private Integer pmState;
    private String secName;
    private String conName;
    private String pId;


    public String getPmdId() {
        return pmdId;
    }

    public void setPmdId(String pmdId) {
        this.pmdId = pmdId;
    }

    public String getPmId() {
        return pmId;
    }

    public void setPmId(String pmId) {
        this.pmId = pmId;
    }

    public String getPmdTitle() {
        return pmdTitle;
    }

    public void setPmdTitle(String pmdTitle) {
        this.pmdTitle = pmdTitle;
    }

    public String getPmdType() {
        return pmdType;
    }

    public void setPmdType(String pmdType) {
        this.pmdType = pmdType;
    }

    public String getPmdContent() {
        return pmdContent;
    }

    public void setPmdContent(String pmdContent) {
        this.pmdContent = pmdContent;
    }

    public String getPmdSolution() {
        return pmdSolution;
    }

    public void setPmdSolution(String pmdSolution) {
        this.pmdSolution = pmdSolution;
    }

    public Integer getPmdStatus() {
        return pmdStatus;
    }

    public void setPmdStatus(Integer pmdStatus) {
        this.pmdStatus = pmdStatus;
    }

    public Date getPmdCreateTime() {
        return pmdCreateTime;
    }

    public void setPmdCreateTime(Date pmdCreateTime) {
        this.pmdCreateTime = pmdCreateTime;
    }

    public String getPmdDutyUid() {
        return pmdDutyUid;
    }

    public void setPmdDutyUid(String pmdDutyUid) {
        this.pmdDutyUid = pmdDutyUid;
    }

    public String getPmdDutyUname() {
        return pmdDutyUname;
    }

    public void setPmdDutyUname(String pmdDutyUname) {
        this.pmdDutyUname = pmdDutyUname;
    }

    public String getPmdCode() {
        return pmdCode;
    }

    public void setPmdCode(String pmdCode) {
        this.pmdCode = pmdCode;
    }

    public String getPmTitle() {
        return pmTitle;
    }

    public void setPmTitle(String pmTitle) {
        this.pmTitle = pmTitle;
    }

    public String getPmCode() {
        return pmCode;
    }

    public void setPmCode(String pmCode) {
        this.pmCode = pmCode;
    }

    public Integer getPmState() {
        return pmState;
    }

    public void setPmState(Integer pmState) {
        this.pmState = pmState;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
