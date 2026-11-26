package com.jpmsoft.common.model.response.SecInfo;

import com.jpmsoft.common.model.requestAndResponse.SecCommonModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecWinNoticeDtlDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 中标通知书
 */

public class SecWinNoticeResponse extends SecCommonModel implements Serializable {

    private static final long serialVersionUID = 3596158064691348201L;

    private String wnId;
    private String secId;
    private BigDecimal wnAmount;
    //中标金额单位
    private String wnAmountUnit;
    private Float wnLower;
    private String wnRemark;
    private String wnCreateUid;
    private String wnCreateUname;
    private Date wnCreateTime;
    private Integer wnState;
    private Date wnStateTime;
    private Integer wnManagePerson;
    private String wnCType;
    private BigDecimal wnRate;
    private String wnOtherValuation;
    private String wnCalibrationMode;
    private String wnWinUnit;

    private String wnWinUnitName;
    /**
     * 中标通知书日期
     */
    private Date wnDate;
    private String wnApprovalMatter;
    private String wnWinFile;
    private String wnAwardFile;
    private String wnBidFile;
    private String wnCalibrateFile;
    //采购小组决策方式
    private String wnDecisionMethods;
    //经办人
    private String wnOperator;
    //经办人姓名
    private String wnOperatorName;
    //联系方式
    private String wnContactInformation;
    //申请时间
    private Date wnApplicationTime;


    /*****************/
    private String dDeterminType;
    private String secBiddingType;
    private BigDecimal cpRate;
    private String cpDescribe;
    private String pName;
    private String secCode;
    private String secOrganizeForm;
    private Date secStartTime;
    private Integer secBiddingCycle;
    private String secPlace;
    private String tendereeName;
    private String pCompetentDepartmentName;
    private List<SecWinUnitResponse> secWinUnits;
    private List<SecWinUnitMemberResponse> secWinUnitMembers;
    private List<SecProjectResponse> secProjects;

    //单位简称
    private String tendereeAbbName;

    private Date wnEvaluationTime;
    private BigDecimal wnBiddingPeriod;
    private String wnPm;
    private String wnExplanation;
    private Date wnStartTime;
    private Date wnEndTime;
    private String wnPublicityFile;


    // 选择方式  0 - 中标金额   1- 下浮率  null - 默认不选
    private Integer cumulativeComplete;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;
    private String projectOwner;

    private String pCategory;
    private String pBuildModel;

    private String tendereeId;

    private List<SecWinNoticeDtlDTO> secWinNoticeDtl;

    public String getTendereeId() {
        return tendereeId;
    }

    public void setTendereeId(String tendereeId) {
        this.tendereeId = tendereeId;
    }

    public List<SecWinNoticeDtlDTO> getSecWinNoticeDtl() {
        return secWinNoticeDtl;
    }

    public void setSecWinNoticeDtl(List<SecWinNoticeDtlDTO> secWinNoticeDtl) {
        this.secWinNoticeDtl = secWinNoticeDtl;
    }

    public String getpCategory() {
        return pCategory;
    }

