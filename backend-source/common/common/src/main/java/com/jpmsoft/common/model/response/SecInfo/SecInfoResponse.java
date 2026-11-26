package com.jpmsoft.common.model.response.SecInfo;

import com.jpmsoft.common.model.request.SecInfo.SecFundsDTO;
import com.jpmsoft.common.model.request.SecInfo.SecInfoBudgetDeclareDtlDto;
import com.jpmsoft.common.model.request.SecInfo.SecProcurementDetailsDTO;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoBudgetModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSourcesFundsModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 标段信息
 */
public class SecInfoResponse implements Serializable {

    private static final long serialVersionUID = -7129942072268621249L;
    /*********原始表信息*******/
    private String secId;
    private String secName;
    private String secCode;
    private String secParentId;
    private Integer secYear;
    private String secBiddingType;
    private String secConType;
    private String secType;
    private BigDecimal secMoney;
    private Date secStartTime;
    private Date secEndTime;
    private String secRemark;
    private Date secFlowTime;
    private Integer secFlowState;
    private String secCreateUid;
    private String secCreateUname;
    private Date secCreateTime;
    private String secPid;
    private String secTenderee;
    private String secFeatures;
    private String secPlace;
    private String secScale;
    private String bpId;
    private Integer bpState;
    private Integer wnState;
    private String wnId;
    private BigDecimal secRate;
    private String secOtherValuation;
    private String secDeterminType;
    private String secOrganizeForm;
    private String secTradingPlace;
    private Integer secBiddingCycle;
    private String secApprovalTitle;
    private String secApprovalNum;
    private Date secApprovalTime;
    private String secApprovalFile;
    private String secPricingMethod;
    private String secPurchaseType;
    private String secEvalMethod;
    private Boolean secEvaluateSeparation;
    private Integer secDuration;
    private String secQualiReview;
    private String secInvestorsQuali;
    private String secValuationFile;
    private Date secBidTime;
    private String secLoaction;
    private String secAgency;
    private String secContractingDepartment;
    private Date secPlanOpenTime;
    private String secContractingType;
    private Boolean secConsortium;
    private Boolean secSubpackage;
    private Boolean secEnterprise;
    private Boolean secImport;
    private String secProjectNature;
    private String secSourceFund;
    private String secFundsReceived;
    private String secSubscriber;
    private String secContacts;
    private String secContactsPhone;
    private String secContactsTel;
    private String secContactsEmail;
    private String secDelegate;
    private BigDecimal secInternetDay;
    private Boolean secAnnounce;
    private BigDecimal secSigningDay;
    private String secPayMethod;
    private BigDecimal secArrivalDay;
    private String secReceAddr;
    private String secSupplier;
    private String secSupplierName;
    private String secSupplierPhone;
    private String secSupplierTel;
    private String secSupplierEmail;
    private String secSupplierAddr;
    private String secConstrAddr;
    private BigDecimal secConstrDay;
    private String secCatalog;
    private String secProCategory;
    private String secProofFile;
    private String secBlankFile;
    private String secOtherFile;
    private String secOtherImg;
    private String secArrival;
    private Boolean secSingleSource;
    private String secContent;
    private BigDecimal secGsAmt;
    private String secTendereeName;
    private String secAgencyCname;
    private String secProOwn;
    private String secEntryCode;
    private String secEntryName;
    private String secCatalogName;
    /*********外键关联信息*******/
    private String secContactsName;
    private String secDelegateName;
    //开标时间
    private String dBidTime;
    //开标地点
    private String dLocation;
    private String secContractingDepartmentName;
    private String secAgencyName;
    private String secAgencyAbbName;
    //招标人名称
    private String tendereeName;
    private String tendereeNameHistory;
    private List<SecProjectResponse> secProjects;
    private List<SecInfoBudgetModel> secInfoBudgets;
    private List<SecSourcesFundsModel> sourcesFundsModels;
    private List<SecProcurementDetailsDTO> secProcurementDetails;
    private List<SecFundsDTO> secFunds;
    private String pName;

    private String pCode;

    private String pType;

    private List<SecPublicityResultsResponse> secPublicityResults;

    private String pCampus;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 发起单位
     */
    private String projectInitiatingUnit;

    private String isNew;

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
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

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpType() {
        return pType;
    }

    public void setpCampus(String pCampus) {
        this.pCampus = pCampus;
    }

    public String getpCampus() {
        return pCampus;
    }

    public void setSecPublicityResults(List<SecPublicityResultsResponse> secPublicityResults) {
        this.secPublicityResults = secPublicityResults;
    }

    public List<SecPublicityResultsResponse> getSecPublicityResults() {
        return secPublicityResults;
    }
    private String pId;
    private String pCompetentDepartmentName;

