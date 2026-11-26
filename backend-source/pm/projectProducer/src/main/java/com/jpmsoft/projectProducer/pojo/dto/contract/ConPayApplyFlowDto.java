package com.jpmsoft.projectProducer.pojo.dto.contract;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ConPayApplyFlowDto extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -447329102315537525L;
    public String cpafId;
    public String cpaId;
    public String pId;
    public String conId;
    public String cpafApplyDetails;
    public String cpafConstructionUnit;
    public String cpafConstructionUnitName;
    public String cpafSerialNumber;
    public String cpafPayee;
    public String cpafPayeeName;
    public String cpafDepositBank;
    public String cpafBankAccount;
    public String cpafContractContent;
    public String cpafContractProgress;
    public BigDecimal cpafTotalLast;
    public BigDecimal cpafApply;
    public BigDecimal cpafPaidThisPeriod;
    public BigDecimal cpafApplication;
    public BigDecimal cpafRemainingAmount;
    public BigDecimal cpafTotalPayment;
    public BigDecimal cpafApplyProportion;
    public Date cpafApplyTime;
    public String cpafApplyUname;
    public Date cpafCreateTime;
    public String cpafCreateUid;
    public Integer state;
    public Date stateTime;
    private Boolean cpafCompute;
    public String cpafNewPayeeName;
    public BigDecimal cpafContractMoney;
    public BigDecimal cpafAmountInvoice;
    private String cpafInvoiceCode;
    public BigDecimal cpafAuditPrice;
    public BigDecimal cpafAmountIssued;
    public BigDecimal cpafAmountDisbursement;

    public String conName;
    public BigDecimal conMoneyTotal;
    private String conIdSup;
    public Integer cpafHistorical;
    private List<Map<String, Object>> conList;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCpafId() {
        return cpafId;
    }

    public void setCpafId(String cpafId) {
        this.cpafId = cpafId;
    }

    public String getCpaId() {
        return cpaId;
    }

    public void setCpaId(String cpaId) {
        this.cpaId = cpaId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getCpafApplyDetails() {
        return cpafApplyDetails;
    }

    public void setCpafApplyDetails(String cpafApplyDetails) {
        this.cpafApplyDetails = cpafApplyDetails;
    }

    public String getCpafConstructionUnit() {
        return cpafConstructionUnit;
    }

    public void setCpafConstructionUnit(String cpafConstructionUnit) {
        this.cpafConstructionUnit = cpafConstructionUnit;
    }

    public String getCpafSerialNumber() {
        return cpafSerialNumber;
    }

    public void setCpafSerialNumber(String cpafSerialNumber) {
        this.cpafSerialNumber = cpafSerialNumber;
    }

    public String getCpafPayee() {
        return cpafPayee;
    }

    public void setCpafPayee(String cpafPayee) {
        this.cpafPayee = cpafPayee;
    }

    public String getCpafDepositBank() {
        return cpafDepositBank;
    }

    public void setCpafDepositBank(String cpafDepositBank) {
        this.cpafDepositBank = cpafDepositBank;
    }

    public String getCpafBankAccount() {
        return cpafBankAccount;
    }

    public void setCpafBankAccount(String cpafBankAccount) {
        this.cpafBankAccount = cpafBankAccount;
    }

    public String getCpafContractContent() {
        return cpafContractContent;
    }

    public void setCpafContractContent(String cpafContractContent) {
        this.cpafContractContent = cpafContractContent;
    }

    public String getCpafContractProgress() {
        return cpafContractProgress;
    }

    public void setCpafContractProgress(String cpafContractProgress) {
        this.cpafContractProgress = cpafContractProgress;
    }

    public BigDecimal getCpafTotalLast() {
        return cpafTotalLast;
    }

    public void setCpafTotalLast(BigDecimal cpafTotalLast) {
        this.cpafTotalLast = cpafTotalLast;
    }

    public BigDecimal getCpafApply() {
        return cpafApply;
    }

    public void setCpafApply(BigDecimal cpafApply) {
        this.cpafApply = cpafApply;
    }

    public BigDecimal getCpafPaidThisPeriod() {
        return cpafPaidThisPeriod;
    }

    public void setCpafPaidThisPeriod(BigDecimal cpafPaidThisPeriod) {
        this.cpafPaidThisPeriod = cpafPaidThisPeriod;
    }

    public BigDecimal getCpafApplication() {
        return cpafApplication;
    }

    public void setCpafApplication(BigDecimal cpafApplication) {
        this.cpafApplication = cpafApplication;
    }

    public BigDecimal getCpafRemainingAmount() {
        return cpafRemainingAmount;
    }

    public void setCpafRemainingAmount(BigDecimal cpafRemainingAmount) {
        this.cpafRemainingAmount = cpafRemainingAmount;
    }

    public BigDecimal getCpafTotalPayment() {
        return cpafTotalPayment;
    }

    public void setCpafTotalPayment(BigDecimal cpafTotalPayment) {
        this.cpafTotalPayment = cpafTotalPayment;
    }

    public BigDecimal getCpafApplyProportion() {
        return cpafApplyProportion;
    }

    public void setCpafApplyProportion(BigDecimal cpafApplyProportion) {
        this.cpafApplyProportion = cpafApplyProportion;
    }

    public Date getCpafApplyTime() {
        return cpafApplyTime;
    }

    public void setCpafApplyTime(Date cpafApplyTime) {
        this.cpafApplyTime = cpafApplyTime;
    }

    public String getCpafApplyUname() {
        return cpafApplyUname;
    }

    public void setCpafApplyUname(String cpafApplyUname) {
        this.cpafApplyUname = cpafApplyUname;
    }

    public Date getCpafCreateTime() {
        return cpafCreateTime;
    }

    public void setCpafCreateTime(Date cpafCreateTime) {
        this.cpafCreateTime = cpafCreateTime;
    }

    public String getCpafCreateUid() {
        return cpafCreateUid;
    }

    public void setCpafCreateUid(String cpafCreateUid) {
        this.cpafCreateUid = cpafCreateUid;
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

    public Boolean getCpafCompute() {
        return cpafCompute;
    }

    public void setCpafCompute(Boolean cpafCompute) {
        this.cpafCompute = cpafCompute;
    }

    public String getCpafNewPayeeName() {
        return cpafNewPayeeName;
    }

    public void setCpafNewPayeeName(String cpafNewPayeeName) {
        this.cpafNewPayeeName = cpafNewPayeeName;
    }

    public BigDecimal getCpafContractMoney() {
        return cpafContractMoney;
    }

    public void setCpafContractMoney(BigDecimal cpafContractMoney) {
        this.cpafContractMoney = cpafContractMoney;
    }

    public BigDecimal getCpafAmountInvoice() {
        return cpafAmountInvoice;
    }

    public void setCpafAmountInvoice(BigDecimal cpafAmountInvoice) {
        this.cpafAmountInvoice = cpafAmountInvoice;
    }

    public String getCpafInvoiceCode() {
        return cpafInvoiceCode;
    }

    public void setCpafInvoiceCode(String cpafInvoiceCode) {
        this.cpafInvoiceCode = cpafInvoiceCode;
    }

    public BigDecimal getCpafAuditPrice() {
        return cpafAuditPrice;
    }

    public void setCpafAuditPrice(BigDecimal cpafAuditPrice) {
        this.cpafAuditPrice = cpafAuditPrice;
    }

    public BigDecimal getCpafAmountIssued() {
        return cpafAmountIssued;
    }

    public void setCpafAmountIssued(BigDecimal cpafAmountIssued) {
        this.cpafAmountIssued = cpafAmountIssued;
    }

    public BigDecimal getCpafAmountDisbursement() {
        return cpafAmountDisbursement;
    }

    public void setCpafAmountDisbursement(BigDecimal cpafAmountDisbursement) {
        this.cpafAmountDisbursement = cpafAmountDisbursement;
    }

    public String getCpafConstructionUnitName() {
        return cpafConstructionUnitName;
    }

    public void setCpafConstructionUnitName(String cpafConstructionUnitName) {
        this.cpafConstructionUnitName = cpafConstructionUnitName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public BigDecimal getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(BigDecimal conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public String getCpafPayeeName() {
        return cpafPayeeName;
    }

    public void setCpafPayeeName(String cpafPayeeName) {
        this.cpafPayeeName = cpafPayeeName;
    }

    public String getConIdSup() {
        return conIdSup;
    }

    public void setConIdSup(String conIdSup) {
        this.conIdSup = conIdSup;
    }

    public List<Map<String, Object>> getConList() {
        return conList;
    }

    public void setConList(List<Map<String, Object>> conList) {
        this.conList = conList;
    }

    public Integer getCpafHistorical() {
        return cpafHistorical;
    }

    public void setCpafHistorical(Integer cpafHistorical) {
        this.cpafHistorical = cpafHistorical;
    }
}
