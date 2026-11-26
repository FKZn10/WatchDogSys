package com.jpmsoft.common.model.response.SecInfo;

public class SecInfoTZResponse extends SecInfoResponse{

    //项目id
    private String pId;
    //项目编码
    private String pCode;
    //项目名称
    private String pName;
    //建设模式
    private String pBuildModel;
    //专业类别
    private String pType;
    //业主单位（主管单位/建设单位/使用单位）
    private String pUseUnit;
    //代建单位/实施单位
    private String pImpCompany;
    //全过程咨询/项目管理单位
    private String pCompetentDepartment;

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    @Override
    public String getpName() {
        return pName;
    }

    @Override
    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
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

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }
}
