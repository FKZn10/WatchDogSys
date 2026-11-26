package com.jpmsoft.common.model.request.Claim;


import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 索赔
 */

public class PcClaimRequest extends BaseFileRequest {

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

    private String pName;
    private String conName;

    private List<PcClaimDtlRequest> pcClaimDtls;
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

    public List<PcClaimDtlRequest> getPcClaimDtls() {
        return pcClaimDtls;
    }

    public void setPcClaimDtls(List<PcClaimDtlRequest> pcClaimDtls) {
        this.pcClaimDtls = pcClaimDtls;
    }
}
