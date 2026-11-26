package com.jpmsoft.common.model.request.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 招标控制价
 */
public class SecControlPriceRequest extends BaseFileRequest {

    private String cpId;
    private String secId;
    private String cpType;
    private String cpPreparedBy;
    private BigDecimal cpPreparedPrice;
    private BigDecimal cpConstructPrice;
    private BigDecimal cpEconomicIndicator;
    private BigDecimal cpNonCompetitive;
    private String cpBoqDoc;
    private String cpConsultUnitDoc;
    private String cpCreateUid;
    private String cpCreateUname;
    private Date cpCreateTime;
    private Integer cpState;
    private Date cpStateTime;
    private String cpRemark;
    private String cpConsultUnitDocIds;
    private String cpBoqDocIds;
    private String cpFilingReceipt;
    private String cpFilingReceiptIds;
    private BigDecimal cpBiddingPrice;
    private BigDecimal cpBudgetPrice;
    private String cpAuditUnit;
    private BigDecimal cpAuditPrice;
    private BigDecimal cpRate;
    private String cpDescribe;

    private String cpConMode;
    private String cpEnterprise;
    private String cpPmRequire;
    private String cpAuditCmp;
    private String cpReputation;
    //region文件
    private String cpSelfPurchase;
    private String cpFocusPurchase;
    private String cpPurchaseDep;
    private String cpSubmitCensor;
    private String cpReviewComments;
    private String cpFinalOffer;
    private String cpSupplement;
    private String cpModify;
    private String cpDelegation;
    private String cpDrawingFile;
    private String cpCompileFile;
    private String cpExamineFile;
    private String cpQuotationCeiling;
    private String cpPublicityTable;
    private String cpApprovalMatter;

    private String cpPublicityTableIds;
    private String cpSelfPurchaseIds;
    private String cpFocusPurchaseIds;
    private String cpPurchaseDepIds;
    private String cpSubmitCensorIds;
    private String cpReviewCommentsIds;
    private String cpFinalOfferIds;
    private String cpSupplementIds;
    private String cpModifyIds;
    private String cpDelegationIds;
    private String cpDrawingFileIds;
    private String cpCompileFileIds;
    private String cpExamineFileIds;
    //endregion
    private String secName;
    private String cpPreparedByName;
    private String cpAuditUnitName;
    private String cpConsultingId;
    //造价咨询公司
    private String costCompany;
    //造价咨询公司控制价
    private String costCompanyPrice;
    //会审意见
    private String cpReviewOpinion;
    //预估合同金额
    private BigDecimal cpConAmount;


    private String cpConsultingName;
    //编制单位编制价（范围筛选）、
    private List<String> cpPreparedPrices;
    //建设单位审核造价（范围筛选）、
    private List<String> cpConstructPrices;
    //总建筑面积经济指标（范围筛选）、
    private List<String> cpEconomicIndicators;
    //不可竞争费合计（范围筛选）、
    private List<String> cpNonCompetitives;
    // 创建时间（范围筛选）、
    private List<String> cpCreateTimes;
    private List<SecControlCpyDto> secControlCpys;

    private List<SecProjectResponse> secProjects;

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getCpType() {
        return cpType;
    }

    public void setCpType(String cpType) {
        this.cpType = cpType;
    }

    public String getCpPreparedBy() {
        return cpPreparedBy;
    }

    public void setCpPreparedBy(String cpPreparedBy) {
        this.cpPreparedBy = cpPreparedBy;
    }

    public BigDecimal getCpPreparedPrice() {
        return cpPreparedPrice;
    }

    public void setCpPreparedPrice(BigDecimal cpPreparedPrice) {
        this.cpPreparedPrice = cpPreparedPrice;
    }

    public BigDecimal getCpConstructPrice() {
        return cpConstructPrice;
    }

    public void setCpConstructPrice(BigDecimal cpConstructPrice) {
        this.cpConstructPrice = cpConstructPrice;
    }

    public BigDecimal getCpEconomicIndicator() {
        return cpEconomicIndicator;
    }

    public void setCpEconomicIndicator(BigDecimal cpEconomicIndicator) {
        this.cpEconomicIndicator = cpEconomicIndicator;
    }

    public BigDecimal getCpNonCompetitive() {
        return cpNonCompetitive;
    }