    public void setpCategory(String pCategory) {
        this.pCategory = pCategory;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    public String getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        this.projectOwner = projectOwner;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getProjectInitiatingUnit() {
        return projectInitiatingUnit;
    }

    public void setProjectInitiatingUnit(String projectInitiatingUnit) {
        this.projectInitiatingUnit = projectInitiatingUnit;
    }

    public String getWnWinUnitName() {
        return wnWinUnitName;
    }

    public void setWnWinUnitName(String wnWinUnitName) {
        this.wnWinUnitName = wnWinUnitName;
    }
    public Integer getCumulativeComplete() {
        return cumulativeComplete;
    }

    public void setCumulativeComplete(Integer cumulativeComplete) {
        this.cumulativeComplete = cumulativeComplete;
    }

    public Date getWnEvaluationTime() {
        return wnEvaluationTime;
    }

    public void setWnEvaluationTime(Date wnEvaluationTime) {
        this.wnEvaluationTime = wnEvaluationTime;
    }

    public BigDecimal getWnBiddingPeriod() {
        return wnBiddingPeriod;
    }

    public void setWnBiddingPeriod(BigDecimal wnBiddingPeriod) {
        this.wnBiddingPeriod = wnBiddingPeriod;
    }

    public String getWnPm() {
        return wnPm;
    }

    public void setWnPm(String wnPm) {
        this.wnPm = wnPm;
    }

    public String getWnExplanation() {
        return wnExplanation;
    }

    public void setWnExplanation(String wnExplanation) {
        this.wnExplanation = wnExplanation;
    }

    public Date getWnStartTime() {
        return wnStartTime;
    }

    public void setWnStartTime(Date wnStartTime) {
        this.wnStartTime = wnStartTime;
    }

    public Date getWnEndTime() {
        return wnEndTime;
    }

    public void setWnEndTime(Date wnEndTime) {
        this.wnEndTime = wnEndTime;
    }

    public String getWnPublicityFile() {
        return wnPublicityFile;
    }

    public void setWnPublicityFile(String wnPublicityFile) {
        this.wnPublicityFile = wnPublicityFile;
    }

    public String getWnId() {
        return wnId;
    }

    public void setWnId(String wnId) {
        this.wnId = wnId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public BigDecimal getWnAmount() {
        return wnAmount;
    }

    public void setWnAmount(BigDecimal wnAmount) {
        this.wnAmount = wnAmount;
    }

    public String getWnAmountUnit() {
        return wnAmountUnit;
    }

    public void setWnAmountUnit(String wnAmountUnit) {
        this.wnAmountUnit = wnAmountUnit;
    }

    public Float getWnLower() {
        return wnLower;
    }

    public void setWnLower(Float wnLower) {
        this.wnLower = wnLower;
    }

    public String getWnRemark() {
        return wnRemark;
    }

    public void setWnRemark(String wnRemark) {
        this.wnRemark = wnRemark;
    }

    public String getWnCreateUid() {
        return wnCreateUid;
    }

    public void setWnCreateUid(String wnCreateUid) {
        this.wnCreateUid = wnCreateUid;
    }

    public String getWnCreateUname() {
        return wnCreateUname;
    }

    public void setWnCreateUname(String wnCreateUname) {
        this.wnCreateUname = wnCreateUname;
    }

    public Date getWnCreateTime() {
        return wnCreateTime;
    }

    public void setWnCreateTime(Date wnCreateTime) {
        this.wnCreateTime = wnCreateTime;
    }

    public Integer getWnState() {
        return wnState;
    }

    public void setWnState(Integer wnState) {
        this.wnState = wnState;
    }

    public Date getWnStateTime() {
        return wnStateTime;
    }

    public void setWnStateTime(Date wnStateTime) {
        this.wnStateTime = wnStateTime;
    }

    public Integer getWnManagePerson() {
        return wnManagePerson;
    }

    public void setWnManagePerson(Integer wnManagePerson) {
        this.wnManagePerson = wnManagePerson;
    }

    public String getWnCType() {
        return wnCType;
    }

    public void setWnCType(String wnCType) {
        this.wnCType = wnCType;
    }

    public BigDecimal getWnRate() {
        return wnRate;
    }

    public void setWnRate(BigDecimal wnRate) {
        this.wnRate = wnRate;
    }

    public String getWnOtherValuation() {
        return wnOtherValuation;
    }

    public void setWnOtherValuation(String wnOtherValuation) {
        this.wnOtherValuation = wnOtherValuation;
    }

    public String getWnCalibrationMode() {
        return wnCalibrationMode;
    }

    public void setWnCalibrationMode(String wnCalibrationMode) {
        this.wnCalibrationMode = wnCalibrationMode;
    }

    public String getWnWinUnit() {
        return wnWinUnit;
    }

    public void setWnWinUnit(String wnWinUnit) {
        this.wnWinUnit = wnWinUnit;
    }

    public Date getWnDate() {
        return wnDate;
    }

    public void setWnDate(Date wnDate) {
        this.wnDate = wnDate;
    }

    public String getWnApprovalMatter() {
        return wnApprovalMatter;
    }

    public void setWnApprovalMatter(String wnApprovalMatter) {
        this.wnApprovalMatter = wnApprovalMatter;
    }

    public String getWnWinFile() {
        return wnWinFile;
    }

    public void setWnWinFile(String wnWinFile) {
        this.wnWinFile = wnWinFile;
    }

    public String getWnAwardFile() {
        return wnAwardFile;
    }

    public void setWnAwardFile(String wnAwardFile) {
        this.wnAwardFile = wnAwardFile;
    }

    public String getWnBidFile() {
        return wnBidFile;
    }

    public void setWnBidFile(String wnBidFile) {
        this.wnBidFile = wnBidFile;
    }

    public String getWnCalibrateFile() {
        return wnCalibrateFile;
    }

    public void setWnCalibrateFile(String wnCalibrateFile) {
        this.wnCalibrateFile = wnCalibrateFile;
    }

    public String getWnDecisionMethods() {
        return wnDecisionMethods;
    }

    public void setWnDecisionMethods(String wnDecisionMethods) {
        this.wnDecisionMethods = wnDecisionMethods;
    }

    public String getWnOperator() {
        return wnOperator;
    }

    public void setWnOperator(String wnOperator) {
        this.wnOperator = wnOperator;
    }

    public String getWnOperatorName() {
        return wnOperatorName;
    }

    public void setWnOperatorName(String wnOperatorName) {
        this.wnOperatorName = wnOperatorName;
    }

    public String getWnContactInformation() {
        return wnContactInformation;
    }

    public void setWnContactInformation(String wnContactInformation) {
        this.wnContactInformation = wnContactInformation;
    }

    public Date getWnApplicationTime() {
        return wnApplicationTime;
    }

    public void setWnApplicationTime(Date wnApplicationTime) {
        this.wnApplicationTime = wnApplicationTime;
    }

    public String getdDeterminType() {
        return dDeterminType;
    }

    public void setdDeterminType(String dDeterminType) {
        this.dDeterminType = dDeterminType;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
    }

    public BigDecimal getCpRate() {
        return cpRate;
    }

    public void setCpRate(BigDecimal cpRate) {
        this.cpRate = cpRate;
    }

    public String getCpDescribe() {
        return cpDescribe;
    }

    public void setCpDescribe(String cpDescribe) {
        this.cpDescribe = cpDescribe;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public String getSecOrganizeForm() {
        return secOrganizeForm;
    }

    public void setSecOrganizeForm(String secOrganizeForm) {
        this.secOrganizeForm = secOrganizeForm;
    }

    public Date getSecStartTime() {
        return secStartTime;
    }

    public void setSecStartTime(Date secStartTime) {
        this.secStartTime = secStartTime;
    }

    public Integer getSecBiddingCycle() {
        return secBiddingCycle;
    }

    public void setSecBiddingCycle(Integer secBiddingCycle) {
        this.secBiddingCycle = secBiddingCycle;
    }

    public String getSecPlace() {
        return secPlace;
    }

    public void setSecPlace(String secPlace) {
        this.secPlace = secPlace;
    }

    public String getTendereeName() {
        return tendereeName;
    }

    public void setTendereeName(String tendereeName) {
        this.tendereeName = tendereeName;
    }

    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public List<SecWinUnitResponse> getSecWinUnits() {
        return secWinUnits;
    }

    public void setSecWinUnits(List<SecWinUnitResponse> secWinUnits) {
        this.secWinUnits = secWinUnits;
    }

    public List<SecWinUnitMemberResponse> getSecWinUnitMembers() {
        return secWinUnitMembers;
    }

    public void setSecWinUnitMembers(List<SecWinUnitMemberResponse> secWinUnitMembers) {
        this.secWinUnitMembers = secWinUnitMembers;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public String getTendereeAbbName() {
        return tendereeAbbName;
    }

    public void setTendereeAbbName(String tendereeAbbName) {
        this.tendereeAbbName = tendereeAbbName;
    }
}