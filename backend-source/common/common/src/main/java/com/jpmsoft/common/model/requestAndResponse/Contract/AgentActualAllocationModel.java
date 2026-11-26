package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 代建合同实拨
 */
public class AgentActualAllocationModel extends ProjectCommonRes {

    private String aalId;
    private String conId;
    private String pId;
    private String aalConCode;
    private BigDecimal aalConMoney;
    private BigDecimal aalAppropriated;
    private BigDecimal aalThisPay;
    private String aalApplicant;
    private Date aalApplyTime;
    private String aalContacts;
    private String aalContactTelephone;
    private String aalAgentConId;
    private String aalAgentConCode;
    private String aalAgentConType;
    private String aalPayType;
    private String aalCollectionAccount;
    private String aalReceivingBank;
    private String aalCollectionNumber;
    private String aalCollectionAccountG;
    private String aalReceivingBankG;
    private String aalCollectionNumbe;
    private String aalGuaranteeFile;
    private String aalLiquidatedDamages;
    private BigDecimal aalConApply;
    private BigDecimal aalConExamine;
    private BigDecimal aalLastTotalPay;
    private BigDecimal aalLastTotalPayExa;
    private BigDecimal aalLastTotalPayL;
    private BigDecimal aalLastTotalPayLexa;
    private BigDecimal aalViolateMoney;
    private BigDecimal aalViolateMoneyExa;
    private BigDecimal aalThisPayMoney;
    private BigDecimal aalThisPayMoneyExa;
    private BigDecimal aalSurplusMoney;
    private BigDecimal aalSurplusMoneyExa;
    private Date aalCreateTime;
    private String aalCreateUid;
    private String aalCreateUname;
    private Integer aalState;
    private String aalStateTime;
    private BigDecimal aalSurplusMoneyAgent;

    private String conName;
    private String scTitle;
    private String scName;
    private String guaranteeIds;
    private String liquidatedIds;
    private String aalApplicantName;
    private String aalApplicantAbbName;
    private BigDecimal aalLastTotalReal;
    //是否结算款 1：是，0：否
    private Integer aalPaySettled;
    //合同结算价
    private BigDecimal aalConSettlementPrice;


    public String getAalId() {
        return aalId;
    }