    public void setCpNonCompetitive(BigDecimal cpNonCompetitive) {
        this.cpNonCompetitive = cpNonCompetitive;
    }

    public String getCpBoqDoc() {
        return cpBoqDoc;
    }

    public void setCpBoqDoc(String cpBoqDoc) {
        this.cpBoqDoc = cpBoqDoc;
    }

    public String getCpConsultUnitDoc() {
        return cpConsultUnitDoc;
    }

    public void setCpConsultUnitDoc(String cpConsultUnitDoc) {
        this.cpConsultUnitDoc = cpConsultUnitDoc;
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

    public Date getCpCreateTime() {
        return cpCreateTime;
    }

    public void setCpCreateTime(Date cpCreateTime) {
        this.cpCreateTime = cpCreateTime;
    }

    public Integer getCpState() {
        return cpState;
    }

    public void setCpState(Integer cpState) {
        this.cpState = cpState;
    }

    public Date getCpStateTime() {
        return cpStateTime;
    }

    public void setCpStateTime(Date cpStateTime) {
        this.cpStateTime = cpStateTime;
    }

    public String getCpRemark() {
        return cpRemark;
    }

    public void setCpRemark(String cpRemark) {
        this.cpRemark = cpRemark;
    }

    public String getCpConsultUnitDocIds() {
        return cpConsultUnitDocIds;
    }

    public void setCpConsultUnitDocIds(String cpConsultUnitDocIds) {
        this.cpConsultUnitDocIds = cpConsultUnitDocIds;
    }

    public String getCpBoqDocIds() {
        return cpBoqDocIds;
    }

    public void setCpBoqDocIds(String cpBoqDocIds) {
        this.cpBoqDocIds = cpBoqDocIds;
    }

    public String getCpFilingReceipt() {
        return cpFilingReceipt;
    }

    public void setCpFilingReceipt(String cpFilingReceipt) {
        this.cpFilingReceipt = cpFilingReceipt;
    }

    public String getCpFilingReceiptIds() {
        return cpFilingReceiptIds;
    }

    public void setCpFilingReceiptIds(String cpFilingReceiptIds) {
        this.cpFilingReceiptIds = cpFilingReceiptIds;
    }

    public BigDecimal getCpBiddingPrice() {
        return cpBiddingPrice;
    }

    public void setCpBiddingPrice(BigDecimal cpBiddingPrice) {
        this.cpBiddingPrice = cpBiddingPrice;
    }

    public BigDecimal getCpBudgetPrice() {
        return cpBudgetPrice;
    }

    public void setCpBudgetPrice(BigDecimal cpBudgetPrice) {
        this.cpBudgetPrice = cpBudgetPrice;
    }

    public String getCpAuditUnit() {
        return cpAuditUnit;
    }

    public void setCpAuditUnit(String cpAuditUnit) {
        this.cpAuditUnit = cpAuditUnit;
    }

    public BigDecimal getCpAuditPrice() {
        return cpAuditPrice;
    }

    public void setCpAuditPrice(BigDecimal cpAuditPrice) {
        this.cpAuditPrice = cpAuditPrice;
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

    public String getCpConMode() {
        return cpConMode;
    }

    public void setCpConMode(String cpConMode) {
        this.cpConMode = cpConMode;
    }

    public String getCpEnterprise() {
        return cpEnterprise;
    }

    public void setCpEnterprise(String cpEnterprise) {
        this.cpEnterprise = cpEnterprise;
    }

    public String getCpPmRequire() {
        return cpPmRequire;
    }

    public void setCpPmRequire(String cpPmRequire) {
        this.cpPmRequire = cpPmRequire;
    }

    public String getCpAuditCmp() {
        return cpAuditCmp;
    }

    public void setCpAuditCmp(String cpAuditCmp) {
        this.cpAuditCmp = cpAuditCmp;
    }

    public String getCpReputation() {
        return cpReputation;
    }

    public void setCpReputation(String cpReputation) {
        this.cpReputation = cpReputation;
    }

    public String getCpSelfPurchase() {
        return cpSelfPurchase;
    }

    public void setCpSelfPurchase(String cpSelfPurchase) {
        this.cpSelfPurchase = cpSelfPurchase;
    }

    public String getCpFocusPurchase() {
        return cpFocusPurchase;
    }

    public void setCpFocusPurchase(String cpFocusPurchase) {
        this.cpFocusPurchase = cpFocusPurchase;
    }

    public String getCpPurchaseDep() {
        return cpPurchaseDep;
    }

    public void setCpPurchaseDep(String cpPurchaseDep) {
        this.cpPurchaseDep = cpPurchaseDep;
    }

    public String getCpSubmitCensor() {
        return cpSubmitCensor;
    }

    public void setCpSubmitCensor(String cpSubmitCensor) {
        this.cpSubmitCensor = cpSubmitCensor;
    }

    public String getCpReviewComments() {
        return cpReviewComments;
    }

    public void setCpReviewComments(String cpReviewComments) {
        this.cpReviewComments = cpReviewComments;
    }

    public String getCpFinalOffer() {
        return cpFinalOffer;
    }

    public void setCpFinalOffer(String cpFinalOffer) {
        this.cpFinalOffer = cpFinalOffer;
    }

    public String getCpSupplement() {
        return cpSupplement;
    }

    public void setCpSupplement(String cpSupplement) {
        this.cpSupplement = cpSupplement;
    }

    public String getCpModify() {
        return cpModify;
    }

    public void setCpModify(String cpModify) {
        this.cpModify = cpModify;
    }

    public String getCpDelegation() {
        return cpDelegation;
    }

    public void setCpDelegation(String cpDelegation) {
        this.cpDelegation = cpDelegation;
    }

    public String getCpDrawingFile() {
        return cpDrawingFile;
    }

    public void setCpDrawingFile(String cpDrawingFile) {
        this.cpDrawingFile = cpDrawingFile;
    }

    public String getCpCompileFile() {
        return cpCompileFile;
    }

    public void setCpCompileFile(String cpCompileFile) {
        this.cpCompileFile = cpCompileFile;
    }

    public String getCpExamineFile() {
        return cpExamineFile;
    }

    public void setCpExamineFile(String cpExamineFile) {
        this.cpExamineFile = cpExamineFile;
    }

    public String getCpQuotationCeiling() {
        return cpQuotationCeiling;
    }

    public void setCpQuotationCeiling(String cpQuotationCeiling) {
        this.cpQuotationCeiling = cpQuotationCeiling;
    }

    public String getCpPublicityTable() {
        return cpPublicityTable;
    }

    public void setCpPublicityTable(String cpPublicityTable) {
        this.cpPublicityTable = cpPublicityTable;
    }

    public String getCpApprovalMatter() {
        return cpApprovalMatter;
    }

    public void setCpApprovalMatter(String cpApprovalMatter) {
        this.cpApprovalMatter = cpApprovalMatter;
    }

    public String getCpPublicityTableIds() {
        return cpPublicityTableIds;
    }

    public void setCpPublicityTableIds(String cpPublicityTableIds) {
        this.cpPublicityTableIds = cpPublicityTableIds;
    }

    public String getCpSelfPurchaseIds() {
        return cpSelfPurchaseIds;
    }

    public void setCpSelfPurchaseIds(String cpSelfPurchaseIds) {
        this.cpSelfPurchaseIds = cpSelfPurchaseIds;
    }

    public String getCpFocusPurchaseIds() {
        return cpFocusPurchaseIds;
    }

    public void setCpFocusPurchaseIds(String cpFocusPurchaseIds) {
        this.cpFocusPurchaseIds = cpFocusPurchaseIds;
    }

    public String getCpPurchaseDepIds() {
        return cpPurchaseDepIds;
    }

    public void setCpPurchaseDepIds(String cpPurchaseDepIds) {
        this.cpPurchaseDepIds = cpPurchaseDepIds;
    }

    public String getCpSubmitCensorIds() {
        return cpSubmitCensorIds;
    }

    public void setCpSubmitCensorIds(String cpSubmitCensorIds) {
        this.cpSubmitCensorIds = cpSubmitCensorIds;
    }

    public String getCpReviewCommentsIds() {
        return cpReviewCommentsIds;
    }

    public void setCpReviewCommentsIds(String cpReviewCommentsIds) {
        this.cpReviewCommentsIds = cpReviewCommentsIds;
    }

    public String getCpFinalOfferIds() {
        return cpFinalOfferIds;
    }

    public void setCpFinalOfferIds(String cpFinalOfferIds) {
        this.cpFinalOfferIds = cpFinalOfferIds;
    }

    public String getCpSupplementIds() {
        return cpSupplementIds;
    }

    public void setCpSupplementIds(String cpSupplementIds) {
        this.cpSupplementIds = cpSupplementIds;
    }

    public String getCpModifyIds() {
        return cpModifyIds;
    }

    public void setCpModifyIds(String cpModifyIds) {
        this.cpModifyIds = cpModifyIds;
    }

    public String getCpDelegationIds() {
        return cpDelegationIds;
    }

    public void setCpDelegationIds(String cpDelegationIds) {
        this.cpDelegationIds = cpDelegationIds;
    }

    public String getCpDrawingFileIds() {
        return cpDrawingFileIds;
    }

    public void setCpDrawingFileIds(String cpDrawingFileIds) {
        this.cpDrawingFileIds = cpDrawingFileIds;
    }

    public String getCpCompileFileIds() {
        return cpCompileFileIds;
    }

    public void setCpCompileFileIds(String cpCompileFileIds) {
        this.cpCompileFileIds = cpCompileFileIds;
    }

    public String getCpExamineFileIds() {
        return cpExamineFileIds;
    }

    public void setCpExamineFileIds(String cpExamineFileIds) {
        this.cpExamineFileIds = cpExamineFileIds;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getCpPreparedByName() {
        return cpPreparedByName;
    }

    public void setCpPreparedByName(String cpPreparedByName) {
        this.cpPreparedByName = cpPreparedByName;
    }

    public String getCpAuditUnitName() {
        return cpAuditUnitName;
    }

    public void setCpAuditUnitName(String cpAuditUnitName) {
        this.cpAuditUnitName = cpAuditUnitName;
    }

    public String getCpConsultingId() {
        return cpConsultingId;
    }

    public void setCpConsultingId(String cpConsultingId) {
        this.cpConsultingId = cpConsultingId;
    }

    public String getCostCompany() {
        return costCompany;
    }

    public void setCostCompany(String costCompany) {
        this.costCompany = costCompany;
    }

    public String getCostCompanyPrice() {
        return costCompanyPrice;
    }

    public void setCostCompanyPrice(String costCompanyPrice) {
        this.costCompanyPrice = costCompanyPrice;
    }

    public String getCpReviewOpinion() {
        return cpReviewOpinion;
    }

    public void setCpReviewOpinion(String cpReviewOpinion) {
        this.cpReviewOpinion = cpReviewOpinion;
    }

    public BigDecimal getCpConAmount() {
        return cpConAmount;
    }

    public void setCpConAmount(BigDecimal cpConAmount) {
        this.cpConAmount = cpConAmount;
    }

    public String getCpConsultingName() {
        return cpConsultingName;
    }

    public void setCpConsultingName(String cpConsultingName) {
        this.cpConsultingName = cpConsultingName;
    }

    public List<String> getCpPreparedPrices() {
        return cpPreparedPrices;
    }

    public void setCpPreparedPrices(List<String> cpPreparedPrices) {
        this.cpPreparedPrices = cpPreparedPrices;
    }

    public List<String> getCpConstructPrices() {
        return cpConstructPrices;
    }

    public void setCpConstructPrices(List<String> cpConstructPrices) {
        this.cpConstructPrices = cpConstructPrices;
    }

    public List<String> getCpEconomicIndicators() {
        return cpEconomicIndicators;
    }

    public void setCpEconomicIndicators(List<String> cpEconomicIndicators) {
        this.cpEconomicIndicators = cpEconomicIndicators;
    }

    public List<String> getCpNonCompetitives() {
        return cpNonCompetitives;
    }

    public void setCpNonCompetitives(List<String> cpNonCompetitives) {
        this.cpNonCompetitives = cpNonCompetitives;
    }

    public List<String> getCpCreateTimes() {
        return cpCreateTimes;
    }

    public void setCpCreateTimes(List<String> cpCreateTimes) {
        this.cpCreateTimes = cpCreateTimes;
    }

    public List<SecControlCpyDto> getSecControlCpys() {
        return secControlCpys;
    }

    public void setSecControlCpys(List<SecControlCpyDto> secControlCpys) {
        this.secControlCpys = secControlCpys;
    }
}