    //单位简称
    private String tendereeAbbName;
    /**
     * 判断是否可以走计划变更
     * 大于0则不能在计划变更中修改
     */
    private int unalterable;
    //总节点数
    private int nodeTotal;
    //已完成
    private int nodeComplete;
    //审批中
    private int nodeApproval;
    //未启动
    private int nodeUnStart;
    //当前所在节点名称以及状态
    private Map<String,Object> currentNode;

    /**
     * 合同估算起始比例
     */
    private Double contractEvaluateAmountRateStart;

    /**
     * 合同估算截至比例
     */
    private Double contractEvaluateAmountRateEnd;

    /**
     * 编标时间
     */
    private Date makeBiddingTime;

    /**
     * 开标时间
     */
    private Date startBiddingTime;

    /**
     * 拟开标场所
     */
    private String startBiddingPlace;

    /**
     * 服务期
     */
    private String servicePeriod;

    /**
     * 采购方式说明
     */
    private String procurementDes;

    /**
     * 采购用途
     */
    private String procurementPurpose;

    /**
     * 是否列入年度采购计划
     */
    private String annualProcurementPlan;

    /**
     * 意向公开日期
     */
    private String intendedPublicDate;

    /**
     * 计划采购时间
     */
    private Date plannedProcurementTime;

    /**
     * 预算确定方式
     */
    private String budgetDeterminationMethod;

    /**
     * 资金落实情况
     */
    private String fundingImplementationStatus;

    /**
     * 采购需求
     */
    private Date procurementRequirements;

    /**
     * 重点项目自查
     */
    private String projectSelfInspection;

    /**
     * 项目补充说明
     */
    private String projectExplanation;

    /**
     * 采购需求一览表  原先存附件批次id 现：表示是否有这个附件1：有  0：没有 。下同
     */
    private String procurementListDoc;

    private String procurementListDocFileIds;


    /**
     * 采购计划调整（追加）申报表
     */
    private String procurementPlanDoc;

    private String procurementPlanDocFileIds;

    /**
     * 免意向公开说明
     */
    private String noIntentionDiscloseDoc;

    private String noIntentionDiscloseDocFileIds;


    /**
     * 预算确定支持材料
     */
    private String supportMaterialsDoc;

    private String supportMaterialsDocFileIds;

    /**
     * 预算支出审批表
     */
    private String expenditureApprovalDoc;

    private String expenditureApprovalDocFileIds;

    private String changeType;
    /**
     * 需求调查
     */
    private String demandSurveyDoc;

    private String demandSurveyDocFileIds;

    /**
     * 其他
     */
    private String otherDoc;

    private String otherDocFileIds;

    /**
     * 标段划分
     */
    private String secDivision;

    //计划进场时间
    private Date planEntryTime;

    //合同概算价
    private BigDecimal budgetMoney;

    //投资估算拆分表id
    private String ieId;

    //投资估算拆分详细表id
    private String ieDetailId;

    //投资概算拆分表id
    private String ibId;

    //投资概算拆分详细表id
    private String ibDetailId;

    //投资估算/概算 拆分类目
    private String isbdType;

    //投资估算/概算拆分排序
    private String isbdSort;

    //概算清单
    private List<SecInfoBudgetDeclareDtlDto> secInfoBudgetDeclareDtlDtoList;

    /**
     * 序号
     */
    private String secOrderNumber;

    /**
     * 标段计划类型
     */
    private String secCostType;

    /**
     * 标段分类
     */
    private String secSectionClassify;

    /**
     * 科目详情
     */
    private List<SecInfoSubjectResponse> secInfoSubjects;

    /**
     * 排序
     */
    private String secSort;

    private String tenderName;

    private String secSubjectNameType;

    /**
     * 采购编号
     */
    private String purchaseNumber;

    private BigDecimal amount;

    private String unitName;

    private Date commentaryTime;

    private String unit;

    private String secParentPath;

    /**
     * 标段类型（,分隔）
     */
    private String secTypeStr;

    private String secEstimateSubject;

    /**
     * 变更阶段
     */
    private String secChangePhase;

    private Integer focusCount;

    public Integer getFocusCount() {
        return focusCount;
    }

    public void setFocusCount(Integer focusCount) {
        this.focusCount = focusCount;
    }

    public String getSecChangePhase() {
        return secChangePhase;
    }

    public void setSecChangePhase(String secChangePhase) {
        this.secChangePhase = secChangePhase;
    }

    public String getSecEstimateSubject() {
        return secEstimateSubject;
    }

    public void setSecEstimateSubject(String secEstimateSubject) {
        this.secEstimateSubject = secEstimateSubject;
    }

    public String getSecTypeStr() {
        return secTypeStr;
    }

    public void setSecTypeStr(String secTypeStr) {
        this.secTypeStr = secTypeStr;
    }

    public String getSecParentPath() {
        return secParentPath;
    }

