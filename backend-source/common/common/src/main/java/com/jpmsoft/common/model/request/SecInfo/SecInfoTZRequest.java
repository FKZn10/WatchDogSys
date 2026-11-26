package com.jpmsoft.common.model.request.SecInfo;

import com.jpmsoft.common.annotations.ClassInfo;

import java.util.List;

public class SecInfoTZRequest {

    private List<String> projectIds;
    @ClassInfo(title = "项目名称",type="string",value = "projectNameList",dbValue = "pName",no = 1)
    private List<String> projectNameList;
    @ClassInfo(title = "建设模式",type="dictionary",value = "pBuildModelList",dictionary = "JPMsoft_JSMS",dbValue = "pBuildModel",no = 2)
    private List<String> pBuildModelList;
    @ClassInfo(title = "专业类别",type="dictionary",value = "pTypeList",dictionary = "JPMsoft_XMLB",dbValue = "pType",no = 3)
    private List<String> pTypeList;
    @ClassInfo(title = "业主单位（主管单位/建设单位/使用单位）",type="string",value = "pUseUnitList",dbValue = "pUseUnit",no = 4)
    private List<String> pUseUnitList;
    @ClassInfo(title = "代建单位/实施单位",type="string",value = "pImpCompanyList",dbValue = "pImpCompany",no = 5)
    private List<String> pImpCompanyList;
    @ClassInfo(title = "全过程咨询/项目管理单位",type="string",value = "pCompetentDepartmentNameList",dbValue = "pCompetentDepartmentName",no = 6)
    private List<String> pCompetentDepartmentNameList;

    public List<String> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<String> projectIds) {
        this.projectIds = projectIds;
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
}
