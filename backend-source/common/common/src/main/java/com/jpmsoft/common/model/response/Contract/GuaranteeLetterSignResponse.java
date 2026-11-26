package com.jpmsoft.common.model.response.Contract;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 保函签收
 */
public class GuaranteeLetterSignResponse extends GuaranteeLetterResponseCommon {
    private String glsId;
    private String pId;
    private String secId;
    private String conId;
    private String glsCode;
    private String glsType;
    private String glsGuarantor;
    private BigDecimal glsMoney;
    private String glsWarrantee;
    private String glsWarranteeTel;
    private Date glsIssuanceDate;
    private Integer glsNoTimeLimit;
    private Date glsDueDate;
    private String glsCreateUid;
    private String glsCreateUname;
    private Date glsCreateTime;
    private Integer glsState;
    private Date glsStateTime;
    private String glsBeneficiary;
    private String glsNum;
    private String glsWarranteePerson;
    private String glsFillingDepartment;
    private Integer glsIsrenewal;
    private String glsRenewalId;
    private String glsDueDateRemark;
    private String glsIssuanceDateRemark;
    private Date glsSignDate;

    private String glsWarranteeName;
    private String glsBeneficiaryName;
    public String glbId;
    public Integer glbState;
    private String glbCreateUid;
    private Date glbDate;
    private String glbCompanyId;
    private String glbCompanyName;
    private GuaranteeLetterBackResponse guaranteeLetterBack;
    public String glsWarranteeNameHistory;
    public String glsBeneficiaryNameHistory;
    private String secFeatures;
    private String secPlace;

    private Date conPlanStartDate;

    private Date conPlanEndDate;

    private BigDecimal conMoneyTotal;

    private String companyPartyAName;

    private String companyPartybName;

    private BigDecimal conAcMoney;


    private String glsJlxs;


    public String getGlsJlxs() {
        return glsJlxs;
    }

    public void setGlsJlxs(String glsJlxs) {
        this.glsJlxs = glsJlxs;
    }

    public BigDecimal getConAcMoney() {
        return conAcMoney;
    }

    public void setConAcMoney(BigDecimal conAcMoney) {
        this.conAcMoney = conAcMoney;
    }

