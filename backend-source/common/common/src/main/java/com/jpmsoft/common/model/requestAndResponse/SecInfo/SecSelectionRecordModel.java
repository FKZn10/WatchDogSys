package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 专家选取记录
 */
@EqualsAndHashCode(callSuper = true)
public class SecSelectionRecordModel extends BaseFileRequest {
    private String srId;
    private String srType;
    private Date srAccessTime;
    private String srAbout;
    private Date srStart;
    private String srCompetent;
    private String srItems;
    private String srDataLists;
    private String srNotes;
    private String srContact;
    private String srPhone;
    private Integer state;
    private Date stateTime;
    private Date srCreateTime;
    private String srCreateUid;
    private String srCreateUname;
    /**
     * 专家选取申请ID
     */
    private String esId;
    private String srExList;
    private Integer srTechnologySum;
    private Integer srEconomicsSum;
    private Integer srTechnologyNum;
    private Integer srEconomicsNum;
    private Integer srExpertNum;
    private Date srBidTime;
    private String srLocation;
    private String srCompany;
    private String srMode;
    private String srOtherTxt;

    private String srCompanyName;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    //概算金额
    @JsonProperty("pApproveBudget")
    private BigDecimal pApproveBudget;
    //标段名称
    private String secName;
    //招标人Id
    private String secTenderee;
    //代理单位ID
    private String secAgency;
    //招标人(单位)
    private String secTendereeName;
    //代理单位
    private String secAgencyName;
    //招标方式
    private String secBiddingType;
    //组织形式
    private String secOrganizeForm;
    //资格审查方式
    private String secQualiReview;
    //是否评定分离
    private Boolean secEvaluateSeparation;
    //开标地点
    private String secLoaction;
    //开标时间
    private Date secBidTime;
    //投资人资格要求
    private String secInvestorsQuali;
    //备注(其他要求)
    private String secRemark;
    //建设地点
    private String secPlace;
    private String secType;
    //评标方法
    @JsonProperty("dFunction")
    private String dFunction;
    //招标范围
    @JsonProperty("dScope")
    private String dScope;
    //工期要求
    @JsonProperty("dExecutiveStandard")
    private String dExecutiveStandard;
    //招标控制价
    @JsonProperty("dAmount")
    private String dAmount;
    //开标时间
    @JsonProperty("dBidTime")
    private String dBidTime;
    //开标地点
    @JsonProperty("dLocation")
    private String dLocation;
    private Integer excludeDiscard;
    private List<String> srAccessTimes;
    private List<SecSelectionRecordDtlModel> secSelectionRecordDtlModels;
    private List<SecExpertShieldDto> secExpertShields;

    /**
     * 回避企业id
     */
    private String avoidCompanyId;
    /**
     * 回避企业名称
     */
    private String avoidCompanyName;

    /**
     * 备注
     */
    private String remark;


    //评分方法
    private String srScoreMethod;

    // 标段类别
    private String srSectionClassify;



    // 建设模式  007 是系统内代建
    @JsonProperty("pBuildModel")
    private String pBuildModel;

    // 项目类别
    @JsonProperty("pCategory")
    private String pCategory;

    // 责任单位
    private String businessUnitName;

    @JsonProperty("pCompetentDepartment")
    private String pCompetentDepartment;

    // 01 显示  02 置灰
    private String showHidden;

    public String getShowHidden() {
        return showHidden;
    }

    public void setShowHidden(String showHidden) {
        this.showHidden = showHidden;
    }

    @JsonProperty("pNameList")
    @ClassInfo(title = "项目名称",type = "string",value = "pNameList",dbValue = "pro.P_Name",no = 1)
    private List<String> pNameList;

    @JsonProperty("secNameList")
    @ClassInfo(title = "标段名称",type = "string",value = "secNameList",dbValue = "sec.Sec_Name",no = 2)
    private List<String> secNameList;

    @JsonProperty("srModeList")
    @ClassInfo(title = "评审类型",type = "dictionary",value = "srModeList",dbValue = "mn.sr_mode",no = 3,dictionary="JPMsoft_PSLX")
    private List<String> srModeList;

    @JsonProperty("srCreateUnameList")
    @ClassInfo(title = "发起人",type = "string",value = "srCreateUnameList",dbValue = "mn.sr_Create_Uname",no = 4)
    private List<String> srCreateUnameList;