    public void setAalId(String aalId) {
        this.aalId = aalId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getAalConCode() {
        return aalConCode;
    }

    public void setAalConCode(String aalConCode) {
        this.aalConCode = aalConCode;
    }

    public BigDecimal getAalConMoney() {
        return aalConMoney;
    }

    public void setAalConMoney(BigDecimal aalConMoney) {
        this.aalConMoney = aalConMoney;
    }

    public BigDecimal getAalAppropriated() {
        return aalAppropriated;
    }

    public void setAalAppropriated(BigDecimal aalAppropriated) {
        this.aalAppropriated = aalAppropriated;
    }

    public BigDecimal getAalThisPay() {
        return aalThisPay;
    }

    public void setAalThisPay(BigDecimal aalThisPay) {
        this.aalThisPay = aalThisPay;
    }

    public String getAalApplicant() {
        return aalApplicant;
    }

    public void setAalApplicant(String aalApplicant) {
        this.aalApplicant = aalApplicant;
    }

    public Date getAalApplyTime() {
        return aalApplyTime;
    }

    public void setAalApplyTime(Date aalApplyTime) {
        this.aalApplyTime = aalApplyTime;
    }

    public String getAalContacts() {
        return aalContacts;
    }

    public void setAalContacts(String aalContacts) {
        this.aalContacts = aalContacts;
    }

    public String getAalContactTelephone() {
        return aalContactTelephone;
    }

    public void setAalContactTelephone(String aalContactTelephone) {
        this.aalContactTelephone = aalContactTelephone;
    }

    public String getAalAgentConId() {
        return aalAgentConId;
    }

    public void setAalAgentConId(String aalAgentConId) {
        this.aalAgentConId = aalAgentConId;
    }

    public String getAalAgentConCode() {
        return aalAgentConCode;
    }

    public void setAalAgentConCode(String aalAgentConCode) {
        this.aalAgentConCode = aalAgentConCode;
    }

    public String getAalAgentConType() {
        return aalAgentConType;
    }

    public void setAalAgentConType(String aalAgentConType) {
        this.aalAgentConType = aalAgentConType;
    }

    public String getAalPayType() {
        return aalPayType;
    }

    public void setAalPayType(String aalPayType) {
        this.aalPayType = aalPayType;
    }

    public String getAalCollectionAccount() {
        return aalCollectionAccount;
    }

    public void setAalCollectionAccount(String aalCollectionAccount) {
        this.aalCollectionAccount = aalCollectionAccount;
    }

    public String getAalReceivingBank() {
        return aalReceivingBank;
    }

    public void setAalReceivingBank(String aalReceivingBank) {
        this.aalReceivingBank = aalReceivingBank;
    }

    public String getAalCollectionNumber() {
        return aalCollectionNumber;
    }

    public void setAalCollectionNumber(String aalCollectionNumber) {
        this.aalCollectionNumber = aalCollectionNumber;
    }

    public String getAalCollectionAccountG() {
        return aalCollectionAccountG;
    }

    public void setAalCollectionAccountG(String aalCollectionAccountG) {
        this.aalCollectionAccountG = aalCollectionAccountG;
    }

    public String getAalReceivingBankG() {
        return aalReceivingBankG;
    }

    public void setAalReceivingBankG(String aalReceivingBankG) {
        this.aalReceivingBankG = aalReceivingBankG;
    }

    public String getAalCollectionNumbe() {
        return aalCollectionNumbe;
    }

    public void setAalCollectionNumbe(String aalCollectionNumbe) {
        this.aalCollectionNumbe = aalCollectionNumbe;
    }

    public String getAalGuaranteeFile() {
        return aalGuaranteeFile;
    }

    public void setAalGuaranteeFile(String aalGuaranteeFile) {
        this.aalGuaranteeFile = aalGuaranteeFile;
    }

    public String getAalLiquidatedDamages() {
        return aalLiquidatedDamages;
    }

    public void setAalLiquidatedDamages(String aalLiquidatedDamages) {
        this.aalLiquidatedDamages = aalLiquidatedDamages;
    }

    public BigDecimal getAalConApply() {
        return aalConApply;
    }

    public void setAalConApply(BigDecimal aalConApply) {
        this.aalConApply = aalConApply;
    }

    public BigDecimal getAalConExamine() {
        return aalConExamine;
    }

    public void setAalConExamine(BigDecimal aalConExamine) {
        this.aalConExamine = aalConExamine;
    }

    public BigDecimal getAalLastTotalPay() {
        return aalLastTotalPay;
    }

    public void setAalLastTotalPay(BigDecimal aalLastTotalPay) {
        this.aalLastTotalPay = aalLastTotalPay;
    }

    public BigDecimal getAalLastTotalPayExa() {
        return aalLastTotalPayExa;
    }

    public void setAalLastTotalPayExa(BigDecimal aalLastTotalPayExa) {
        this.aalLastTotalPayExa = aalLastTotalPayExa;
    }

    public BigDecimal getAalLastTotalPayL() {
        return aalLastTotalPayL;
    }

    public void setAalLastTotalPayL(BigDecimal aalLastTotalPayL) {
        this.aalLastTotalPayL = aalLastTotalPayL;
    }

    public BigDecimal getAalLastTotalPayLexa() {
        return aalLastTotalPayLexa;
    }

    public void setAalLastTotalPayLexa(BigDecimal aalLastTotalPayLexa) {
        this.aalLastTotalPayLexa = aalLastTotalPayLexa;
    }

    public BigDecimal getAalViolateMoney() {
        return aalViolateMoney;
    }

    public void setAalViolateMoney(BigDecimal aalViolateMoney) {
        this.aalViolateMoney = aalViolateMoney;
    }

    public BigDecimal getAalViolateMoneyExa() {
        return aalViolateMoneyExa;
    }

    public void setAalViolateMoneyExa(BigDecimal aalViolateMoneyExa) {
        this.aalViolateMoneyExa = aalViolateMoneyExa;
    }

    public BigDecimal getAalThisPayMoney() {
        return aalThisPayMoney;
    }

    public void setAalThisPayMoney(BigDecimal aalThisPayMoney) {
        this.aalThisPayMoney = aalThisPayMoney;
    }

    public BigDecimal getAalThisPayMoneyExa() {
        return aalThisPayMoneyExa;
    }

    public void setAalThisPayMoneyExa(BigDecimal aalThisPayMoneyExa) {
        this.aalThisPayMoneyExa = aalThisPayMoneyExa;
    }

    public BigDecimal getAalSurplusMoney() {
        return aalSurplusMoney;
    }

    public void setAalSurplusMoney(BigDecimal aalSurplusMoney) {
        this.aalSurplusMoney = aalSurplusMoney;
    }

    public BigDecimal getAalSurplusMoneyExa() {
        return aalSurplusMoneyExa;
    }

    public void setAalSurplusMoneyExa(BigDecimal aalSurplusMoneyExa) {
        this.aalSurplusMoneyExa = aalSurplusMoneyExa;
    }

    public Date getAalCreateTime() {
        return aalCreateTime;
    }

    public void setAalCreateTime(Date aalCreateTime) {
        this.aalCreateTime = aalCreateTime;
    }

    public String getAalCreateUid() {
        return aalCreateUid;
    }

    public void setAalCreateUid(String aalCreateUid) {
        this.aalCreateUid = aalCreateUid;
    }

    public String getAalCreateUname() {
        return aalCreateUname;
    }

    public void setAalCreateUname(String aalCreateUname) {
        this.aalCreateUname = aalCreateUname;
    }

    public Integer getAalState() {
        return aalState;
    }

    public void setAalState(Integer aalState) {
        this.aalState = aalState;
    }

    public String getAalStateTime() {
        return aalStateTime;
    }

    public void setAalStateTime(String aalStateTime) {
        this.aalStateTime = aalStateTime;
    }

    public BigDecimal getAalSurplusMoneyAgent() {
        return aalSurplusMoneyAgent;
    }

    public void setAalSurplusMoneyAgent(BigDecimal aalSurplusMoneyAgent) {
        this.aalSurplusMoneyAgent = aalSurplusMoneyAgent;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }


    public String getScTitle() {
        return scTitle;
    }

    public void setScTitle(String scTitle) {
        this.scTitle = scTitle;
    }

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }

