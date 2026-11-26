package com.jpmsoft.common.model.requestAndResponse.SecInfo;


import com.jpmsoft.common.model.BaseModeRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * 招标公告
 */
public class SecProjectModel extends BaseModeRequest {

    private String pId;
    private String pName;
    private String pCode;
    private Integer secTotal;
    private BigDecimal secMoneyTotal;
    private String pCreateUname;
    private String pCreateUid;
    private String pCompetentDepartmentName;
    private Integer state;
    private String secCreateUname;
    private String pBuildModel;
    private String pUseUnit;
    private String pImpCompany;
    private String pType;
    private List<String> projectNameList;
    private List<String> pBuildModelList;
    private List<String> pTypeList;
    private List<String> pUseUnitList;
    private List<String> pImpCompanyList;
    private List<String> pCompetentDepartmentNameList;
    //招采计划表id
    private String ppId;
    private List<String> secTotals;
    private List<String> secMoneyTotals;

    private String isNew;

    private String pUseUnitName;

    public String getpUseUnitName() {
        return pUseUnitName;
    }

    public void setpUseUnitName(String pUseUnitName) {
        this.pUseUnitName = pUseUnitName;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
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

    public Integer getSecTotal() {
        return secTotal;
    }

    public void setSecTotal(Integer secTotal) {
        this.secTotal = secTotal;
    }

    public BigDecimal getSecMoneyTotal() {
        return secMoneyTotal;
    }

    public void setSecMoneyTotal(BigDecimal secMoneyTotal) {
        this.secMoneyTotal = secMoneyTotal;
    }

    public String getpCreateUname() {
        return pCreateUname;
    }

    public void setpCreateUname(String pCreateUname) {
        this.pCreateUname = pCreateUname;
    }

    public String getpCreateUid() {
        return pCreateUid;
    }

    public void setpCreateUid(String pCreateUid) {
        this.pCreateUid = pCreateUid;
    }

    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getSecCreateUname() {
        return secCreateUname;
    }

    public void setSecCreateUname(String secCreateUname) {
        this.secCreateUname = secCreateUname;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    public String getpUseUnit() {
        return pUseUnit;
    }

    public void setpUseUnit(String pUseUnit) {
        this.pUseUnit = pUseUnit;
    }

    public String getpImpCompany() {
        return pImpCompany;
    }

    public void setpImpCompany(String pImpCompany) {
        this.pImpCompany = pImpCompany;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public List<String> getProjectNameList() {
        return projectNameList;
    }

    public void setProjectNameList(List<String> projectNameList) {
        this.projectNameList = projectNameList;
    }

    public List<String> getpBuildModelList() {
        return pBuildModelList;
    }

    public void setpBuildModelList(List<String> pBuildModelList) {
        this.pBuildModelList = pBuildModelList;
    }

    public List<String> getpTypeList() {
        return pTypeList;
    }

    public void setpTypeList(List<String> pTypeList) {
        this.pTypeList = pTypeList;
    }

    public List<String> getpUseUnitList() {
        return pUseUnitList;
    }

    public void setpUseUnitList(List<String> pUseUnitList) {
        this.pUseUnitList = pUseUnitList;
    }

    public List<String> getpImpCompanyList() {
        return pImpCompanyList;
    }

    public void setpImpCompanyList(List<String> pImpCompanyList) {
        this.pImpCompanyList = pImpCompanyList;
    }

    public List<String> getpCompetentDepartmentNameList() {
        return pCompetentDepartmentNameList;
    }

    public void setpCompetentDepartmentNameList(List<String> pCompetentDepartmentNameList) {
        this.pCompetentDepartmentNameList = pCompetentDepartmentNameList;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public List<String> getSecTotals() {
        return secTotals;
    }

    public void setSecTotals(List<String> secTotals) {
        this.secTotals = secTotals;
    }

    public List<String> getSecMoneyTotals() {
        return secMoneyTotals;
    }

    public void setSecMoneyTotals(List<String> secMoneyTotals) {
        this.secMoneyTotals = secMoneyTotals;
    }
}