    @JsonProperty("pCategoryList")
    @ClassInfo(title = "项目类别",type = "dictionary",value = "pCategoryList",dbValue = "pro.P_Category",no = 5,dictionary="JPMsoft_NDTZXMFL")
    private List<String> pCategoryList;

    @JsonProperty("stateList")
    @ClassInfo(title = "流程状态",type="dictionary",value = "stateList",dictionary = "JPMsoft_LCZT",dbValue = "mn.state",no = 6)
    private List<Integer> stateList;

    private String dQualiReview;

    private Date planOpenTime;

    private String planOpenLocation;

    private Date erBidTime;

    private String erMethod;

    @JsonProperty("dCalibrationMethod")
    private String dCalibrationMethod;


    private String erType;

    // 招标控制价
    private BigDecimal erTenderSumLimit;
    // 招标控制费率
    private BigDecimal erBiddingControlRate;
    // 折算控制价
    private BigDecimal erConversionControlPrice;

    private String secSectionClassify;

    private String keyExpertSelection;

    @ClassInfo(title = "是否重点专家选取", type = "dictionary", value = "keyExpertSelectionList", dbValue = "mn.key_expert_selection", no = 7, dictionary = "JPMsoft_TOF")
    private List<String> keyExpertSelectionList;

    private String conditionA;

    private String conditionB;

    private String conditionC;

    private String conditionD;

    private String conditionE;

    private String conditionDDetails;

    private String accordingD;

    private String accordingDDetails;

    private String expertDemonstration;

    private String expertDemonstrationIds;

    private String reason;

    /**
     * 项目所属行业
     */
    private String pProfessionalType;

    /**
     * 项目所属行业其他内容
     */
    private String pProfessionalTypeRemark;

    private BigDecimal secMoney;

    private String isNew;

    private List<String> participationOrFinished;

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public List<String> getParticipationOrFinished() {
        return participationOrFinished;
    }

    public void setParticipationOrFinished(List<String> participationOrFinished) {
        this.participationOrFinished = participationOrFinished;
    }

    public String getExpertDemonstrationIds() {
        return expertDemonstrationIds;
    }

    public void setExpertDemonstrationIds(String expertDemonstrationIds) {
        this.expertDemonstrationIds = expertDemonstrationIds;
    }

    public String getpProfessionalType() {
        return pProfessionalType;
    }

    public void setpProfessionalType(String pProfessionalType) {
        this.pProfessionalType = pProfessionalType;
    }

    public String getpProfessionalTypeRemark() {
        return pProfessionalTypeRemark;
    }

    public void setpProfessionalTypeRemark(String pProfessionalTypeRemark) {
        this.pProfessionalTypeRemark = pProfessionalTypeRemark;
    }

    public BigDecimal getSecMoney() {
        return secMoney;
    }

    public void setSecMoney(BigDecimal secMoney) {
        this.secMoney = secMoney;
    }

    public String getConditionA() {
        return conditionA;
    }

    public void setConditionA(String conditionA) {
        this.conditionA = conditionA;
    }

    public String getConditionB() {
        return conditionB;
    }

    public void setConditionB(String conditionB) {
        this.conditionB = conditionB;
    }

    public String getConditionC() {
        return conditionC;
    }

    public void setConditionC(String conditionC) {
        this.conditionC = conditionC;
    }

    public String getConditionD() {
        return conditionD;
    }

    public void setConditionD(String conditionD) {
        this.conditionD = conditionD;
    }

    public String getConditionE() {
        return conditionE;
    }

    public void setConditionE(String conditionE) {
        this.conditionE = conditionE;
    }

    public String getConditionDDetails() {
        return conditionDDetails;
    }

    public void setConditionDDetails(String conditionDDetails) {
        this.conditionDDetails = conditionDDetails;
    }

    public String getAccordingD() {
        return accordingD;
    }

    public void setAccordingD(String accordingD) {
        this.accordingD = accordingD;
    }

    public String getAccordingDDetails() {
        return accordingDDetails;
    }

    public void setAccordingDDetails(String accordingDDetails) {
        this.accordingDDetails = accordingDDetails;
    }

    public String getExpertDemonstration() {
        return expertDemonstration;
    }

