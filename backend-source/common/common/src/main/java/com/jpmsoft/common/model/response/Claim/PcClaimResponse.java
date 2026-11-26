package com.jpmsoft.common.model.response.Claim;


import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 索赔
 */

public class PcClaimResponse extends ProjectCommonRes {

    private String claimId;
    private Integer claimType;
    private String pId;
    private String conId;
    private String claimFp;
    private String claimSp;
    private BigDecimal claimConMoney;
    private String claimConState;
    private String claimTitle;
    private String claimContent;
    private String claimRemark;
    private String claimCreateUid;
    private String claimCreateUname;
    private Date claimCreateTime;
    private Integer claimState;
    private Date claimStateTime;


    private String conName;
    private String claimFpName;
    private String claimSpName;
    private String  claimFpNameHistory;
    private String claimSpNameHistory;
    private List<PcClaimDtlResponse> pcClaimDtls;
    //单位简称
    private String claimFpAbbName;
    private String claimSpAbbName;

    /**
     * 流程编码
     */
    private String processNumber;

    public String getProcessNumber() {
        return processNumber;
    }

    public void setProcessNumber(String processNumber) {
        this.processNumber = processNumber;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public Integer getClaimType() {
        return claimType;
    }

    public void setClaimType(Integer claimType) {
        this.claimType = claimType;
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

    public String getClaimFp() {
        return claimFp;
    }

    public void setClaimFp(String claimFp) {
        this.claimFp = claimFp;
    }

    public String getClaimSp() {
        return claimSp;
    }

    public void setClaimSp(String claimSp) {
        this.claimSp = claimSp;
    }

    public BigDecimal getClaimConMoney() {
        return claimConMoney;
    }

    public void setClaimConMoney(BigDecimal claimConMoney) {
        this.claimConMoney = claimConMoney;
    }

    public String getClaimConState() {
        return claimConState;
    }

    public void setClaimConState(String claimConState) {
        this.claimConState = claimConState;
    }

    public String getClaimTitle() {
        return claimTitle;
    }

    public void setClaimTitle(String claimTitle) {
        this.claimTitle = claimTitle;
    }

    public String getClaimContent() {
        return claimContent;
    }

    public void setClaimContent(String claimContent) {
        this.claimContent = claimContent;
    }

    public String getClaimRemark() {
        return claimRemark;
    }

    public void setClaimRemark(String claimRemark) {
        this.claimRemark = claimRemark;
    }

    public String getClaimCreateUid() {
        return claimCreateUid;
    }

    public void setClaimCreateUid(String claimCreateUid) {
        this.claimCreateUid = claimCreateUid;
    }

    public String getClaimCreateUname() {
        return claimCreateUname;
    }

    public void setClaimCreateUname(String claimCreateUname) {
        this.claimCreateUname = claimCreateUname;
    }

    public Date getClaimCreateTime() {
        return claimCreateTime;
    }

    public void setClaimCreateTime(Date claimCreateTime) {
        this.claimCreateTime = claimCreateTime;
    }

    public Integer getClaimState() {
        return claimState;
    }

    public void setClaimState(Integer claimState) {
        this.claimState = claimState;
    }

    public Date getClaimStateTime() {
        return claimStateTime;
    }

    public void setClaimStateTime(Date claimStateTime) {
        this.claimStateTime = claimStateTime;
    }


    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getClaimFpName() {
        return claimFpName;
    }

    public void setClaimFpName(String claimFpName) {
        this.claimFpName = claimFpName;
    }

    public String getClaimSpName() {
        return claimSpName;
    }

    public void setClaimSpName(String claimSpName) {
        this.claimSpName = claimSpName;
    }


    public String getClaimFpNameHistory() {
        return claimFpNameHistory;
    }

    public void setClaimFpNameHistory(String claimFpNameHistory) {
        this.claimFpNameHistory = claimFpNameHistory;
    }

    public String getClaimSpNameHistory() {
        return claimSpNameHistory;
    }

    public void setClaimSpNameHistory(String claimSpNameHistory) {
        this.claimSpNameHistory = claimSpNameHistory;
    }

    public List<PcClaimDtlResponse> getPcClaimDtls() {
        return pcClaimDtls;
    }

    public void setPcClaimDtls(List<PcClaimDtlResponse> pcClaimDtls) {
        this.pcClaimDtls = pcClaimDtls;
    }

    public String getClaimFpAbbName() {
        return claimFpAbbName;
    }

    public void setClaimFpAbbName(String claimFpAbbName) {
        this.claimFpAbbName = claimFpAbbName;
    }

    public String getClaimSpAbbName() {
        return claimSpAbbName;
    }

    public void setClaimSpAbbName(String claimSpAbbName) {
        this.claimSpAbbName = claimSpAbbName;
    }
}
