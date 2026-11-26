package com.jpmsoft.common.model.request.Contract;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 保函签收
 */
public class GuaranteeLetterSignRequest extends BaseFileRequest {

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

    public String pName;
    public String conName;
    private String conCode;
    public String secName;
    private String glsWarranteeName;
    private List<String> glsIssuanceDates;
    private List<String> glsDueDates;
    private List<String> glsCreateTimes;
    private List<String> glsMoneys;
    private String glsBeneficiaryName;
    private String glbCompanyName;
    private String companyPartyAName;
    private String companyPartybName;
    /**
     * 是否保函办退
     * true 是
     * false 否
     * null 不区分
     */
    private Boolean haveBack;
    /**
     * 是否到期
     * true 已过期
     * false 未过期
     * null 不区分
     */
    private Boolean overDue;
    /**
     * 1:两月内
     * 2:一月内
     * 3:一周内
     */
    private Integer duwSoon;

    private String glsJlxs;

    private Integer glbState;

    public Integer getGlbState() {
        return glbState;
    }

    public void setGlbState(Integer glbState) {
        this.glbState = glbState;
    }

    public String getGlbCompanyName() {
        return glbCompanyName;
    }

    public void setGlbCompanyName(String glbCompanyName) {
        this.glbCompanyName = glbCompanyName;
    }
    public String getGlsBeneficiaryName() {
        return glsBeneficiaryName;
    }

    public void setGlsBeneficiaryName(String glsBeneficiaryName) {
        this.glsBeneficiaryName = glsBeneficiaryName;
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

    public String getGlsJlxs() {
        return glsJlxs;
    }

    public void setGlsJlxs(String glsJlxs) {
        this.glsJlxs = glsJlxs;
    }

    public String getGlsId() {
        return glsId;
    }

    public void setGlsId(String glsId) {
        this.glsId = glsId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
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

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
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

    public List<String> getGlsIssuanceDates() {
        return glsIssuanceDates;
    }

    public void setGlsIssuanceDates(List<String> glsIssuanceDates) {
        this.glsIssuanceDates = glsIssuanceDates;
    }

    public List<String> getGlsDueDates() {
        return glsDueDates;
    }

    public void setGlsDueDates(List<String> glsDueDates) {
        this.glsDueDates = glsDueDates;
    }

    public List<String> getGlsCreateTimes() {
        return glsCreateTimes;
    }

    public void setGlsCreateTimes(List<String> glsCreateTimes) {
        this.glsCreateTimes = glsCreateTimes;
    }

    public Boolean getHaveBack() {
        return haveBack;
    }

    public void setHaveBack(Boolean haveBack) {
        this.haveBack = haveBack;
    }

    public Boolean getOverDue() {
        return overDue;
    }

    public void setOverDue(Boolean overDue) {
        this.overDue = overDue;
    }

    public Integer getDuwSoon() {
        return duwSoon;
    }

    public void setDuwSoon(Integer duwSoon) {
        this.duwSoon = duwSoon;
    }

    public List<String> getGlsMoneys() {
        return glsMoneys;
    }

    public void setGlsMoneys(List<String> glsMoneys) {
        this.glsMoneys = glsMoneys;
    }
}
