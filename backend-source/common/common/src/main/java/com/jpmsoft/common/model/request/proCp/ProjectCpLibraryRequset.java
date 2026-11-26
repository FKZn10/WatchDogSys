package com.jpmsoft.common.model.request.proCp;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;
import java.util.List;

/**
 * 项目建设程序库
 */
public class ProjectCpLibraryRequset extends BaseModeRequest {

    private String cplId;
    private String cplName;
    private String cplCode;
    private String cplParentId;
    private String cplType;
    private Integer cplDays;
    /**
     * 前置建设程序
     */
    private String cplProId;
    /**
     * 行政部门
     */
    private String cplAdministration;
    /**
     * 官网链接
     */
    private String cplAdministrationUrl;
    private String cplRemark;
    /**
     * 表单
     */
    private String cplHtml;
    private String cplCreateUid;
    private String cplCreateUname;
    private Date cplCreateTime;
    private String cplState;
    private Integer cplSort;
    private String cplAdministrationUrlH;
    private String tCode;
    private String cplFileExpiain;
    private String cplAchievementType;
    private Integer cplStatus;
    private String cplFileType;

    private List<ProjectCpLibraryRiskRequest> riskList;

    public List<ProjectCpLibraryRiskRequest> getRiskList() {
        return riskList;
    }

    public void setRiskList(List<ProjectCpLibraryRiskRequest> riskList) {
        this.riskList = riskList;
    }

    public String getCplId() {
        return cplId;
    }

    public void setCplId(String cplId) {
        this.cplId = cplId;
    }

    public String getCplName() {
        return cplName;
    }

    public void setCplName(String cplName) {
        this.cplName = cplName;
    }

    public String getCplCode() {
        return cplCode;
    }

    public void setCplCode(String cplCode) {
        this.cplCode = cplCode;
    }

    public String getCplParentId() {
        return cplParentId;
    }

    public void setCplParentId(String cplParentId) {
        this.cplParentId = cplParentId;
    }

    public String getCplType() {
        return cplType;
    }

    public void setCplType(String cplType) {
        this.cplType = cplType;
    }

    public Integer getCplDays() {
        return cplDays;
    }

    public void setCplDays(Integer cplDays) {
        this.cplDays = cplDays;
    }

    public String getCplProId() {
        return cplProId;
    }

    public void setCplProId(String cplProId) {
        this.cplProId = cplProId;
    }

    public String getCplAdministration() {
        return cplAdministration;
    }

    public void setCplAdministration(String cplAdministration) {
        this.cplAdministration = cplAdministration;
    }

    public String getCplAdministrationUrl() {
        return cplAdministrationUrl;
    }

    public void setCplAdministrationUrl(String cplAdministrationUrl) {
        this.cplAdministrationUrl = cplAdministrationUrl;
    }

    public String getCplRemark() {
        return cplRemark;
    }

    public void setCplRemark(String cplRemark) {
        this.cplRemark = cplRemark;
    }

    public String getCplHtml() {
        return cplHtml;
    }

    public void setCplHtml(String cplHtml) {
        this.cplHtml = cplHtml;
    }

    public String getCplCreateUid() {
        return cplCreateUid;
    }

    public void setCplCreateUid(String cplCreateUid) {
        this.cplCreateUid = cplCreateUid;
    }

    public String getCplCreateUname() {
        return cplCreateUname;
    }

    public void setCplCreateUname(String cplCreateUname) {
        this.cplCreateUname = cplCreateUname;
    }

    public Date getCplCreateTime() {
        return cplCreateTime;
    }

    public void setCplCreateTime(Date cplCreateTime) {
        this.cplCreateTime = cplCreateTime;
    }

    public String getCplState() {
        return cplState;
    }

    public void setCplState(String cplState) {
        this.cplState = cplState;
    }

    public Integer getCplSort() {
        return cplSort;
    }

    public void setCplSort(Integer cplSort) {
        this.cplSort = cplSort;
    }

    public String getCplAdministrationUrlH() {
        return cplAdministrationUrlH;
    }

    public void setCplAdministrationUrlH(String cplAdministrationUrlH) {
        this.cplAdministrationUrlH = cplAdministrationUrlH;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getCplFileExpiain() {
        return cplFileExpiain;
    }

    public void setCplFileExpiain(String cplFileExpiain) {
        this.cplFileExpiain = cplFileExpiain;
    }

    public String getCplAchievementType() {
        return cplAchievementType;
    }

    public void setCplAchievementType(String cplAchievementType) {
        this.cplAchievementType = cplAchievementType;
    }

    public Integer getCplStatus() {
        return cplStatus;
    }

    public void setCplStatus(Integer cplStatus) {
        this.cplStatus = cplStatus;
    }

    public String getCplFileType() {
        return cplFileType;
    }

    public void setCplFileType(String cplFileType) {
        this.cplFileType = cplFileType;
    }
}
