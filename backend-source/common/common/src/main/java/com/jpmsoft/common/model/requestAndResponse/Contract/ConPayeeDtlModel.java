package com.jpmsoft.common.model.requestAndResponse.Contract;

import java.util.Date;

/**
 * 合同收款单位明细
 */
public class ConPayeeDtlModel {

    private String cpdId;
    private String conId;
    private String cpdCName;
    private String cpdCId;
    private String cpdBank;
    private String cpdBankAccount;
    private String cpdCreateUid;
    private Date cpdCreateTime;
    private String cpdCreateUname;
    private String cpdAccountBankName;
    private String cpdAccountBankNum;

    public String getCpdId() {
        return cpdId;
    }

    public void setCpdId(String cpdId) {
        this.cpdId = cpdId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getCpdCName() {
        return cpdCName;
    }

    public void setCpdCName(String cpdCName) {
        this.cpdCName = cpdCName;
    }

    public String getCpdCId() {
        return cpdCId;
    }

    public void setCpdCId(String cpdCId) {
        this.cpdCId = cpdCId;
    }

    public String getCpdBank() {
        return cpdBank;
    }

    public void setCpdBank(String cpdBank) {
        this.cpdBank = cpdBank;
    }

    public String getCpdBankAccount() {
        return cpdBankAccount;
    }

    public void setCpdBankAccount(String cpdBankAccount) {
        this.cpdBankAccount = cpdBankAccount;
    }

    public String getCpdCreateUid() {
        return cpdCreateUid;
    }

    public void setCpdCreateUid(String cpdCreateUid) {
        this.cpdCreateUid = cpdCreateUid;
    }

    public Date getCpdCreateTime() {
        return cpdCreateTime;
    }

    public void setCpdCreateTime(Date cpdCreateTime) {
        this.cpdCreateTime = cpdCreateTime;
    }

    public String getCpdCreateUname() {
        return cpdCreateUname;
    }

    public void setCpdCreateUname(String cpdCreateUname) {
        this.cpdCreateUname = cpdCreateUname;
    }

    public String getCpdAccountBankName() {
        return cpdAccountBankName;
    }

    public void setCpdAccountBankName(String cpdAccountBankName) {
        this.cpdAccountBankName = cpdAccountBankName;
    }

    public String getCpdAccountBankNum() {
        return cpdAccountBankNum;
    }

    public void setCpdAccountBankNum(String cpdAccountBankNum) {
        this.cpdAccountBankNum = cpdAccountBankNum;
    }
}
