package com.jpmsoft.common.model.response.InvestmentControl;

import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.InvBudgetDeclareDtlModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 概算申报
 */
public class InvBudgetDeclareResponse extends ProjectCommonRes {

    private String bdId;
    private String bdPreparedBy;
    private String bdAuditUnit;
    private BigDecimal bdMoney;
    private String bdRemark;
    private String bdCreateUid;
    private String bdCreateUname;
    private Date bdCreateTime;
    private Integer bdState;
    private Date bdStateTime;
    private String pId;
    private BigDecimal bdAuditAmt;
    private String bdSplitForm;
    private Integer bdSplitType;
    private String bdApprovalTitle;
    private String bdApprovalNum;
    private Date bdApprovalTime;
    private String bdApprovalFile;
    private String bdApprovalUnit;
    private BigDecimal bdCoverArea;
    private BigDecimal bdBuildArea;
    private String bdType;
    /*************************/
    private String bdPreparedByName;
    private String bdAuditUnitName;
    private String bdSplitFormName;
    private List<InvBudgetDeclareDtlModel> invBudgetDeclareDtls;
    //单位简称
    private String  bdPreparedByAbbName;
    private String  bdAuditUnitAbbName;

    //送审概算文件
    private String bdReviewFile;
    //审定概算文件
    private String bdAuthorizeFile;


    // 工程建安费
    private BigDecimal engineeringConstructionSafetyFeeAmount;
    // 工程建设其他费
    private BigDecimal engineeringConstructionOtherFeeAmount;
    // 其中土地费
    private BigDecimal landFeeAmount;
    // 建设期贷款利息费用
    private BigDecimal constructionPeriodLoanInterestAmount;
    // 预备费
    private BigDecimal reserveFeeAmount;

    public BigDecimal getEngineeringConstructionSafetyFeeAmount() {
        return engineeringConstructionSafetyFeeAmount;
    }

    public void setEngineeringConstructionSafetyFeeAmount(BigDecimal engineeringConstructionSafetyFeeAmount) {
        this.engineeringConstructionSafetyFeeAmount = engineeringConstructionSafetyFeeAmount;
    }

    public BigDecimal getEngineeringConstructionOtherFeeAmount() {
        return engineeringConstructionOtherFeeAmount;
    }

    public void setEngineeringConstructionOtherFeeAmount(BigDecimal engineeringConstructionOtherFeeAmount) {
        this.engineeringConstructionOtherFeeAmount = engineeringConstructionOtherFeeAmount;
    }

    public BigDecimal getLandFeeAmount() {
        return landFeeAmount;
    }

    public void setLandFeeAmount(BigDecimal landFeeAmount) {
        this.landFeeAmount = landFeeAmount;
    }

    public BigDecimal getConstructionPeriodLoanInterestAmount() {
        return constructionPeriodLoanInterestAmount;
    }

    public void setConstructionPeriodLoanInterestAmount(BigDecimal constructionPeriodLoanInterestAmount) {
        this.constructionPeriodLoanInterestAmount = constructionPeriodLoanInterestAmount;
    }

    public BigDecimal getReserveFeeAmount() {
        return reserveFeeAmount;
    }

    public void setReserveFeeAmount(BigDecimal reserveFeeAmount) {
        this.reserveFeeAmount = reserveFeeAmount;
    }

    public String getBdId() {
        return bdId;
    }

    public void setBdId(String bdId) {
        this.bdId = bdId;
    }

    public String getBdPreparedBy() {
        return bdPreparedBy;
    }

    public void setBdPreparedBy(String bdPreparedBy) {
        this.bdPreparedBy = bdPreparedBy;
    }

    public String getBdAuditUnit() {
        return bdAuditUnit;
    }

    public void setBdAuditUnit(String bdAuditUnit) {
        this.bdAuditUnit = bdAuditUnit;
    }

    public BigDecimal getBdMoney() {
        return bdMoney;
    }

    public void setBdMoney(BigDecimal bdMoney) {
        this.bdMoney = bdMoney;
    }

    public String getBdRemark() {
        return bdRemark;
    }

    public void setBdRemark(String bdRemark) {
        this.bdRemark = bdRemark;
    }

    public String getBdCreateUid() {
        return bdCreateUid;
    }

    public void setBdCreateUid(String bdCreateUid) {
        this.bdCreateUid = bdCreateUid;
    }

    public String getBdCreateUname() {
        return bdCreateUname;
    }

    public void setBdCreateUname(String bdCreateUname) {
        this.bdCreateUname = bdCreateUname;
    }

