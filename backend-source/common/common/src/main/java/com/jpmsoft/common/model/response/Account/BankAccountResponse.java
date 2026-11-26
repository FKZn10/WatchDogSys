package com.jpmsoft.common.model.response.Account;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.io.Serializable;
import java.util.Date;

/**
 * 单位账户管理
 */
public class BankAccountResponse extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -5143987067276383357L;
    private String baId;
    private String baCId;
    private String baType;
    private String baName;
    private String baNu;
    private String baBank;
    private String baBankName;
    private String baBankNu;
    private String baCreateUid;
    private String baCreateUname;
    private Date baCreateTime;
    private Integer baState;
    private Date baStateTime;
    private String pId;

    private String baCName;
    private String baCNameHistory;
    private String baCAbbName;

    public String getBaId() {
        return baId;
    }

    public void setBaId(String baId) {
        this.baId = baId;
    }

    public String getBaCId() {
        return baCId;
    }

    public void setBaCId(String baCId) {
        this.baCId = baCId;
    }

    public String getBaType() {
        return baType;
    }

    public void setBaType(String baType) {
        this.baType = baType;
    }

    public String getBaName() {
        return baName;
    }

    public void setBaName(String baName) {
        this.baName = baName;
    }

    public String getBaNu() {
        return baNu;
    }

    public void setBaNu(String baNu) {
        this.baNu = baNu;
    }

    public String getBaBank() {
        return baBank;
    }

    public void setBaBank(String baBank) {
        this.baBank = baBank;
    }

    public String getBaBankName() {
        return baBankName;
    }

    public void setBaBankName(String baBankName) {
        this.baBankName = baBankName;
    }

    public String getBaBankNu() {
        return baBankNu;
    }

    public void setBaBankNu(String baBankNu) {
        this.baBankNu = baBankNu;
    }

    public String getBaCreateUid() {
        return baCreateUid;
    }

    public void setBaCreateUid(String baCreateUid) {
        this.baCreateUid = baCreateUid;
    }

    public String getBaCreateUname() {
        return baCreateUname;
    }

    public void setBaCreateUname(String baCreateUname) {
        this.baCreateUname = baCreateUname;
    }

    public Date getBaCreateTime() {
        return baCreateTime;
    }

    public void setBaCreateTime(Date baCreateTime) {
        this.baCreateTime = baCreateTime;
    }

    public Integer getBaState() {
        return baState;
    }

    public void setBaState(Integer baState) {
        this.baState = baState;
    }

    public Date getBaStateTime() {
        return baStateTime;
    }

    public void setBaStateTime(Date baStateTime) {
        this.baStateTime = baStateTime;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getBaCName() {
        return baCName;
    }

    public void setBaCName(String baCName) {
        this.baCName = baCName;
    }

    public String getBaCNameHistory() {
        return baCNameHistory;
    }

    public void setBaCNameHistory(String baCNameHistory) {
        this.baCNameHistory = baCNameHistory;
    }

    public String getBaCAbbName() {
        return baCAbbName;
    }

    public void setBaCAbbName(String baCAbbName) {
        this.baCAbbName = baCAbbName;
    }
}