    public BigDecimal getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(BigDecimal conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public String getCompanyPartyAName() {
        return companyPartyAName;
    }

    public void setCompanyPartyAName(String companyPartyAName) {
        this.companyPartyAName = companyPartyAName;
    }

    public String getCompanyPartybName() {
        return companyPartybName;
    }

    public void setCompanyPartybName(String companyPartybName) {
        this.companyPartybName = companyPartybName;
    }

    public Date getConPlanStartDate() {
        return conPlanStartDate;
    }

    public void setConPlanStartDate(Date conPlanStartDate) {
        this.conPlanStartDate = conPlanStartDate;
    }

    public Date getConPlanEndDate() {
        return conPlanEndDate;
    }

    public void setConPlanEndDate(Date conPlanEndDate) {
        this.conPlanEndDate = conPlanEndDate;
    }

    public String getGlsId() {
        return glsId;
    }

    public void setGlsId(String glsId) {
        this.glsId = glsId;
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

    public String getGlsCode() {
        return glsCode;
    }

    public void setGlsCode(String glsCode) {
        this.glsCode = glsCode;
    }

    public String getGlsType() {
        return glsType;
    }

    public void setGlsType(String glsType) {
        this.glsType = glsType;
    }

    public String getGlsGuarantor() {
        return glsGuarantor;
    }

    public void setGlsGuarantor(String glsGuarantor) {
        this.glsGuarantor = glsGuarantor;
    }

    public BigDecimal getGlsMoney() {
        return glsMoney;
    }

    public void setGlsMoney(BigDecimal glsMoney) {
        this.glsMoney = glsMoney;
    }

    public String getGlsWarrantee() {
        return glsWarrantee;
    }

    public void setGlsWarrantee(String glsWarrantee) {
        this.glsWarrantee = glsWarrantee;
    }

    public String getGlsWarranteeTel() {
        return glsWarranteeTel;
    }

    public void setGlsWarranteeTel(String glsWarranteeTel) {
        this.glsWarranteeTel = glsWarranteeTel;
    }

    public Date getGlsIssuanceDate() {
        return glsIssuanceDate;
    }

    public void setGlsIssuanceDate(Date glsIssuanceDate) {
        this.glsIssuanceDate = glsIssuanceDate;
    }

    public Integer getGlsNoTimeLimit() {
        return glsNoTimeLimit;
    }

    public void setGlsNoTimeLimit(Integer glsNoTimeLimit) {
        this.glsNoTimeLimit = glsNoTimeLimit;
    }

    public Date getGlsDueDate() {
        return glsDueDate;
    }

    public void setGlsDueDate(Date glsDueDate) {
        this.glsDueDate = glsDueDate;
    }

    public String getGlsCreateUid() {
        return glsCreateUid;
    }

    public void setGlsCreateUid(String glsCreateUid) {
        this.glsCreateUid = glsCreateUid;
    }

    public String getGlsCreateUname() {
        return glsCreateUname;
    }

    public void setGlsCreateUname(String glsCreateUname) {
        this.glsCreateUname = glsCreateUname;
    }

    public Date getGlsCreateTime() {
        return glsCreateTime;
    }

    public void setGlsCreateTime(Date glsCreateTime) {
        this.glsCreateTime = glsCreateTime;
    }

    public Integer getGlsState() {
        return glsState;
    }

    public void setGlsState(Integer glsState) {
        this.glsState = glsState;
    }

    public Date getGlsStateTime() {
        return glsStateTime;
    }

    public void setGlsStateTime(Date glsStateTime) {
        this.glsStateTime = glsStateTime;
    }

    public String getGlsBeneficiary() {
        return glsBeneficiary;
    }

    public void setGlsBeneficiary(String glsBeneficiary) {
        this.glsBeneficiary = glsBeneficiary;
    }

    public String getGlsNum() {
        return glsNum;
    }

    public void setGlsNum(String glsNum) {
        this.glsNum = glsNum;
    }

    public String getGlsWarranteePerson() {
        return glsWarranteePerson;
    }

    public void setGlsWarranteePerson(String glsWarranteePerson) {
        this.glsWarranteePerson = glsWarranteePerson;
    }

    public String getGlsFillingDepartment() {
        return glsFillingDepartment;
    }

    public void setGlsFillingDepartment(String glsFillingDepartment) {
        this.glsFillingDepartment = glsFillingDepartment;
    }

    public Integer getGlsIsrenewal() {
        return glsIsrenewal;
    }

    public void setGlsIsrenewal(Integer glsIsrenewal) {
        this.glsIsrenewal = glsIsrenewal;
    }

    public String getGlsRenewalId() {
        return glsRenewalId;
    }

    public void setGlsRenewalId(String glsRenewalId) {
        this.glsRenewalId = glsRenewalId;
    }

    public String getGlsDueDateRemark() {
        return glsDueDateRemark;
    }

    public void setGlsDueDateRemark(String glsDueDateRemark) {
        this.glsDueDateRemark = glsDueDateRemark;
    }

    public String getGlsIssuanceDateRemark() {
        return glsIssuanceDateRemark;
    }

    public void setGlsIssuanceDateRemark(String glsIssuanceDateRemark) {
        this.glsIssuanceDateRemark = glsIssuanceDateRemark;
    }

    public Date getGlsSignDate() {
        return glsSignDate;
    }

    public void setGlsSignDate(Date glsSignDate) {
        this.glsSignDate = glsSignDate;
    }

    public String getGlsWarranteeName() {
        return glsWarranteeName;
    }

    public void setGlsWarranteeName(String glsWarranteeName) {
        this.glsWarranteeName = glsWarranteeName;
    }

    public String getGlsBeneficiaryName() {
        return glsBeneficiaryName;
    }

    public void setGlsBeneficiaryName(String glsBeneficiaryName) {
        this.glsBeneficiaryName = glsBeneficiaryName;
    }

    public String getGlbId() {
        return glbId;
    }

    public void setGlbId(String glbId) {
        this.glbId = glbId;
    }

    public Integer getGlbState() {
        return glbState;
    }

    public void setGlbState(Integer glbState) {
        this.glbState = glbState;
    }

    public String getGlbCreateUid() {
        return glbCreateUid;
    }

    public void setGlbCreateUid(String glbCreateUid) {
        this.glbCreateUid = glbCreateUid;
    }

    public Date getGlbDate() {
        return glbDate;
    }

    public void setGlbDate(Date glbDate) {
        this.glbDate = glbDate;
    }

    public String getGlbCompanyId() {
        return glbCompanyId;
    }

    public void setGlbCompanyId(String glbCompanyId) {
        this.glbCompanyId = glbCompanyId;
    }

    public String getGlbCompanyName() {
        return glbCompanyName;
    }

    public void setGlbCompanyName(String glbCompanyName) {
        this.glbCompanyName = glbCompanyName;
    }

    public GuaranteeLetterBackResponse getGuaranteeLetterBack() {
        return guaranteeLetterBack;
    }

    public void setGuaranteeLetterBack(GuaranteeLetterBackResponse guaranteeLetterBack) {
        this.guaranteeLetterBack = guaranteeLetterBack;
    }


    public String getGlsWarranteeNameHistory() {
        return glsWarranteeNameHistory;
    }

    public void setGlsWarranteeNameHistory(String glsWarranteeNameHistory) {
        this.glsWarranteeNameHistory = glsWarranteeNameHistory;
    }


    public String getGlsBeneficiaryNameHistory() {
        return glsBeneficiaryNameHistory;
    }

    public void setGlsBeneficiaryNameHistory(String glsBeneficiaryNameHistory) {
        this.glsBeneficiaryNameHistory = glsBeneficiaryNameHistory;
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
}