    public Date getBdCreateTime() {
        return bdCreateTime;
    }

    public void setBdCreateTime(Date bdCreateTime) {
        this.bdCreateTime = bdCreateTime;
    }

    public Integer getBdState() {
        return bdState;
    }

    public void setBdState(Integer bdState) {
        this.bdState = bdState;
    }

    public Date getBdStateTime() {
        return bdStateTime;
    }

    public void setBdStateTime(Date bdStateTime) {
        this.bdStateTime = bdStateTime;
    }

    public String getBdPreparedByName() {
        return bdPreparedByName;
    }

    public void setBdPreparedByName(String bdPreparedByName) {
        this.bdPreparedByName = bdPreparedByName;
    }

    public String getBdAuditUnitName() {
        return bdAuditUnitName;
    }

    public void setBdAuditUnitName(String bdAuditUnitName) {
        this.bdAuditUnitName = bdAuditUnitName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public BigDecimal getBdAuditAmt() {
        return bdAuditAmt;
    }

    public void setBdAuditAmt(BigDecimal bdAuditAmt) {
        this.bdAuditAmt = bdAuditAmt;
    }

    public String getBdSplitForm() {
        return bdSplitForm;
    }

    public void setBdSplitForm(String bdSplitForm) {
        this.bdSplitForm = bdSplitForm;
    }

    public Integer getBdSplitType() {
        return bdSplitType;
    }

    public void setBdSplitType(Integer bdSplitType) {
        this.bdSplitType = bdSplitType;
    }

    public String getBdApprovalTitle() {
        return bdApprovalTitle;
    }

    public void setBdApprovalTitle(String bdApprovalTitle) {
        this.bdApprovalTitle = bdApprovalTitle;
    }

    public String getBdApprovalNum() {
        return bdApprovalNum;
    }

    public void setBdApprovalNum(String bdApprovalNum) {
        this.bdApprovalNum = bdApprovalNum;
    }

    public Date getBdApprovalTime() {
        return bdApprovalTime;
    }

    public void setBdApprovalTime(Date bdApprovalTime) {
        this.bdApprovalTime = bdApprovalTime;
    }

    public String getBdApprovalFile() {
        return bdApprovalFile;
    }

    public void setBdApprovalFile(String bdApprovalFile) {
        this.bdApprovalFile = bdApprovalFile;
    }

    public String getBdApprovalUnit() {
        return bdApprovalUnit;
    }

    public void setBdApprovalUnit(String bdApprovalUnit) {
        this.bdApprovalUnit = bdApprovalUnit;
    }

    public BigDecimal getBdCoverArea() {
        return bdCoverArea;
    }

    public void setBdCoverArea(BigDecimal bdCoverArea) {
        this.bdCoverArea = bdCoverArea;
    }

    public BigDecimal getBdBuildArea() {
        return bdBuildArea;
    }

    public void setBdBuildArea(BigDecimal bdBuildArea) {
        this.bdBuildArea = bdBuildArea;
    }

    public String getBdType() {
        return bdType;
    }

    public void setBdType(String bdType) {
        this.bdType = bdType;
    }

    public String getBdSplitFormName() {
        return bdSplitFormName;
    }

    public void setBdSplitFormName(String bdSplitFormName) {
        this.bdSplitFormName = bdSplitFormName;
    }

    public List<InvBudgetDeclareDtlModel> getInvBudgetDeclareDtls() {
        return invBudgetDeclareDtls;
    }

    public void setInvBudgetDeclareDtls(List<InvBudgetDeclareDtlModel> invBudgetDeclareDtls) {
        this.invBudgetDeclareDtls = invBudgetDeclareDtls;
    }

    public String getBdPreparedByAbbName() {
        return bdPreparedByAbbName;
    }

    public void setBdPreparedByAbbName(String bdPreparedByAbbName) {
        this.bdPreparedByAbbName = bdPreparedByAbbName;
    }

    public String getBdAuditUnitAbbName() {
        return bdAuditUnitAbbName;
    }

    public void setBdAuditUnitAbbName(String bdAuditUnitAbbName) {
        this.bdAuditUnitAbbName = bdAuditUnitAbbName;
    }

    public String getBdReviewFile() {
        return bdReviewFile;
    }

    public void setBdReviewFile(String bdReviewFile) {
        this.bdReviewFile = bdReviewFile;
    }

    public String getBdAuthorizeFile() {
        return bdAuthorizeFile;
    }

    public void setBdAuthorizeFile(String bdAuthorizeFile) {
        this.bdAuthorizeFile = bdAuthorizeFile;
    }
}
