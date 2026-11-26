package com.jpmsoft.common.model.requestAndResponse.Design;

import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目建议书
 */
public class DesignProposalModel extends ProjectCommonRes {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String dpId;
    private String dpName;
    private String pId;
    private BigDecimal dpInvestment;
    private String dpFeasibilityUnitId;
    private String dpEstimationUnitId;
    @FileFields(index = 1)
    private String dpInvestmentFile;
    private String dpRemark;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String dpCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String dpCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date dpCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer dpState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date dpStateTime;
    private String conId;
    private String dpCode;
    private String dpFeasibilityUnitTel;
    private Date dpCompilationTime;

    private String conName;
    private String conCode;
    private String dpFeasibilityUnitName;
    private String dpEstimationUnitName;
    private String dpFeasibilityUnitNameHistory;
    private String dpEstimationUnitNameHistory;
    @FileFields(index = 1, type = "dpInvestmentFileIds")
    private String dpInvestmentFileIds;
    //单位简称
    private String dpFeasibilityUnitAbbName;
    private String dpEstimationUnitAbbName;


    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getDpName() {
        return dpName;
    }

    public void setDpName(String dpName) {
        this.dpName = dpName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public BigDecimal getDpInvestment() {
        return dpInvestment;
    }

    public void setDpInvestment(BigDecimal dpInvestment) {
        this.dpInvestment = dpInvestment;
    }

    public String getDpFeasibilityUnitId() {
        return dpFeasibilityUnitId;
    }

    public void setDpFeasibilityUnitId(String dpFeasibilityUnitId) {
        this.dpFeasibilityUnitId = dpFeasibilityUnitId;
    }

    public String getDpEstimationUnitId() {
        return dpEstimationUnitId;
    }

    public void setDpEstimationUnitId(String dpEstimationUnitId) {
        this.dpEstimationUnitId = dpEstimationUnitId;
    }

    public String getDpInvestmentFile() {
        return dpInvestmentFile;
    }

    public void setDpInvestmentFile(String dpInvestmentFile) {
        this.dpInvestmentFile = dpInvestmentFile;
    }

    public String getDpRemark() {
        return dpRemark;
    }

    public void setDpRemark(String dpRemark) {
        this.dpRemark = dpRemark;
    }

    public String getDpCreateUid() {
        return dpCreateUid;
    }

    public void setDpCreateUid(String dpCreateUid) {
        this.dpCreateUid = dpCreateUid;
    }

    public String getDpCreateUname() {
        return dpCreateUname;
    }

    public void setDpCreateUname(String dpCreateUname) {
        this.dpCreateUname = dpCreateUname;
    }

    public Date getDpCreateTime() {
        return dpCreateTime;
    }

    public void setDpCreateTime(Date dpCreateTime) {
        this.dpCreateTime = dpCreateTime;
    }

    public Integer getDpState() {
        return dpState;
    }

    public void setDpState(Integer dpState) {
        this.dpState = dpState;
    }

    public Date getDpStateTime() {
        return dpStateTime;
    }

    public void setDpStateTime(Date dpStateTime) {
        this.dpStateTime = dpStateTime;
    }

    @Override
    public String getConId() {
        return conId;
    }

    @Override
    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getDpCode() {
        return dpCode;
    }

    public void setDpCode(String dpCode) {
        this.dpCode = dpCode;
    }

    public String getDpFeasibilityUnitTel() {
        return dpFeasibilityUnitTel;
    }

    public void setDpFeasibilityUnitTel(String dpFeasibilityUnitTel) {
        this.dpFeasibilityUnitTel = dpFeasibilityUnitTel;
    }

    public Date getDpCompilationTime() {
        return dpCompilationTime;
    }

    public void setDpCompilationTime(Date dpCompilationTime) {
        this.dpCompilationTime = dpCompilationTime;
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

    public String getDpFeasibilityUnitName() {
        return dpFeasibilityUnitName;
    }

    public void setDpFeasibilityUnitName(String dpFeasibilityUnitName) {
        this.dpFeasibilityUnitName = dpFeasibilityUnitName;
    }

    public String getDpEstimationUnitName() {
        return dpEstimationUnitName;
    }

    public void setDpEstimationUnitName(String dpEstimationUnitName) {
        this.dpEstimationUnitName = dpEstimationUnitName;
    }

    public String getDpFeasibilityUnitNameHistory() {
        return dpFeasibilityUnitNameHistory;
    }

    public void setDpFeasibilityUnitNameHistory(String dpFeasibilityUnitNameHistory) {
        this.dpFeasibilityUnitNameHistory = dpFeasibilityUnitNameHistory;
    }

    public String getDpEstimationUnitNameHistory() {
        return dpEstimationUnitNameHistory;
    }

    public void setDpEstimationUnitNameHistory(String dpEstimationUnitNameHistory) {
        this.dpEstimationUnitNameHistory = dpEstimationUnitNameHistory;
    }

    public String getDpInvestmentFileIds() {
        return dpInvestmentFileIds;
    }

    public void setDpInvestmentFileIds(String dpInvestmentFileIds) {
        this.dpInvestmentFileIds = dpInvestmentFileIds;
    }

    public String getDpFeasibilityUnitAbbName() {
        return dpFeasibilityUnitAbbName;
    }

    public void setDpFeasibilityUnitAbbName(String dpFeasibilityUnitAbbName) {
        this.dpFeasibilityUnitAbbName = dpFeasibilityUnitAbbName;
    }

    public String getDpEstimationUnitAbbName() {
        return dpEstimationUnitAbbName;
    }

    public void setDpEstimationUnitAbbName(String dpEstimationUnitAbbName) {
        this.dpEstimationUnitAbbName = dpEstimationUnitAbbName;
    }
}
