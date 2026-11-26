package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 代建资金预拨
 */
public class AgentFundAdvanceModel  extends ProjectCommonRes {

    private String afaId;
    private String pId;
    private String conId;
    private String conContractCode;
    private BigDecimal conContractMoney;
    private BigDecimal afaEstimatedAmount;
    private BigDecimal afaLastTotalMoney;
    private BigDecimal afaThisMoney;
    private BigDecimal afaSurplusMoney;
    private String afaApplicationBatch;
    private String afaApplicationDepartment;
    private Date afaApplicationTime;
    private String afaCreateUid;
    private String afaCreateUname;
    private Date afaCreateTime;
    private Integer afaState;
    private Date afaStateTime;
    private BigDecimal afaCapitalSurplus;
    private BigDecimal afaUnpaid;


    private String conName;
    private String conCode;
    private String applyCName;
    private String applyCAbbName;
    private List<AgentFundAdvanceListModel> fundAdvanceLists;

    public String getAfaId() {
        return afaId;
    }

    public void setAfaId(String afaId) {
        this.afaId = afaId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConContractCode() {
        return conContractCode;
    }

    public void setConContractCode(String conContractCode) {
        this.conContractCode = conContractCode;
    }

    public BigDecimal getConContractMoney() {
        return conContractMoney;
    }

    public void setConContractMoney(BigDecimal conContractMoney) {
        this.conContractMoney = conContractMoney;
    }

    public BigDecimal getAfaEstimatedAmount() {
        return afaEstimatedAmount;
    }

    public void setAfaEstimatedAmount(BigDecimal afaEstimatedAmount) {
        this.afaEstimatedAmount = afaEstimatedAmount;
    }

    public BigDecimal getAfaLastTotalMoney() {
        return afaLastTotalMoney;
    }

    public void setAfaLastTotalMoney(BigDecimal afaLastTotalMoney) {
        this.afaLastTotalMoney = afaLastTotalMoney;
    }

    public BigDecimal getAfaThisMoney() {
        return afaThisMoney;
    }

    public void setAfaThisMoney(BigDecimal afaThisMoney) {
        this.afaThisMoney = afaThisMoney;
    }

    public BigDecimal getAfaSurplusMoney() {
        return afaSurplusMoney;
    }

    public void setAfaSurplusMoney(BigDecimal afaSurplusMoney) {
        this.afaSurplusMoney = afaSurplusMoney;
    }

    public String getAfaApplicationBatch() {
        return afaApplicationBatch;
    }

    public void setAfaApplicationBatch(String afaApplicationBatch) {
        this.afaApplicationBatch = afaApplicationBatch;
    }

    public String getAfaApplicationDepartment() {
        return afaApplicationDepartment;
    }

    public void setAfaApplicationDepartment(String afaApplicationDepartment) {
        this.afaApplicationDepartment = afaApplicationDepartment;
    }

    public Date getAfaApplicationTime() {
        return afaApplicationTime;
    }

    public void setAfaApplicationTime(Date afaApplicationTime) {
        this.afaApplicationTime = afaApplicationTime;
    }

    public String getAfaCreateUid() {
        return afaCreateUid;
    }

    public void setAfaCreateUid(String afaCreateUid) {
        this.afaCreateUid = afaCreateUid;
    }

    public String getAfaCreateUname() {
        return afaCreateUname;
    }

    public void setAfaCreateUname(String afaCreateUname) {
        this.afaCreateUname = afaCreateUname;
    }

    public Date getAfaCreateTime() {
        return afaCreateTime;
    }

    public void setAfaCreateTime(Date afaCreateTime) {
        this.afaCreateTime = afaCreateTime;
    }

    public Integer getAfaState() {
        return afaState;
    }

    public void setAfaState(Integer afaState) {
        this.afaState = afaState;
    }

    public Date getAfaStateTime() {
        return afaStateTime;
    }

    public void setAfaStateTime(Date afaStateTime) {
        this.afaStateTime = afaStateTime;
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

    public String getApplyCName() {
        return applyCName;
    }

    public void setApplyCName(String applyCName) {
        this.applyCName = applyCName;
    }

    public String getApplyCAbbName() {
        return applyCAbbName;
    }

    public void setApplyCAbbName(String applyCAbbName) {
        this.applyCAbbName = applyCAbbName;
    }

    public List<AgentFundAdvanceListModel> getFundAdvanceLists() {
        return fundAdvanceLists;
    }

    public void setFundAdvanceLists(List<AgentFundAdvanceListModel> fundAdvanceLists) {
        this.fundAdvanceLists = fundAdvanceLists;
    }

    public BigDecimal getAfaCapitalSurplus() {
        return afaCapitalSurplus;
    }

    public void setAfaCapitalSurplus(BigDecimal afaCapitalSurplus) {
        this.afaCapitalSurplus = afaCapitalSurplus;
    }

    public BigDecimal getAfaUnpaid() {
        return afaUnpaid;
    }

    public void setAfaUnpaid(BigDecimal afaUnpaid) {
        this.afaUnpaid = afaUnpaid;
    }
}