    public void setSecParentPath(String secParentPath) {
        this.secParentPath = secParentPath;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getCommentaryTime() {
        return commentaryTime;
    }

    public void setCommentaryTime(Date commentaryTime) {
        this.commentaryTime = commentaryTime;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    public String getTenderName() {
        return tenderName;
    }

    public void setTenderName(String tenderName) {
        this.tenderName = tenderName;
    }

    public String getSecSubjectNameType() {
        return secSubjectNameType;
    }

    public void setSecSubjectNameType(String secSubjectNameType) {
        this.secSubjectNameType = secSubjectNameType;
    }

    public String getSecSort() {
        return secSort;
    }

    public void setSecSort(String secSort) {
        this.secSort = secSort;
    }

    public List<SecInfoSubjectResponse> getSecInfoSubjects() {
        return secInfoSubjects;
    }

    public void setSecInfoSubjects(List<SecInfoSubjectResponse> secInfoSubjects) {
        this.secInfoSubjects = secInfoSubjects;
    }

    public String getSecOrderNumber() {
        return secOrderNumber;
    }

    public void setSecOrderNumber(String secOrderNumber) {
        this.secOrderNumber = secOrderNumber;
    }

    public String getSecCostType() {
        return secCostType;
    }

    public void setSecCostType(String secCostType) {
        this.secCostType = secCostType;
    }

    public String getSecSectionClassify() {
        return secSectionClassify;
    }

    public void setSecSectionClassify(String secSectionClassify) {
        this.secSectionClassify = secSectionClassify;
    }

    public Double getContractEvaluateAmountRateStart() {
        return contractEvaluateAmountRateStart;
    }

    public void setContractEvaluateAmountRateStart(Double contractEvaluateAmountRateStart) {
        this.contractEvaluateAmountRateStart = contractEvaluateAmountRateStart;
    }

    public Double getContractEvaluateAmountRateEnd() {
        return contractEvaluateAmountRateEnd;
    }

    public void setContractEvaluateAmountRateEnd(Double contractEvaluateAmountRateEnd) {
        this.contractEvaluateAmountRateEnd = contractEvaluateAmountRateEnd;
    }

    public Date getMakeBiddingTime() {
        return makeBiddingTime;
    }

    public void setMakeBiddingTime(Date makeBiddingTime) {
        this.makeBiddingTime = makeBiddingTime;
    }

    public Date getStartBiddingTime() {
        return startBiddingTime;
    }

    public void setStartBiddingTime(Date startBiddingTime) {
        this.startBiddingTime = startBiddingTime;
    }

    public String getStartBiddingPlace() {
        return startBiddingPlace;
    }

    public void setStartBiddingPlace(String startBiddingPlace) {
        this.startBiddingPlace = startBiddingPlace;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public String getSecParentId() {
        return secParentId;
    }

    public void setSecParentId(String secParentId) {
        this.secParentId = secParentId;
    }

    public Integer getSecYear() {
        return secYear;
    }

    public void setSecYear(Integer secYear) {
        this.secYear = secYear;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
    }

    public String getSecConType() {
        return secConType;
    }

    public void setSecConType(String secConType) {
        this.secConType = secConType;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public BigDecimal getSecMoney() {
        return secMoney;
    }

    public void setSecMoney(BigDecimal secMoney) {
        this.secMoney = secMoney;
    }

    public Date getSecStartTime() {
        return secStartTime;
    }

    public void setSecStartTime(Date secStartTime) {
        this.secStartTime = secStartTime;
    }

    public Date getSecEndTime() {
        return secEndTime;
    }

    public void setSecEndTime(Date secEndTime) {
        this.secEndTime = secEndTime;
    }

    public String getSecRemark() {
        return secRemark;
    }

    public void setSecRemark(String secRemark) {
        this.secRemark = secRemark;
    }

    public Date getSecFlowTime() {
        return secFlowTime;
    }

    public void setSecFlowTime(Date secFlowTime) {
        this.secFlowTime = secFlowTime;
    }

    public Integer getSecFlowState() {
        return secFlowState;
    }

    public void setSecFlowState(Integer secFlowState) {
        this.secFlowState = secFlowState;
    }

    public String getSecCreateUid() {
        return secCreateUid;
    }

    public void setSecCreateUid(String secCreateUid) {
        this.secCreateUid = secCreateUid;
    }

    public String getSecCreateUname() {
        return secCreateUname;
    }

    public void setSecCreateUname(String secCreateUname) {
        this.secCreateUname = secCreateUname;
    }

    public Date getSecCreateTime() {
        return secCreateTime;
    }

    public void setSecCreateTime(Date secCreateTime) {
        this.secCreateTime = secCreateTime;
    }

    public String getSecPid() {
        return secPid;
    }

    public void setSecPid(String secPid) {
        this.secPid = secPid;
    }

    public String getSecTenderee() {
        return secTenderee;
    }

    public void setSecTenderee(String secTenderee) {
        this.secTenderee = secTenderee;
    }

    public String getSecFeatures() {
        return secFeatures;
    }

    public void setSecFeatures(String secFeatures) {
        this.secFeatures = secFeatures;
    }

    public String getSecPlace() {
        return secPlace;
    }

    public void setSecPlace(String secPlace) {
        this.secPlace = secPlace;
    }

    public String getSecScale() {
        return secScale;
    }

    public void setSecScale(String secScale) {
        this.secScale = secScale;
    }

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }

    public Integer getBpState() {
        return bpState;
    }

    public void setBpState(Integer bpState) {
        this.bpState = bpState;
    }

    public Integer getWnState() {
        return wnState;
    }

    public void setWnState(Integer wnState) {
        this.wnState = wnState;
    }

    public String getWnId() {
        return wnId;
    }

    public void setWnId(String wnId) {
        this.wnId = wnId;
    }

    public BigDecimal getSecRate() {
        return secRate;
    }

    public void setSecRate(BigDecimal secRate) {
        this.secRate = secRate;
    }

    public String getSecOtherValuation() {
        return secOtherValuation;
    }

    public void setSecOtherValuation(String secOtherValuation) {
        this.secOtherValuation = secOtherValuation;
    }

    public String getSecDeterminType() {
        return secDeterminType;
    }

    public void setSecDeterminType(String secDeterminType) {
        this.secDeterminType = secDeterminType;
    }

    public String getSecOrganizeForm() {
        return secOrganizeForm;
    }

    public void setSecOrganizeForm(String secOrganizeForm) {
        this.secOrganizeForm = secOrganizeForm;
    }

    public String getSecTradingPlace() {
        return secTradingPlace;
    }

    public void setSecTradingPlace(String secTradingPlace) {
        this.secTradingPlace = secTradingPlace;
    }

    public Integer getSecBiddingCycle() {
        return secBiddingCycle;
    }

    public void setSecBiddingCycle(Integer secBiddingCycle) {
        this.secBiddingCycle = secBiddingCycle;
    }

    public String getSecApprovalTitle() {
        return secApprovalTitle;
    }

    public void setSecApprovalTitle(String secApprovalTitle) {
        this.secApprovalTitle = secApprovalTitle;
    }

    public String getSecApprovalNum() {
        return secApprovalNum;
    }

    public void setSecApprovalNum(String secApprovalNum) {
        this.secApprovalNum = secApprovalNum;
    }

    public Date getSecApprovalTime() {
        return secApprovalTime;
    }

    public void setSecApprovalTime(Date secApprovalTime) {
        this.secApprovalTime = secApprovalTime;
    }

    public String getSecApprovalFile() {
        return secApprovalFile;
    }

    public void setSecApprovalFile(String secApprovalFile) {
        this.secApprovalFile = secApprovalFile;
    }


    public String getSecPricingMethod() {
        return secPricingMethod;
    }

    public void setSecPricingMethod(String secPricingMethod) {
        this.secPricingMethod = secPricingMethod;
    }

    public String getSecPurchaseType() {
        return secPurchaseType;
    }

    public void setSecPurchaseType(String secPurchaseType) {
        this.secPurchaseType = secPurchaseType;
    }

    public String getSecEvalMethod() {
        return secEvalMethod;
    }

    public void setSecEvalMethod(String secEvalMethod) {
        this.secEvalMethod = secEvalMethod;
    }

    public Boolean getSecEvaluateSeparation() {
        return secEvaluateSeparation;
    }

    public void setSecEvaluateSeparation(Boolean secEvaluateSeparation) {
        this.secEvaluateSeparation = secEvaluateSeparation;
    }

    public Integer getSecDuration() {
        return secDuration;
    }

    public void setSecDuration(Integer secDuration) {
        this.secDuration = secDuration;
    }

    public String getSecQualiReview() {
        return secQualiReview;
    }

    public void setSecQualiReview(String secQualiReview) {
        this.secQualiReview = secQualiReview;
    }

    public String getSecInvestorsQuali() {
        return secInvestorsQuali;
    }

    public void setSecInvestorsQuali(String secInvestorsQuali) {
        this.secInvestorsQuali = secInvestorsQuali;
    }

    public String getSecValuationFile() {
        return secValuationFile;
    }

    public void setSecValuationFile(String secValuationFile) {
        this.secValuationFile = secValuationFile;
    }

    public Date getSecBidTime() {
        return secBidTime;
    }

    public void setSecBidTime(Date secBidTime) {
        this.secBidTime = secBidTime;
    }

    public String getSecLoaction() {
        return secLoaction;
    }

    public void setSecLoaction(String secLoaction) {
        this.secLoaction = secLoaction;
    }

    public String getSecAgency() {
        return secAgency;
    }

    public void setSecAgency(String secAgency) {
        this.secAgency = secAgency;
    }

    public String getSecContractingDepartment() {
        return secContractingDepartment;
    }

    public void setSecContractingDepartment(String secContractingDepartment) {
        this.secContractingDepartment = secContractingDepartment;
    }

    public Date getSecPlanOpenTime() {
        return secPlanOpenTime;
    }

    public void setSecPlanOpenTime(Date secPlanOpenTime) {
        this.secPlanOpenTime = secPlanOpenTime;
    }

    public String getSecContractingType() {
        return secContractingType;
    }

    public void setSecContractingType(String secContractingType) {
        this.secContractingType = secContractingType;
    }

    public Boolean getSecConsortium() {
        return secConsortium;
    }

    public void setSecConsortium(Boolean secConsortium) {
        this.secConsortium = secConsortium;
    }

    public Boolean getSecSubpackage() {
        return secSubpackage;
    }

    public void setSecSubpackage(Boolean secSubpackage) {
        this.secSubpackage = secSubpackage;
    }

    public Boolean getSecEnterprise() {
        return secEnterprise;
    }

    public void setSecEnterprise(Boolean secEnterprise) {
        this.secEnterprise = secEnterprise;
    }

    public Boolean getSecImport() {
        return secImport;
    }

    public void setSecImport(Boolean secImport) {
        this.secImport = secImport;
    }

    public String getSecProjectNature() {
        return secProjectNature;
    }

    public void setSecProjectNature(String secProjectNature) {
        this.secProjectNature = secProjectNature;
    }

    public String getSecSourceFund() {
        return secSourceFund;
    }

    public void setSecSourceFund(String secSourceFund) {
        this.secSourceFund = secSourceFund;
    }

    public String getSecFundsReceived() {
        return secFundsReceived;
    }

    public void setSecFundsReceived(String secFundsReceived) {
        this.secFundsReceived = secFundsReceived;
    }

    public String getSecSubscriber() {
        return secSubscriber;
    }

    public void setSecSubscriber(String secSubscriber) {
        this.secSubscriber = secSubscriber;
    }

    public String getSecContacts() {
        return secContacts;
    }

    public void setSecContacts(String secContacts) {
        this.secContacts = secContacts;
    }

    public String getSecContactsPhone() {
        return secContactsPhone;
    }

    public void setSecContactsPhone(String secContactsPhone) {
        this.secContactsPhone = secContactsPhone;
    }

    public String getSecContactsTel() {
        return secContactsTel;
    }

    public void setSecContactsTel(String secContactsTel) {
        this.secContactsTel = secContactsTel;
    }

    public String getSecContactsEmail() {
        return secContactsEmail;
    }

    public void setSecContactsEmail(String secContactsEmail) {
        this.secContactsEmail = secContactsEmail;
    }

    public String getSecDelegate() {
        return secDelegate;
    }

    public void setSecDelegate(String secDelegate) {
        this.secDelegate = secDelegate;
    }

    public BigDecimal getSecInternetDay() {
        return secInternetDay;
    }

    public void setSecInternetDay(BigDecimal secInternetDay) {
        this.secInternetDay = secInternetDay;
    }

    public Boolean getSecAnnounce() {
        return secAnnounce;
    }

    public void setSecAnnounce(Boolean secAnnounce) {
        this.secAnnounce = secAnnounce;
    }

    public BigDecimal getSecSigningDay() {
        return secSigningDay;
    }

    public void setSecSigningDay(BigDecimal secSigningDay) {
        this.secSigningDay = secSigningDay;
    }

    public String getSecPayMethod() {
        return secPayMethod;
    }

    public void setSecPayMethod(String secPayMethod) {
        this.secPayMethod = secPayMethod;
    }

    public BigDecimal getSecArrivalDay() {
        return secArrivalDay;
    }

    public void setSecArrivalDay(BigDecimal secArrivalDay) {
        this.secArrivalDay = secArrivalDay;
    }

    public Boolean getSecSingleSource() {
        return secSingleSource;
    }

    public void setSecSingleSource(Boolean secSingleSource) {
        this.secSingleSource = secSingleSource;
    }

    public String getSecContent() {
        return secContent;
    }

    public void setSecContent(String secContent) {
        this.secContent = secContent;
    }

    public BigDecimal getSecGsAmt() {
        return secGsAmt;
    }

    public void setSecGsAmt(BigDecimal secGsAmt) {
        this.secGsAmt = secGsAmt;
    }

    public String getSecTendereeName() {
        return secTendereeName;
    }

    public void setSecTendereeName(String secTendereeName) {
        this.secTendereeName = secTendereeName;
    }

    public String getSecAgencyCname() {
        return secAgencyCname;
    }

    public void setSecAgencyCname(String secAgencyCname) {
        this.secAgencyCname = secAgencyCname;
    }

    public String getSecProOwn() {
        return secProOwn;
    }

    public void setSecProOwn(String secProOwn) {
        this.secProOwn = secProOwn;
    }

    public String getSecEntryCode() {
        return secEntryCode;
    }

    public void setSecEntryCode(String secEntryCode) {
        this.secEntryCode = secEntryCode;
    }

    public String getSecEntryName() {
        return secEntryName;
    }

    public void setSecEntryName(String secEntryName) {
        this.secEntryName = secEntryName;
    }

    public String getSecCatalogName() {
        return secCatalogName;
    }

    public void setSecCatalogName(String secCatalogName) {
        this.secCatalogName = secCatalogName;
    }

    public String getSecContactsName() {
        return secContactsName;
    }

    public void setSecContactsName(String secContactsName) {
        this.secContactsName = secContactsName;
    }

    public String getSecDelegateName() {
        return secDelegateName;
    }

    public void setSecDelegateName(String secDelegateName) {
        this.secDelegateName = secDelegateName;
    }

    public String getSecReceAddr() {
        return secReceAddr;
    }

    public void setSecReceAddr(String secReceAddr) {
        this.secReceAddr = secReceAddr;
    }

    public String getSecSupplier() {
        return secSupplier;
    }

    public void setSecSupplier(String secSupplier) {
        this.secSupplier = secSupplier;
    }

    public String getSecSupplierName() {
        return secSupplierName;
    }

    public void setSecSupplierName(String secSupplierName) {
        this.secSupplierName = secSupplierName;
    }

    public String getSecSupplierPhone() {
        return secSupplierPhone;
    }

    public void setSecSupplierPhone(String secSupplierPhone) {
        this.secSupplierPhone = secSupplierPhone;
    }

    public String getSecSupplierTel() {
        return secSupplierTel;
    }

    public void setSecSupplierTel(String secSupplierTel) {
        this.secSupplierTel = secSupplierTel;
    }

    public String getSecSupplierEmail() {
        return secSupplierEmail;
    }

    public void setSecSupplierEmail(String secSupplierEmail) {
        this.secSupplierEmail = secSupplierEmail;
    }

    public String getSecSupplierAddr() {
        return secSupplierAddr;
    }

    public void setSecSupplierAddr(String secSupplierAddr) {
        this.secSupplierAddr = secSupplierAddr;
    }

    public String getSecConstrAddr() {
        return secConstrAddr;
    }

    public void setSecConstrAddr(String secConstrAddr) {
        this.secConstrAddr = secConstrAddr;
    }

    public BigDecimal getSecConstrDay() {
        return secConstrDay;
    }

    public void setSecConstrDay(BigDecimal secConstrDay) {
        this.secConstrDay = secConstrDay;
    }

    public String getSecCatalog() {
        return secCatalog;
    }

    public void setSecCatalog(String secCatalog) {
        this.secCatalog = secCatalog;
    }

    public String getSecProCategory() {
        return secProCategory;
    }

    public void setSecProCategory(String secProCategory) {
        this.secProCategory = secProCategory;
    }

    public String getSecProofFile() {
        return secProofFile;
    }

    public void setSecProofFile(String secProofFile) {
        this.secProofFile = secProofFile;
    }

    public String getSecBlankFile() {
        return secBlankFile;
    }

    public void setSecBlankFile(String secBlankFile) {
        this.secBlankFile = secBlankFile;
    }

    public String getSecOtherFile() {
        return secOtherFile;
    }

    public void setSecOtherFile(String secOtherFile) {
        this.secOtherFile = secOtherFile;
    }

    public String getSecOtherImg() {
        return secOtherImg;
    }

    public void setSecOtherImg(String secOtherImg) {
        this.secOtherImg = secOtherImg;
    }

    public String getSecArrival() {
        return secArrival;
    }

    public void setSecArrival(String secArrival) {
        this.secArrival = secArrival;
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

    public String getSecContractingDepartmentName() {
        return secContractingDepartmentName;
    }

    public void setSecContractingDepartmentName(String secContractingDepartmentName) {
        this.secContractingDepartmentName = secContractingDepartmentName;
    }

    public String getSecAgencyName() {
        return secAgencyName;
    }

    public void setSecAgencyName(String secAgencyName) {
        this.secAgencyName = secAgencyName;
    }

    public String getSecAgencyAbbName() {
        return secAgencyAbbName;
    }

    public void setSecAgencyAbbName(String secAgencyAbbName) {
        this.secAgencyAbbName = secAgencyAbbName;
    }

    public String getTendereeName() {
        return tendereeName;
    }

    public void setTendereeName(String tendereeName) {
        this.tendereeName = tendereeName;
    }

    public String getTendereeAbbName() {
        return tendereeAbbName;
    }

    public void setTendereeAbbName(String tendereeAbbName) {
        this.tendereeAbbName = tendereeAbbName;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public List<SecInfoBudgetModel> getSecInfoBudgets() {
        return secInfoBudgets;
    }

    public void setSecInfoBudgets(List<SecInfoBudgetModel> secInfoBudgets) {
        this.secInfoBudgets = secInfoBudgets;
    }

    public String getTendereeNameHistory() {
        return tendereeNameHistory;
    }

    public void setTendereeNameHistory(String tendereeNameHistory) {
        this.tendereeNameHistory = tendereeNameHistory;
    }

    public List<SecSourcesFundsModel> getSourcesFundsModels() {
        return sourcesFundsModels;
    }

    public void setSourcesFundsModels(List<SecSourcesFundsModel> sourcesFundsModels) {
        this.sourcesFundsModels = sourcesFundsModels;
    }

    public List<SecProcurementDetailsDTO> getSecProcurementDetails() {
        return secProcurementDetails;
    }

    public void setSecProcurementDetails(List<SecProcurementDetailsDTO> secProcurementDetails) {
        this.secProcurementDetails = secProcurementDetails;
    }

    public List<SecFundsDTO> getSecFunds() {
        return secFunds;
    }

    public void setSecFunds(List<SecFundsDTO> secFunds) {
        this.secFunds = secFunds;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpCompetentDepartmentName() {
        return pCompetentDepartmentName;
    }

    public void setpCompetentDepartmentName(String pCompetentDepartmentName) {
        this.pCompetentDepartmentName = pCompetentDepartmentName;
    }

    public int getUnalterable() {
        return unalterable;
    }

    public void setUnalterable(int unalterable) {
        this.unalterable = unalterable;
    }

    public int getNodeTotal() {
        return nodeTotal;
    }

    public void setNodeTotal(int nodeTotal) {
        this.nodeTotal = nodeTotal;
    }

    public int getNodeComplete() {
        return nodeComplete;
    }

    public void setNodeComplete(int nodeComplete) {
        this.nodeComplete = nodeComplete;
    }

    public int getNodeApproval() {
        return nodeApproval;
    }

    public void setNodeApproval(int nodeApproval) {
        this.nodeApproval = nodeApproval;
    }

    public int getNodeUnStart() {
        return nodeUnStart;
    }

    public void setNodeUnStart(int nodeUnStart) {
        this.nodeUnStart = nodeUnStart;
    }

    public Map<String, Object> getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(Map<String, Object> currentNode) {
        this.currentNode = currentNode;
    }

    public String getServicePeriod() {
        return servicePeriod;
    }

    public void setServicePeriod(String servicePeriod) {
        this.servicePeriod = servicePeriod;
    }

    public String getProcurementDes() {
        return procurementDes;
    }

    public void setProcurementDes(String procurementDes) {
        this.procurementDes = procurementDes;
    }

    public String getProcurementPurpose() {
        return procurementPurpose;
    }

    public void setProcurementPurpose(String procurementPurpose) {
        this.procurementPurpose = procurementPurpose;
    }

    public String getAnnualProcurementPlan() {
        return annualProcurementPlan;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public void setAnnualProcurementPlan(String annualProcurementPlan) {
        this.annualProcurementPlan = annualProcurementPlan;
    }

    public String getIntendedPublicDate() {
        return intendedPublicDate;
    }

    public void setIntendedPublicDate(String intendedPublicDate) {
        this.intendedPublicDate = intendedPublicDate;
    }

    public Date getPlannedProcurementTime() {
        return plannedProcurementTime;
    }

    public void setPlannedProcurementTime(Date plannedProcurementTime) {
        this.plannedProcurementTime = plannedProcurementTime;
    }

    public String getBudgetDeterminationMethod() {
        return budgetDeterminationMethod;
    }

    public void setBudgetDeterminationMethod(String budgetDeterminationMethod) {
        this.budgetDeterminationMethod = budgetDeterminationMethod;
    }

    public String getFundingImplementationStatus() {
        return fundingImplementationStatus;
    }

    public void setFundingImplementationStatus(String fundingImplementationStatus) {
        this.fundingImplementationStatus = fundingImplementationStatus;
    }

    public Date getProcurementRequirements() {
        return procurementRequirements;
    }

    public void setProcurementRequirements(Date procurementRequirements) {
        this.procurementRequirements = procurementRequirements;
    }

    public String getProjectSelfInspection() {
        return projectSelfInspection;
    }

    public void setProjectSelfInspection(String projectSelfInspection) {
        this.projectSelfInspection = projectSelfInspection;
    }

    public String getProjectExplanation() {
        return projectExplanation;
    }

    public void setProjectExplanation(String projectExplanation) {
        this.projectExplanation = projectExplanation;
    }

    public String getProcurementListDoc() {
        return procurementListDoc;
    }

    public void setProcurementListDoc(String procurementListDoc) {
        this.procurementListDoc = procurementListDoc;
    }

    public String getProcurementListDocFileIds() {
        return procurementListDocFileIds;
    }

    public void setProcurementListDocFileIds(String procurementListDocFileIds) {
        this.procurementListDocFileIds = procurementListDocFileIds;
    }

    public String getProcurementPlanDoc() {
        return procurementPlanDoc;
    }

    public void setProcurementPlanDoc(String procurementPlanDoc) {
        this.procurementPlanDoc = procurementPlanDoc;
    }

    public String getProcurementPlanDocFileIds() {
        return procurementPlanDocFileIds;
    }

    public void setProcurementPlanDocFileIds(String procurementPlanDocFileIds) {
        this.procurementPlanDocFileIds = procurementPlanDocFileIds;
    }

    public String getNoIntentionDiscloseDoc() {
        return noIntentionDiscloseDoc;
    }

    public void setNoIntentionDiscloseDoc(String noIntentionDiscloseDoc) {
        this.noIntentionDiscloseDoc = noIntentionDiscloseDoc;
    }

    public String getNoIntentionDiscloseDocFileIds() {
        return noIntentionDiscloseDocFileIds;
    }

    public void setNoIntentionDiscloseDocFileIds(String noIntentionDiscloseDocFileIds) {
        this.noIntentionDiscloseDocFileIds = noIntentionDiscloseDocFileIds;
    }

    public String getSupportMaterialsDoc() {
        return supportMaterialsDoc;
    }

    public void setSupportMaterialsDoc(String supportMaterialsDoc) {
        this.supportMaterialsDoc = supportMaterialsDoc;
    }

    public String getSupportMaterialsDocFileIds() {
        return supportMaterialsDocFileIds;
    }

    public void setSupportMaterialsDocFileIds(String supportMaterialsDocFileIds) {
        this.supportMaterialsDocFileIds = supportMaterialsDocFileIds;
    }

    public String getExpenditureApprovalDoc() {
        return expenditureApprovalDoc;
    }

    public void setExpenditureApprovalDoc(String expenditureApprovalDoc) {
        this.expenditureApprovalDoc = expenditureApprovalDoc;
    }

    public String getExpenditureApprovalDocFileIds() {
        return expenditureApprovalDocFileIds;
    }

    public void setExpenditureApprovalDocFileIds(String expenditureApprovalDocFileIds) {
        this.expenditureApprovalDocFileIds = expenditureApprovalDocFileIds;
    }

    public String getDemandSurveyDoc() {
        return demandSurveyDoc;
    }

    public void setDemandSurveyDoc(String demandSurveyDoc) {
        this.demandSurveyDoc = demandSurveyDoc;
    }

    public String getDemandSurveyDocFileIds() {
        return demandSurveyDocFileIds;
    }

    public void setDemandSurveyDocFileIds(String demandSurveyDocFileIds) {
        this.demandSurveyDocFileIds = demandSurveyDocFileIds;
    }

    public String getOtherDoc() {
        return otherDoc;
    }

    public void setOtherDoc(String otherDoc) {
        this.otherDoc = otherDoc;
    }

    public String getOtherDocFileIds() {
        return otherDocFileIds;
    }

    public void setOtherDocFileIds(String otherDocFileIds) {
        this.otherDocFileIds = otherDocFileIds;
    }

    public String getSecDivision() {
        return secDivision;
    }

    public void setSecDivision(String secDivision) {
        this.secDivision = secDivision;
    }

    public Date getPlanEntryTime() {
        return planEntryTime;
    }

    public void setPlanEntryTime(Date planEntryTime) {
        this.planEntryTime = planEntryTime;
    }

    public BigDecimal getBudgetMoney() {
        return budgetMoney;
    }

    public void setBudgetMoney(BigDecimal budgetMoney) {
        this.budgetMoney = budgetMoney;
    }

    public String getIeId() {
        return ieId;
    }

    public void setIeId(String ieId) {
        this.ieId = ieId;
    }

    public String getIeDetailId() {
        return ieDetailId;
    }

    public void setIeDetailId(String ieDetailId) {
        this.ieDetailId = ieDetailId;
    }

    public String getIbId() {
        return ibId;
    }

    public void setIbId(String ibId) {
        this.ibId = ibId;
    }

    public String getIbDetailId() {
        return ibDetailId;
    }

    public void setIbDetailId(String ibDetailId) {
        this.ibDetailId = ibDetailId;
    }

    public String getIsbdType() {
        return isbdType;
    }

    public void setIsbdType(String isbdType) {
        this.isbdType = isbdType;
    }

    public String getIsbdSort() {
        return isbdSort;
    }

    public void setIsbdSort(String isbdSort) {
        this.isbdSort = isbdSort;
    }

    public List<SecInfoBudgetDeclareDtlDto> getSecInfoBudgetDeclareDtlDtoList() {
        return secInfoBudgetDeclareDtlDtoList;
    }

    public void setSecInfoBudgetDeclareDtlDtoList(List<SecInfoBudgetDeclareDtlDto> secInfoBudgetDeclareDtlDtoList) {
        this.secInfoBudgetDeclareDtlDtoList = secInfoBudgetDeclareDtlDtoList;
    }
}
