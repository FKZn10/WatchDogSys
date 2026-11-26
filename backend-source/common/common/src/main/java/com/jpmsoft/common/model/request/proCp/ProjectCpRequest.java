package com.jpmsoft.common.model.request.proCp;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

/**
 * 项目建设程序
 */
public class ProjectCpRequest extends BaseFileRequest {

    private String cpId;
    private String pId;
    private Date cpCreateTime;
    private String cpCreateUid;
    private String cpCreateUname;
    private String cpRemark;
    private String cpltId;
    private String pName;
    private String pCode;
    private String uName;

    private String pType;
    private String pProjectStage;
    private String pConstructionUnit;
    private String pCompetentDepartment;
    private String pRegion;
    private Integer state;
    //项目审批状态
    private Integer proState;
    private Date stateTime;

    private String pBuildModel;
    private String pImpCompany;
    private String pUseUnit;
    private List<ProjectCpLibraryRequset> proCpls;

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Date getCpCreateTime() {
        return cpCreateTime;
    }

    public void setCpCreateTime(Date cpCreateTime) {
        this.cpCreateTime = cpCreateTime;
    }

    public String getCpCreateUid() {
        return cpCreateUid;
    }

    public void setCpCreateUid(String cpCreateUid) {
        this.cpCreateUid = cpCreateUid;
    }

    public String getCpCreateUname() {
        return cpCreateUname;
    }

    public void setCpCreateUname(String cpCreateUname) {
        this.cpCreateUname = cpCreateUname;
    }

    public String getCpRemark() {
        return cpRemark;
    }

    public void setCpRemark(String cpRemark) {
        this.cpRemark = cpRemark;
    }

    public String getCpltId() {
        return cpltId;
    }

    public void setCpltId(String cpltId) {
        this.cpltId = cpltId;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpProjectStage() {
        return pProjectStage;
    }

    public void setpProjectStage(String pProjectStage) {
        this.pProjectStage = pProjectStage;
    }

    public String getpConstructionUnit() {
        return pConstructionUnit;
    }

    public void setpConstructionUnit(String pConstructionUnit) {
        this.pConstructionUnit = pConstructionUnit;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }

    public String getpRegion() {
        return pRegion;
    }

    public void setpRegion(String pRegion) {
        this.pRegion = pRegion;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getProState() {
        return proState;
    }

    public void setProState(Integer proState) {
        this.proState = proState;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    public String getpImpCompany() {
        return pImpCompany;
    }

    public void setpImpCompany(String pImpCompany) {
        this.pImpCompany = pImpCompany;
    }

    public String getpUseUnit() {
        return pUseUnit;
    }

    public void setpUseUnit(String pUseUnit) {
        this.pUseUnit = pUseUnit;
    }

    public List<ProjectCpLibraryRequset> getProCpls() {
        return proCpls;
    }

    public void setProCpls(List<ProjectCpLibraryRequset> proCpls) {
        this.proCpls = proCpls;
    }
}