    public void setExpertDemonstration(String expertDemonstration) {
        this.expertDemonstration = expertDemonstration;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getKeyExpertSelectionList() {
        return keyExpertSelectionList;
    }

    public void setKeyExpertSelectionList(List<String> keyExpertSelectionList) {
        this.keyExpertSelectionList = keyExpertSelectionList;
    }

    public String getKeyExpertSelection() {
        return keyExpertSelection;
    }

    public void setKeyExpertSelection(String keyExpertSelection) {
        this.keyExpertSelection = keyExpertSelection;
    }

    public String getSecSectionClassify() {
        return secSectionClassify;
    }

    public void setSecSectionClassify(String secSectionClassify) {
        this.secSectionClassify = secSectionClassify;
    }

    public BigDecimal getErConversionControlPrice() {
        return erConversionControlPrice;
    }

    public void setErConversionControlPrice(BigDecimal erConversionControlPrice) {
        this.erConversionControlPrice = erConversionControlPrice;
    }

    public BigDecimal getErTenderSumLimit() {
        return erTenderSumLimit;
    }

    public void setErTenderSumLimit(BigDecimal erTenderSumLimit) {
        this.erTenderSumLimit = erTenderSumLimit;
    }

    public BigDecimal getErBiddingControlRate() {
        return erBiddingControlRate;
    }

    public void setErBiddingControlRate(BigDecimal erBiddingControlRate) {
        this.erBiddingControlRate = erBiddingControlRate;
    }

    public String getErType() {
        return erType;
    }

    public void setErType(String erType) {
        this.erType = erType;
    }

    public String getdCalibrationMethod() {
        return dCalibrationMethod;
    }

    public void setdCalibrationMethod(String dCalibrationMethod) {
        this.dCalibrationMethod = dCalibrationMethod;
    }

    public String getErMethod() {
        return erMethod;
    }

    public void setErMethod(String erMethod) {
        this.erMethod = erMethod;
    }

    public Date getErBidTime() {
        return erBidTime;
    }

    public void setErBidTime(Date erBidTime) {
        this.erBidTime = erBidTime;
    }

    public String getdQualiReview() {
        return dQualiReview;
    }

    public void setdQualiReview(String dQualiReview) {
        this.dQualiReview = dQualiReview;
    }

    public Date getPlanOpenTime() {
        return planOpenTime;
    }

    public void setPlanOpenTime(Date planOpenTime) {
        this.planOpenTime = planOpenTime;
    }

    public String getPlanOpenLocation() {
        return planOpenLocation;
    }

    public void setPlanOpenLocation(String planOpenLocation) {
        this.planOpenLocation = planOpenLocation;
    }

    public String getSrSectionClassify() {
        return srSectionClassify;
    }

    public void setSrSectionClassify(String srSectionClassify) {
        this.srSectionClassify = srSectionClassify;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }

    public List<String> getpCategoryList() {
        return pCategoryList;
    }

    public void setpCategoryList(List<String> pCategoryList) {
        this.pCategoryList = pCategoryList;
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

    public List<String> getSrModeList() {
        return srModeList;
    }

    public void setSrModeList(List<String> srModeList) {
        this.srModeList = srModeList;
    }

    public List<String> getSrCreateUnameList() {
        return srCreateUnameList;
    }

    public void setSrCreateUnameList(List<String> srCreateUnameList) {
        this.srCreateUnameList = srCreateUnameList;
    }

    public List<Integer> getStateList() {
        return stateList;
    }

    public void setStateList(List<Integer> stateList) {
        this.stateList = stateList;
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
    public String getBusinessUnitName() {
        return businessUnitName;
    }

    public void setBusinessUnitName(String businessUnitName) {
        this.businessUnitName = businessUnitName;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAvoidCompanyId() {
        return avoidCompanyId;
    }

    public void setAvoidCompanyId(String avoidCompanyId) {
        this.avoidCompanyId = avoidCompanyId;
    }

    public String getAvoidCompanyName() {
        return avoidCompanyName;
    }

    public void setAvoidCompanyName(String avoidCompanyName) {
        this.avoidCompanyName = avoidCompanyName;
    }

    public String getSrId() {
        return srId;
    }

    public void setSrId(String srId) {
        this.srId = srId;
    }

    public String getSrType() {
        return srType;
    }

    public void setSrType(String srType) {
        this.srType = srType;
    }

    public Date getSrAccessTime() {
        return srAccessTime;
    }

    public void setSrAccessTime(Date srAccessTime) {
        this.srAccessTime = srAccessTime;
    }

    public String getSrAbout() {
        return srAbout;
    }

    public void setSrAbout(String srAbout) {
        this.srAbout = srAbout;
    }

    public Date getSrStart() {
        return srStart;
    }

    public void setSrStart(Date srStart) {
        this.srStart = srStart;
    }

    public String getSrCompetent() {
        return srCompetent;
    }

    public void setSrCompetent(String srCompetent) {
        this.srCompetent = srCompetent;
    }

    public String getSrItems() {
        return srItems;
    }

    public void setSrItems(String srItems) {
        this.srItems = srItems;
    }

    public String getSrDataLists() {
        return srDataLists;
    }

    public void setSrDataLists(String srDataLists) {
        this.srDataLists = srDataLists;
    }

    public String getSrNotes() {
        return srNotes;
    }

    public void setSrNotes(String srNotes) {
        this.srNotes = srNotes;
    }

    public String getSrContact() {
        return srContact;
    }

    public void setSrContact(String srContact) {
        this.srContact = srContact;
    }

    public String getSrPhone() {
        return srPhone;
    }

    public void setSrPhone(String srPhone) {
        this.srPhone = srPhone;
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

    public Date getSrCreateTime() {
        return srCreateTime;
    }

    public void setSrCreateTime(Date srCreateTime) {
        this.srCreateTime = srCreateTime;
    }

    public String getSrCreateUid() {
        return srCreateUid;
    }

    public void setSrCreateUid(String srCreateUid) {
        this.srCreateUid = srCreateUid;
    }

    public String getSrCreateUname() {
        return srCreateUname;
    }

    public void setSrCreateUname(String srCreateUname) {
        this.srCreateUname = srCreateUname;
    }

    public String getEsId() {
        return esId;
    }

    public void setEsId(String esId) {
        this.esId = esId;
    }

    public String getSrExList() {
        return srExList;
    }

    public void setSrExList(String srExList) {
        this.srExList = srExList;
    }

    public Integer getSrTechnologySum() {
        return srTechnologySum;
    }

    public void setSrTechnologySum(Integer srTechnologySum) {
        this.srTechnologySum = srTechnologySum;
    }

    public Integer getSrEconomicsSum() {
        return srEconomicsSum;
    }

    public void setSrEconomicsSum(Integer srEconomicsSum) {
        this.srEconomicsSum = srEconomicsSum;
    }

    public Integer getSrTechnologyNum() {
        return srTechnologyNum;
    }

    public void setSrTechnologyNum(Integer srTechnologyNum) {
        this.srTechnologyNum = srTechnologyNum;
    }

    public Integer getSrEconomicsNum() {
        return srEconomicsNum;
    }

    public void setSrEconomicsNum(Integer srEconomicsNum) {
        this.srEconomicsNum = srEconomicsNum;
    }

    public Integer getSrExpertNum() {
        return srExpertNum;
    }

    public void setSrExpertNum(Integer srExpertNum) {
        this.srExpertNum = srExpertNum;
    }

    public Date getSrBidTime() {
        return srBidTime;
    }

    public void setSrBidTime(Date srBidTime) {
        this.srBidTime = srBidTime;
    }

    public String getSrLocation() {
        return srLocation;
    }

    public void setSrLocation(String srLocation) {
        this.srLocation = srLocation;
    }

    public String getSrCompany() {
        return srCompany;
    }

    public void setSrCompany(String srCompany) {
        this.srCompany = srCompany;
    }

    public String getSrMode() {
        return srMode;
    }

    public void setSrMode(String srMode) {
        this.srMode = srMode;
    }

    public String getSrOtherTxt() {
        return srOtherTxt;
    }

    public void setSrOtherTxt(String srOtherTxt) {
        this.srOtherTxt = srOtherTxt;
    }

    public String getSrCompanyName() {
        return srCompanyName;
    }

    public void setSrCompanyName(String srCompanyName) {
        this.srCompanyName = srCompanyName;
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

    public BigDecimal getpApproveBudget() {
        return pApproveBudget;
    }

    public void setpApproveBudget(BigDecimal pApproveBudget) {
        this.pApproveBudget = pApproveBudget;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecTenderee() {
        return secTenderee;
    }

    public void setSecTenderee(String secTenderee) {
        this.secTenderee = secTenderee;
    }

    public String getSecAgency() {
        return secAgency;
    }

    public void setSecAgency(String secAgency) {
        this.secAgency = secAgency;
    }

    public String getSecTendereeName() {
        return secTendereeName;
    }

    public void setSecTendereeName(String secTendereeName) {
        this.secTendereeName = secTendereeName;
    }

    public String getSecAgencyName() {
        return secAgencyName;
    }

    public void setSecAgencyName(String secAgencyName) {
        this.secAgencyName = secAgencyName;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
    }

    public String getSecOrganizeForm() {
        return secOrganizeForm;
    }

    public void setSecOrganizeForm(String secOrganizeForm) {
        this.secOrganizeForm = secOrganizeForm;
    }

    public String getSecQualiReview() {
        return secQualiReview;
    }

    public void setSecQualiReview(String secQualiReview) {
        this.secQualiReview = secQualiReview;
    }

    public Boolean getSecEvaluateSeparation() {
        return secEvaluateSeparation;
    }

    public void setSecEvaluateSeparation(Boolean secEvaluateSeparation) {
        this.secEvaluateSeparation = secEvaluateSeparation;
    }

    public String getSecLoaction() {
        return secLoaction;
    }

    public void setSecLoaction(String secLoaction) {
        this.secLoaction = secLoaction;
    }

    public Date getSecBidTime() {
        return secBidTime;
    }

    public void setSecBidTime(Date secBidTime) {
        this.secBidTime = secBidTime;
    }

    public String getSecInvestorsQuali() {
        return secInvestorsQuali;
    }

    public void setSecInvestorsQuali(String secInvestorsQuali) {
        this.secInvestorsQuali = secInvestorsQuali;
    }

    public String getSecRemark() {
        return secRemark;
    }

    public void setSecRemark(String secRemark) {
        this.secRemark = secRemark;
    }

    public String getSecPlace() {
        return secPlace;
    }

    public void setSecPlace(String secPlace) {
        this.secPlace = secPlace;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public String getdFunction() {
        return dFunction;
    }

    public void setdFunction(String dFunction) {
        this.dFunction = dFunction;
    }

    public String getdScope() {
        return dScope;
    }

    public void setdScope(String dScope) {
        this.dScope = dScope;
    }

    public String getdExecutiveStandard() {
        return dExecutiveStandard;
    }

    public void setdExecutiveStandard(String dExecutiveStandard) {
        this.dExecutiveStandard = dExecutiveStandard;
    }

    public String getdAmount() {
        return dAmount;
    }

    public void setdAmount(String dAmount) {
        this.dAmount = dAmount;
    }

    public String getdBidTime() {
        return dBidTime;
    }

    public void setdBidTime(String dBidTime) {
        this.dBidTime = dBidTime;
    }

    public String getdLocation() {
        return dLocation;
    }

    public void setdLocation(String dLocation) {
        this.dLocation = dLocation;
    }

    public Integer getExcludeDiscard() {
        return excludeDiscard;
    }

    public void setExcludeDiscard(Integer excludeDiscard) {
        this.excludeDiscard = excludeDiscard;
    }

    public List<String> getSrAccessTimes() {
        return srAccessTimes;
    }

    public void setSrAccessTimes(List<String> srAccessTimes) {
        this.srAccessTimes = srAccessTimes;
    }

    public List<SecSelectionRecordDtlModel> getSecSelectionRecordDtlModels() {
        return secSelectionRecordDtlModels;
    }

    public void setSecSelectionRecordDtlModels(List<SecSelectionRecordDtlModel> secSelectionRecordDtlModels) {
        this.secSelectionRecordDtlModels = secSelectionRecordDtlModels;
    }

    public List<SecExpertShieldDto> getSecExpertShields() {
        return secExpertShields;
    }

    public void setSecExpertShields(List<SecExpertShieldDto> secExpertShields) {
        this.secExpertShields = secExpertShields;
    }

    public String getSrScoreMethod() {
        return srScoreMethod;
    }

    public void setSrScoreMethod(String srScoreMethod) {
        this.srScoreMethod = srScoreMethod;
    }
}
