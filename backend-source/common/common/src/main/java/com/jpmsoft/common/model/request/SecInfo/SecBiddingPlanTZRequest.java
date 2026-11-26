package com.jpmsoft.common.model.request.SecInfo;

import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SecBiddingPlanTZRequest extends BaseFileRequest implements Serializable {
    private String bpId;
    private String bpName;
    private Integer bpYear;
    private Integer bpMonth;
    private String bpRemark;
    private String bpCreateUid;
    private String bpCreateUname;
    private Date bpCreateTime;
    private Date stateTime;
    private Integer state;
    private String bpTenderee;
    private String bpApprovalTitle;
    private String bpApprovalNum;
    private Date bpApprovalTime;
    private String bpApprovalFile;

    private Date bpSecTime;
    private String bpCid;
    private String bpDid;
    private String bpCnature;
    private String bpDnature;
    private Date bpTime;
    private String bpSecContent;
    private BigDecimal bpGsAmt;
    private String bpBiddingType;
    private Date bpEndTime;
    private String bpSecName;
    private Boolean bpGczb;
    private String bpProScale;
    private BigDecimal bpProAmt;
    private String bpFundSource;
    private String bpScopeOverview;
    private String bpSm;
    private BigDecimal bpEsAmt;
    //委托类型
    private String bpEntrustmentType;

    private String bpCname;
    private String bpDname;
    private String bpApprovalFileIds;
    private Integer secFlowState;
    private String pName;
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
    @ClassInfo(title = "主管单位",type="string",value = "pCompetentDepartmentNameList",dbValue = "pCompetentDepartmentName",no = 6)
    private List<String> pCompetentDepartmentNameList;

    private String isNew;

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }

    public String getBpName() {
        return bpName;
    }

    public void setBpName(String bpName) {
        this.bpName = bpName;
    }

    public Integer getBpYear() {
        return bpYear;
    }

    public void setBpYear(Integer bpYear) {
        this.bpYear = bpYear;
    }

    public Integer getBpMonth() {
        return bpMonth;
    }

    public void setBpMonth(Integer bpMonth) {
        this.bpMonth = bpMonth;
    }

    public String getBpRemark() {
        return bpRemark;
    }

    public void setBpRemark(String bpRemark) {
        this.bpRemark = bpRemark;
    }

    public String getBpCreateUid() {
        return bpCreateUid;
    }

    public void setBpCreateUid(String bpCreateUid) {
        this.bpCreateUid = bpCreateUid;
    }

    public String getBpCreateUname() {
        return bpCreateUname;
    }

    public void setBpCreateUname(String bpCreateUname) {
        this.bpCreateUname = bpCreateUname;
    }

    public Date getBpCreateTime() {
        return bpCreateTime;
    }

    public void setBpCreateTime(Date bpCreateTime) {
        this.bpCreateTime = bpCreateTime;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBpTenderee() {
        return bpTenderee;
    }

    public void setBpTenderee(String bpTenderee) {
        this.bpTenderee = bpTenderee;
    }

    public String getBpApprovalTitle() {
        return bpApprovalTitle;
    }

    public void setBpApprovalTitle(String bpApprovalTitle) {
        this.bpApprovalTitle = bpApprovalTitle;
    }

    public String getBpApprovalNum() {
        return bpApprovalNum;
    }

    public void setBpApprovalNum(String bpApprovalNum) {
        this.bpApprovalNum = bpApprovalNum;
    }

    public Date getBpApprovalTime() {
        return bpApprovalTime;
    }

    public void setBpApprovalTime(Date bpApprovalTime) {
        this.bpApprovalTime = bpApprovalTime;
    }

    public String getBpApprovalFile() {
        return bpApprovalFile;
    }

    public void setBpApprovalFile(String bpApprovalFile) {
        this.bpApprovalFile = bpApprovalFile;
    }

    public Date getBpSecTime() {
        return bpSecTime;
    }

    public void setBpSecTime(Date bpSecTime) {
        this.bpSecTime = bpSecTime;
    }

    public String getBpCid() {
        return bpCid;
    }

    public void setBpCid(String bpCid) {
        this.bpCid = bpCid;
    }

    public String getBpDid() {
        return bpDid;
    }

    public void setBpDid(String bpDid) {
        this.bpDid = bpDid;
    }

    public String getBpCnature() {
        return bpCnature;
    }

    public void setBpCnature(String bpCnature) {
        this.bpCnature = bpCnature;
    }

    public String getBpDnature() {
        return bpDnature;
    }

    public void setBpDnature(String bpDnature) {
        this.bpDnature = bpDnature;
    }

    public Date getBpTime() {
        return bpTime;
    }

    public void setBpTime(Date bpTime) {
        this.bpTime = bpTime;
    }

    public String getBpSecContent() {
        return bpSecContent;
    }

    public void setBpSecContent(String bpSecContent) {
        this.bpSecContent = bpSecContent;
    }

    public BigDecimal getBpGsAmt() {
        return bpGsAmt;
    }

    public void setBpGsAmt(BigDecimal bpGsAmt) {
        this.bpGsAmt = bpGsAmt;
    }

    public String getBpBiddingType() {
        return bpBiddingType;
    }

    public void setBpBiddingType(String bpBiddingType) {
        this.bpBiddingType = bpBiddingType;
    }

    public Date getBpEndTime() {
        return bpEndTime;
    }

    public void setBpEndTime(Date bpEndTime) {
        this.bpEndTime = bpEndTime;
    }

    public String getBpSecName() {
        return bpSecName;
    }

    public void setBpSecName(String bpSecName) {
        this.bpSecName = bpSecName;
    }

    public Boolean getBpGczb() {
        return bpGczb;
    }

    public void setBpGczb(Boolean bpGczb) {
        this.bpGczb = bpGczb;
    }

    public String getBpProScale() {
        return bpProScale;
    }

    public void setBpProScale(String bpProScale) {
        this.bpProScale = bpProScale;
    }

    public BigDecimal getBpProAmt() {
        return bpProAmt;
    }

    public void setBpProAmt(BigDecimal bpProAmt) {
        this.bpProAmt = bpProAmt;
    }

    public String getBpFundSource() {
        return bpFundSource;
    }

    public void setBpFundSource(String bpFundSource) {
        this.bpFundSource = bpFundSource;
    }

    public String getBpScopeOverview() {
        return bpScopeOverview;
    }

    public void setBpScopeOverview(String bpScopeOverview) {
        this.bpScopeOverview = bpScopeOverview;
    }

    public String getBpSm() {
        return bpSm;
    }

    public void setBpSm(String bpSm) {
        this.bpSm = bpSm;
    }

    public BigDecimal getBpEsAmt() {
        return bpEsAmt;
    }

    public void setBpEsAmt(BigDecimal bpEsAmt) {
        this.bpEsAmt = bpEsAmt;
    }

    public String getBpEntrustmentType() {
        return bpEntrustmentType;
    }

    public void setBpEntrustmentType(String bpEntrustmentType) {
        this.bpEntrustmentType = bpEntrustmentType;
    }

    public String getBpCname() {
        return bpCname;
    }

    public void setBpCname(String bpCname) {
        this.bpCname = bpCname;
    }

    public String getBpDname() {
        return bpDname;
    }

    public void setBpDname(String bpDname) {
        this.bpDname = bpDname;
    }

    public String getBpApprovalFileIds() {
        return bpApprovalFileIds;
    }

    public void setBpApprovalFileIds(String bpApprovalFileIds) {
        this.bpApprovalFileIds = bpApprovalFileIds;
    }

    public Integer getSecFlowState() {
        return secFlowState;
    }

    public void setSecFlowState(Integer secFlowState) {
        this.secFlowState = secFlowState;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

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
