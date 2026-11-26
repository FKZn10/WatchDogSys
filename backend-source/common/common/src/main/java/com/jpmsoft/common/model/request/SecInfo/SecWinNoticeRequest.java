package com.jpmsoft.common.model.request.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 中标通知书
 */
public class SecWinNoticeRequest extends BaseFileRequest {

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


    private Date wnEvaluationTime;
    private BigDecimal wnBiddingPeriod;
    private String wnPm;
    private String wnExplanation;
    private Date wnStartTime;
    private Date wnEndTime;
    private String wnPublicityFile;
    private String wnPublicityFileIds;


    private String wnWinFileIds;
    private String wnAwardFileIds;
    private String wnBidFileIds;
    private String wnCalibrateFileIds;
    private List<SecWinUnitRequest> winUnits;
    private List<SecWinUnitMemberRequest> winUnitMembers;
    private String secName;
    private String secBiddingType;
    private String secPlace;
    //中标金额（范围筛选）、
    private List<String> wnAmounts;
    //下浮率（范围筛选）、
    private List<String> wnLowers;
    //下浮率（范围筛选）、
    private List<String> wnCreateTimes;
    //中标单位
    private String cName;
    private String pName;
    private String secWinUnits;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;
    private String projectOwner;

    @JsonProperty("pNameList")
    @ClassInfo(title = "项目名称", type = "string", value = "pNameList", dbValue = "case when pre.P_Name is null then sp.P_Name else pre.P_Name end", no = 1)
    private List<String> pNameList;

    @ClassInfo(title = "标段名称", type = "string", value = "secNameList", dbValue = "sec.sec_Name", no = 2)
    private List<String> secNameList;

    @JsonProperty("pCategoryList")
    @ClassInfo(title = "项目分类", type = "dictionary", value = "pCategoryList", dbValue = "case when pre.P_Category is null then sp.p_Category else pre.P_Category end", dictionary = "JPMsoft_NDTZXMFL", no = 3)
    private List<String> pCategoryList;

    @JsonProperty("createUnameList")
    @ClassInfo(title = "发起人", type = "string", value = "createUnameList", dbValue = "mn.Wn_Create_Uname", no = 4)
    private List<String> createUnameList;

    @JsonProperty("createTimeStr")
    @ClassInfo(title = "发起时间", type = "date", value = "createTimeStr", dbValue = "mn.Wn_Create_Time", no = 5)
    private String createTimeStr;

    @JsonProperty("stateList")
    @ClassInfo(title = "流程状态", type = "dictionary", value = "stateList", dbValue = "mn.Wn_State", dictionary = "JPMsoft_LCZT", no = 6)
    private List<Integer> stateList;

    private List<String> participationOrFinished;

    private String isNew;

    private String tendereeId;

    private List<SecProjectResponse> secProjects;

    public String getTendereeId() {
        return tendereeId;
    }

    public void setTendereeId(String tendereeId) {
        this.tendereeId = tendereeId;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public List<String> getpNameList() {
        return pNameList;
    }

    public void setpNameList(List<String> pNameList) {
        this.pNameList = pNameList;
    }

    public List<String> getSecNameList() {
        return secNameList;
    }

    public void setSecNameList(List<String> secNameList) {
        this.secNameList = secNameList;
    }

    public List<String> getpCategoryList() {
        return pCategoryList;
    }

    public void setpCategoryList(List<String> pCategoryList) {
        this.pCategoryList = pCategoryList;
    }

    public List<String> getCreateUnameList() {
        return createUnameList;
    }

    public void setCreateUnameList(List<String> createUnameList) {
        this.createUnameList = createUnameList;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public List<Integer> getStateList() {
        return stateList;
    }

    public void setStateList(List<Integer> stateList) {
        this.stateList = stateList;
    }

    public List<String> getParticipationOrFinished() {
        return participationOrFinished;
    }

    public void setParticipationOrFinished(List<String> participationOrFinished) {
        this.participationOrFinished = participationOrFinished;
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

    public String getSecWinUnits() {
        return secWinUnits;
    }

    public void setSecWinUnits(String secWinUnits) {
        this.secWinUnits = secWinUnits;
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

    public String getWnPublicityFileIds() {
        return wnPublicityFileIds;
    }

    public void setWnPublicityFileIds(String wnPublicityFileIds) {
        this.wnPublicityFileIds = wnPublicityFileIds;
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

    public String getWnWinFileIds() {
        return wnWinFileIds;
    }

    public void setWnWinFileIds(String wnWinFileIds) {
        this.wnWinFileIds = wnWinFileIds;
    }

    public String getWnAwardFileIds() {
        return wnAwardFileIds;
    }

    public void setWnAwardFileIds(String wnAwardFileIds) {
        this.wnAwardFileIds = wnAwardFileIds;
    }

    public String getWnBidFileIds() {
        return wnBidFileIds;
    }

    public void setWnBidFileIds(String wnBidFileIds) {
        this.wnBidFileIds = wnBidFileIds;
    }

    public String getWnCalibrateFileIds() {
        return wnCalibrateFileIds;
    }

    public void setWnCalibrateFileIds(String wnCalibrateFileIds) {
        this.wnCalibrateFileIds = wnCalibrateFileIds;
    }

    public List<SecWinUnitRequest> getWinUnits() {
        return winUnits;
    }

    public void setWinUnits(List<SecWinUnitRequest> winUnits) {
        this.winUnits = winUnits;
    }

    public List<SecWinUnitMemberRequest> getWinUnitMembers() {
        return winUnitMembers;
    }

    public void setWinUnitMembers(List<SecWinUnitMemberRequest> winUnitMembers) {
        this.winUnitMembers = winUnitMembers;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
    }

    public String getSecPlace() {
        return secPlace;
    }

    public void setSecPlace(String secPlace) {
        this.secPlace = secPlace;
    }

    public List<String> getWnAmounts() {
        return wnAmounts;
    }

    public void setWnAmounts(List<String> wnAmounts) {
        this.wnAmounts = wnAmounts;
    }

    public List<String> getWnLowers() {
        return wnLowers;
    }

    public void setWnLowers(List<String> wnLowers) {
        this.wnLowers = wnLowers;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public List<String> getWnCreateTimes() {
        return wnCreateTimes;
    }

    public void setWnCreateTimes(List<String> wnCreateTimes) {
        this.wnCreateTimes = wnCreateTimes;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

}
