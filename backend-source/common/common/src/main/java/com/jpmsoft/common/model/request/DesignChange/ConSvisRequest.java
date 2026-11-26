package com.jpmsoft.common.model.request.DesignChange;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 现场签证指令单
 */
public class ConSvisRequest extends BaseFileRequest {

    private String svisId;
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

    private String pName;
    private String conName;
    /**
     * true过滤
     * Con_Sv_Budget 表选 Con_Svis 除作废的， 仅仅只能关联一次
     */
    private boolean filterSvBudget;
    public String getSvisId() {
        return svisId;
    }

    public void setSvisId(String svisId) {
        this.svisId = svisId;
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

    public boolean isFilterSvBudget() {
        return filterSvBudget;
    }

    public void setFilterSvBudget(boolean filterSvBudget) {
        this.filterSvBudget = filterSvBudget;
    }
}