    public String getGuaranteeIds() {
        return guaranteeIds;
    }

    public void setGuaranteeIds(String guaranteeIds) {
        this.guaranteeIds = guaranteeIds;
    }

    public String getLiquidatedIds() {
        return liquidatedIds;
    }

    public void setLiquidatedIds(String liquidatedIds) {
        this.liquidatedIds = liquidatedIds;
    }

    public String getAalApplicantName() {
        return aalApplicantName;
    }

    public void setAalApplicantName(String aalApplicantName) {
        this.aalApplicantName = aalApplicantName;
    }

    public String getAalApplicantAbbName() {
        return aalApplicantAbbName;
    }

    public void setAalApplicantAbbName(String aalApplicantAbbName) {
        this.aalApplicantAbbName = aalApplicantAbbName;
    }

    public BigDecimal getAalLastTotalReal() {
        return aalLastTotalReal;
    }

    public void setAalLastTotalReal(BigDecimal aalLastTotalReal) {
        this.aalLastTotalReal = aalLastTotalReal;
    }

    public Integer getAalPaySettled() {
        return aalPaySettled;
    }

    public void setAalPaySettled(Integer aalPaySettled) {
        this.aalPaySettled = aalPaySettled;
    }

    public BigDecimal getAalConSettlementPrice() {
        return aalConSettlementPrice;
    }

    public void setAalConSettlementPrice(BigDecimal aalConSettlementPrice) {
        this.aalConSettlementPrice = aalConSettlementPrice;
    }
}
