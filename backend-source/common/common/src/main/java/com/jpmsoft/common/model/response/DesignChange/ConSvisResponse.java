package com.jpmsoft.common.model.response.DesignChange;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 现场签证指令单
 */
public class ConSvisResponse extends ProjectCommonRes {

    private String svisId;
    private String pId;
    private String conId;
    private String svisCode;
    private String svisProjectName;
    private String svisProposedId;
    private String svisMajor;
    private String svisDrawingNo;
    private Date svisDate;
    private String svisContentSummary;
    private String svisContent;
    private String svisCreateUid;
    private String svisCreateUname;
    private Date svisCreateTime;
    private Integer svisState;
    private Date svisStateTime;
    private String svisName;
    private BigDecimal svisContractorAmt;
    private String svisMainCode;
    private String svisSubCode;
    /**
     * 监理预算
     */
    private BigDecimal svisSupervisionAmt;
    private String conName;
    private String svisProposedName;
    private String svisProposedNameHistory;
    //单位简称
    private String svisProposedAbbName;


    public String getSvisId() {
        return svisId;
    }

    public void setSvisId(String svisId) {
        this.svisId = svisId;
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

    public String getSvisCode() {
        return svisCode;
    }

    public void setSvisCode(String svisCode) {
        this.svisCode = svisCode;
    }

    public String getSvisProjectName() {
        return svisProjectName;
    }

    public void setSvisProjectName(String svisProjectName) {
        this.svisProjectName = svisProjectName;
    }

    public String getSvisProposedId() {
        return svisProposedId;
    }

    public void setSvisProposedId(String svisProposedId) {
        this.svisProposedId = svisProposedId;
    }

    public String getSvisMajor() {
        return svisMajor;
    }

    public void setSvisMajor(String svisMajor) {
        this.svisMajor = svisMajor;
    }

    public String getSvisDrawingNo() {
        return svisDrawingNo;
    }

    public void setSvisDrawingNo(String svisDrawingNo) {
        this.svisDrawingNo = svisDrawingNo;
    }

    public Date getSvisDate() {
        return svisDate;
    }

    public void setSvisDate(Date svisDate) {
        this.svisDate = svisDate;
    }

    public String getSvisContentSummary() {
        return svisContentSummary;
    }

    public void setSvisContentSummary(String svisContentSummary) {
        this.svisContentSummary = svisContentSummary;
    }

    public String getSvisContent() {
        return svisContent;
    }

    public void setSvisContent(String svisContent) {
        this.svisContent = svisContent;
    }

    public String getSvisCreateUid() {
        return svisCreateUid;
    }

    public void setSvisCreateUid(String svisCreateUid) {
        this.svisCreateUid = svisCreateUid;
    }

    public String getSvisCreateUname() {
        return svisCreateUname;
    }

    public void setSvisCreateUname(String svisCreateUname) {
        this.svisCreateUname = svisCreateUname;
    }

    public Date getSvisCreateTime() {
        return svisCreateTime;
    }

    public void setSvisCreateTime(Date svisCreateTime) {
        this.svisCreateTime = svisCreateTime;
    }

    public Integer getSvisState() {
        return svisState;
    }

    public void setSvisState(Integer svisState) {
        this.svisState = svisState;
    }

    public Date getSvisStateTime() {
        return svisStateTime;
    }

    public void setSvisStateTime(Date svisStateTime) {
        this.svisStateTime = svisStateTime;
    }

    public String getSvisName() {
        return svisName;
    }

    public void setSvisName(String svisName) {
        this.svisName = svisName;
    }

    public BigDecimal getSvisContractorAmt() {
        return svisContractorAmt;
    }

    public void setSvisContractorAmt(BigDecimal svisContractorAmt) {
        this.svisContractorAmt = svisContractorAmt;
    }

    public String getSvisMainCode() {
        return svisMainCode;
    }

    public void setSvisMainCode(String svisMainCode) {
        this.svisMainCode = svisMainCode;
    }

    public String getSvisSubCode() {
        return svisSubCode;
    }

    public void setSvisSubCode(String svisSubCode) {
        this.svisSubCode = svisSubCode;
    }

    public BigDecimal getSvisSupervisionAmt() {
        return svisSupervisionAmt;
    }

    public void setSvisSupervisionAmt(BigDecimal svisSupervisionAmt) {
        this.svisSupervisionAmt = svisSupervisionAmt;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getSvisProposedName() {
        return svisProposedName;
    }

    public void setSvisProposedName(String svisProposedName) {
        this.svisProposedName = svisProposedName;
    }

    public String getSvisProposedNameHistory() {
        return svisProposedNameHistory;
    }

    public void setSvisProposedNameHistory(String svisProposedNameHistory) {
        this.svisProposedNameHistory = svisProposedNameHistory;
    }

    public String getSvisProposedAbbName() {
        return svisProposedAbbName;
    }

    public void setSvisProposedAbbName(String svisProposedAbbName) {
        this.svisProposedAbbName = svisProposedAbbName;
    }
}
