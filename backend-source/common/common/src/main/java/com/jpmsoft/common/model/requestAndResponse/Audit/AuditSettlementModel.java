package com.jpmsoft.common.model.requestAndResponse.Audit;

import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算审计
 */
public class AuditSettlementModel extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -6669874599838806659L;
    @ProcessFields(type = ProcessFieldEnum.id)
    private String asId;
    private String pId;
    private String secId;
    private String conId;
    private BigDecimal asSettlementPrice;
    private String asCreateCid;
    private String asContactName;
    private String asContactTel;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String asCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String asCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date asCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer asState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date asStateTime;
    @FileFields(index = 1)
    private String asFileBidDoc;
    @FileFields(index = 2)
    private String asFileTenderDoc;
    @FileFields(index = 3)
    private String asFileContract;
    @FileFields(index = 4)
    private String asFileBidWin;
    @FileFields(index = 5)
    private String asFileCompletion;
    @FileFields(index = 6)
    private String asFileStatement;
    @FileFields(index = 7)
    private String asFileCalculation;
    @FileFields(index = 8)
    private String asFileOther;
    private String asRemark;
    private Date asStartDate;
    private Date asEndDate;
    @FileFields(index = 9)
    private String asAuditResult;
    private String asType;
    private Integer asNeedMoney;
    @FileFields(index = 10)
    private String asAssetFile;
    private String asContractor;

    private String conName;
    private String conCode;
    private String secName;
    private String secCode;
    private String cName;
    private String cNameHistory;
    private String cAbbName;
    @FileFields(index = 1,type = "asFileBidDocIds")
    private String asFileBidDocIds;
    @FileFields(index = 2,type = "asFileTenderDocIds")
    private String asFileTenderDocIds;
    @FileFields(index = 3,type = "asFileContractIds")
    private String asFileContractIds;
    @FileFields(index = 4,type = "asFileBidWinIds")
    private String asFileBidWinIds;
    @FileFields(index = 5,type = "asFileCompletionIds")
    private String asFileCompletionIds;
    @FileFields(index = 6,type = "asFileStatementIds")
    private String asFileStatementIds;
    @FileFields(index = 7,type = "asFileCalculationIds")
    private String asFileCalculationIds;
    @FileFields(index = 8,type = "asFileOtherIds")
    private String asFileOtherIds;
    @FileFields(index = 9,type = "asAuditResultIds")
    private String asAuditResultIds;
    @FileFields(index = 10,type = "asAssetFileIds")
    private String asAssetFileIds;
    private BigDecimal conMoneyTotal;
    private BigDecimal asConMoneyTotal;
    private BigDecimal asReviewTheAmount;
    private Date asDateOfReferral;
    private BigDecimal asAuditedAmount;
    private Date asDateOfApproval;
    private BigDecimal asDeductionAmount;
    private Float asReductionRate;
    private String asAuditUnit;
    private String asAuditUnitName;

    public String getAsId() {
        return asId;
    }

    public void setAsId(String asId) {
        this.asId = asId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public BigDecimal getAsSettlementPrice() {
        return asSettlementPrice;
    }

    public void setAsSettlementPrice(BigDecimal asSettlementPrice) {
        this.asSettlementPrice = asSettlementPrice;
    }

    public String getAsCreateCid() {
        return asCreateCid;
    }

    public void setAsCreateCid(String asCreateCid) {
        this.asCreateCid = asCreateCid;
    }

    public String getAsContactName() {
        return asContactName;
    }

    public void setAsContactName(String asContactName) {
        this.asContactName = asContactName;
    }

    public String getAsContactTel() {
        return asContactTel;
    }

    public void setAsContactTel(String asContactTel) {
        this.asContactTel = asContactTel;
    }

    public String getAsCreateUid() {
        return asCreateUid;
    }

    public void setAsCreateUid(String asCreateUid) {
        this.asCreateUid = asCreateUid;
    }

    public String getAsCreateUname() {
        return asCreateUname;
    }

    public void setAsCreateUname(String asCreateUname) {
        this.asCreateUname = asCreateUname;
    }

    public Date getAsCreateTime() {
        return asCreateTime;
    }

    public void setAsCreateTime(Date asCreateTime) {
        this.asCreateTime = asCreateTime;
    }

    public Integer getAsState() {
        return asState;
    }

    public void setAsState(Integer asState) {
        this.asState = asState;
    }

    public Date getAsStateTime() {
        return asStateTime;
    }

    public void setAsStateTime(Date asStateTime) {
        this.asStateTime = asStateTime;
    }

    public String getAsFileBidDoc() {
        return asFileBidDoc;
    }

    public void setAsFileBidDoc(String asFileBidDoc) {
        this.asFileBidDoc = asFileBidDoc;
    }

    public String getAsFileTenderDoc() {
        return asFileTenderDoc;
    }

    public void setAsFileTenderDoc(String asFileTenderDoc) {
        this.asFileTenderDoc = asFileTenderDoc;
    }

    public String getAsFileContract() {
        return asFileContract;
    }

    public void setAsFileContract(String asFileContract) {
        this.asFileContract = asFileContract;
    }

    public String getAsFileBidWin() {
        return asFileBidWin;
    }

    public void setAsFileBidWin(String asFileBidWin) {
        this.asFileBidWin = asFileBidWin;
    }

    public String getAsFileCompletion() {
        return asFileCompletion;
    }

    public void setAsFileCompletion(String asFileCompletion) {
        this.asFileCompletion = asFileCompletion;
    }

    public String getAsFileStatement() {
        return asFileStatement;
    }

    public void setAsFileStatement(String asFileStatement) {
        this.asFileStatement = asFileStatement;
    }

    public String getAsFileCalculation() {
        return asFileCalculation;
    }

    public void setAsFileCalculation(String asFileCalculation) {
        this.asFileCalculation = asFileCalculation;
    }

    public String getAsFileOther() {
        return asFileOther;
    }

    public void setAsFileOther(String asFileOther) {
        this.asFileOther = asFileOther;
    }

    public String getAsRemark() {
        return asRemark;
    }

    public void setAsRemark(String asRemark) {
        this.asRemark = asRemark;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcNameHistory() {
        return cNameHistory;
    }

    public void setcNameHistory(String cNameHistory) {
        this.cNameHistory = cNameHistory;
    }

    public String getcAbbName() {
        return cAbbName;
    }

    public void setcAbbName(String cAbbName) {
        this.cAbbName = cAbbName;
    }

    public String getAsFileBidDocIds() {
        return asFileBidDocIds;
    }

    public void setAsFileBidDocIds(String asFileBidDocIds) {
        this.asFileBidDocIds = asFileBidDocIds;
    }

    public String getAsFileTenderDocIds() {
        return asFileTenderDocIds;
    }

    public void setAsFileTenderDocIds(String asFileTenderDocIds) {
        this.asFileTenderDocIds = asFileTenderDocIds;
    }

    public String getAsFileContractIds() {
        return asFileContractIds;
    }

    public void setAsFileContractIds(String asFileContractIds) {
        this.asFileContractIds = asFileContractIds;
    }

    public String getAsFileBidWinIds() {
        return asFileBidWinIds;
    }

    public void setAsFileBidWinIds(String asFileBidWinIds) {
        this.asFileBidWinIds = asFileBidWinIds;
    }

    public String getAsFileCompletionIds() {
        return asFileCompletionIds;
    }

    public void setAsFileCompletionIds(String asFileCompletionIds) {
        this.asFileCompletionIds = asFileCompletionIds;
    }

    public String getAsFileStatementIds() {
        return asFileStatementIds;
    }

    public void setAsFileStatementIds(String asFileStatementIds) {
        this.asFileStatementIds = asFileStatementIds;
    }

    public String getAsFileCalculationIds() {
        return asFileCalculationIds;
    }

    public void setAsFileCalculationIds(String asFileCalculationIds) {
        this.asFileCalculationIds = asFileCalculationIds;
    }

    public String getAsFileOtherIds() {
        return asFileOtherIds;
    }

    public void setAsFileOtherIds(String asFileOtherIds) {
        this.asFileOtherIds = asFileOtherIds;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public BigDecimal getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(BigDecimal conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public Date getAsStartDate() {
        return asStartDate;
    }

    public void setAsStartDate(Date asStartDate) {
        this.asStartDate = asStartDate;
    }

    public Date getAsEndDate() {
        return asEndDate;
    }

    public void setAsEndDate(Date asEndDate) {
        this.asEndDate = asEndDate;
    }

    public String getAsAuditResult() {
        return asAuditResult;
    }

    public void setAsAuditResult(String asAuditResult) {
        this.asAuditResult = asAuditResult;
    }

    public String getAsAuditResultIds() {
        return asAuditResultIds;
    }

    public void setAsAuditResultIds(String asAuditResultIds) {
        this.asAuditResultIds = asAuditResultIds;
    }

    public String getAsContractor() {
        return asContractor;
    }

    public void setAsContractor(String asContractor) {
        this.asContractor = asContractor;
    }

    public String getAsAssetFileIds() {
        return asAssetFileIds;
    }

    public void setAsAssetFileIds(String asAssetFileIds) {
        this.asAssetFileIds = asAssetFileIds;
    }

    public String getAsType() {
        return asType;
    }

    public void setAsType(String asType) {
        this.asType = asType;
    }

    public Integer getAsNeedMoney() {
        return asNeedMoney;
    }

    public void setAsNeedMoney(Integer asNeedMoney) {
        this.asNeedMoney = asNeedMoney;
    }

    public String getAsAssetFile() {
        return asAssetFile;
    }

    public void setAsAssetFile(String asAssetFile) {
        this.asAssetFile = asAssetFile;
    }

    public BigDecimal getAsConMoneyTotal() {
        return asConMoneyTotal;
    }

    public void setAsConMoneyTotal(BigDecimal asConMoneyTotal) {
        this.asConMoneyTotal = asConMoneyTotal;
    }

    public BigDecimal getAsReviewTheAmount() {
        return asReviewTheAmount;
    }

    public void setAsReviewTheAmount(BigDecimal asReviewTheAmount) {
        this.asReviewTheAmount = asReviewTheAmount;
    }

    public Date getAsDateOfReferral() {
        return asDateOfReferral;
    }

    public void setAsDateOfReferral(Date asDateOfReferral) {
        this.asDateOfReferral = asDateOfReferral;
    }

    public BigDecimal getAsAuditedAmount() {
        return asAuditedAmount;
    }

    public void setAsAuditedAmount(BigDecimal asAuditedAmount) {
        this.asAuditedAmount = asAuditedAmount;
    }

    public Date getAsDateOfApproval() {
        return asDateOfApproval;
    }

    public void setAsDateOfApproval(Date asDateOfApproval) {
        this.asDateOfApproval = asDateOfApproval;
    }

    public BigDecimal getAsDeductionAmount() {
        return asDeductionAmount;
    }

    public void setAsDeductionAmount(BigDecimal asDeductionAmount) {
        this.asDeductionAmount = asDeductionAmount;
    }

    public Float getAsReductionRate() {
        return asReductionRate;
    }

    public void setAsReductionRate(Float asReductionRate) {
        this.asReductionRate = asReductionRate;
    }

    public String getAsAuditUnit() {
        return asAuditUnit;
    }

    public void setAsAuditUnit(String asAuditUnit) {
        this.asAuditUnit = asAuditUnit;
    }

    public String getAsAuditUnitName() {
        return asAuditUnitName;
    }

    public void setAsAuditUnitName(String asAuditUnitName) {
        this.asAuditUnitName = asAuditUnitName;
    }
}
