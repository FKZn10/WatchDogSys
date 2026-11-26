package com.jpmsoft.common.model.request.Account;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 合同账户变更
 */
public class BankAccountChangeRequest extends BaseFileRequest {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String bacId;
    private String pId;
    private String conId;
    private String bacName;
    private String bacNu;
    private String bacBank;
    private String bacBankName;
    private String bacBankNu;
    private String baIdN;
    private String bacNameN;
    private String bacNuN;
    private String bacBankN;
    private String bacBankNameN;
    private String bacBankNuN;
    private String bacNameWage;
    private String bacNuWage;
    private String bacBankWage;
    private String bacBankNameWage;
    private String bacBankNuWage;
    private String baIdWageN;
    private String bacNameWageN;
    private String bacNuWageN;
    private String bacBankWageN;
    private String bacBankNameWageN;
    private String bacBankNuWageN;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String bacCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String bacCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date bacCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer bacState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date bacStateTime;

    private String pName;
    private String conName;


    public String getBacId() {
        return bacId;
    }

    public void setBacId(String bacId) {
        this.bacId = bacId;
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

    public String getBacName() {
        return bacName;
    }

    public void setBacName(String bacName) {
        this.bacName = bacName;
    }

    public String getBacNu() {
        return bacNu;
    }

    public void setBacNu(String bacNu) {
        this.bacNu = bacNu;
    }

    public String getBacBank() {
        return bacBank;
    }

    public void setBacBank(String bacBank) {
        this.bacBank = bacBank;
    }

    public String getBacBankName() {
        return bacBankName;
    }

    public void setBacBankName(String bacBankName) {
        this.bacBankName = bacBankName;
    }

    public String getBacBankNu() {
        return bacBankNu;
    }

    public void setBacBankNu(String bacBankNu) {
        this.bacBankNu = bacBankNu;
    }

    public String getBaIdN() {
        return baIdN;
    }

    public void setBaIdN(String baIdN) {
        this.baIdN = baIdN;
    }

    public String getBacNameN() {
        return bacNameN;
    }

    public void setBacNameN(String bacNameN) {
        this.bacNameN = bacNameN;
    }

    public String getBacNuN() {
        return bacNuN;
    }

    public void setBacNuN(String bacNuN) {
        this.bacNuN = bacNuN;
    }

    public String getBacBankN() {
        return bacBankN;
    }

    public void setBacBankN(String bacBankN) {
        this.bacBankN = bacBankN;
    }

    public String getBacBankNameN() {
        return bacBankNameN;
    }

    public void setBacBankNameN(String bacBankNameN) {
        this.bacBankNameN = bacBankNameN;
    }

    public String getBacBankNuN() {
        return bacBankNuN;
    }

    public void setBacBankNuN(String bacBankNuN) {
        this.bacBankNuN = bacBankNuN;
    }

    public String getBacNameWage() {
        return bacNameWage;
    }

    public void setBacNameWage(String bacNameWage) {
        this.bacNameWage = bacNameWage;
    }

    public String getBacNuWage() {
        return bacNuWage;
    }

    public void setBacNuWage(String bacNuWage) {
        this.bacNuWage = bacNuWage;
    }

    public String getBacBankWage() {
        return bacBankWage;
    }

    public void setBacBankWage(String bacBankWage) {
        this.bacBankWage = bacBankWage;
    }

    public String getBacBankNameWage() {
        return bacBankNameWage;
    }

    public void setBacBankNameWage(String bacBankNameWage) {
        this.bacBankNameWage = bacBankNameWage;
    }

    public String getBacBankNuWage() {
        return bacBankNuWage;
    }

    public void setBacBankNuWage(String bacBankNuWage) {
        this.bacBankNuWage = bacBankNuWage;
    }

    public String getBaIdWageN() {
        return baIdWageN;
    }

    public void setBaIdWageN(String baIdWageN) {
        this.baIdWageN = baIdWageN;
    }

    public String getBacNameWageN() {
        return bacNameWageN;
    }

    public void setBacNameWageN(String bacNameWageN) {
        this.bacNameWageN = bacNameWageN;
    }

    public String getBacNuWageN() {
        return bacNuWageN;
    }

    public void setBacNuWageN(String bacNuWageN) {
        this.bacNuWageN = bacNuWageN;
    }

    public String getBacBankWageN() {
        return bacBankWageN;
    }

    public void setBacBankWageN(String bacBankWageN) {
        this.bacBankWageN = bacBankWageN;
    }

    public String getBacBankNameWageN() {
        return bacBankNameWageN;
    }

    public void setBacBankNameWageN(String bacBankNameWageN) {
        this.bacBankNameWageN = bacBankNameWageN;
    }

    public String getBacBankNuWageN() {
        return bacBankNuWageN;
    }

    public void setBacBankNuWageN(String bacBankNuWageN) {
        this.bacBankNuWageN = bacBankNuWageN;
    }

    public String getBacCreateUid() {
        return bacCreateUid;
    }

    public void setBacCreateUid(String bacCreateUid) {
        this.bacCreateUid = bacCreateUid;
    }

    public String getBacCreateUname() {
        return bacCreateUname;
    }

    public void setBacCreateUname(String bacCreateUname) {
        this.bacCreateUname = bacCreateUname;
    }

    public Date getBacCreateTime() {
        return bacCreateTime;
    }

    public void setBacCreateTime(Date bacCreateTime) {
        this.bacCreateTime = bacCreateTime;
    }

    public Integer getBacState() {
        return bacState;
    }

    public void setBacState(Integer bacState) {
        this.bacState = bacState;
    }

    public Date getBacStateTime() {
        return bacStateTime;
    }

    public void setBacStateTime(Date bacStateTime) {
        this.bacStateTime = bacStateTime;
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
}
