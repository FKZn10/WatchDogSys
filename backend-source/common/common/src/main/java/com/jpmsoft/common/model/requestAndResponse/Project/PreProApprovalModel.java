package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目立项
 */
public class PreProApprovalModel extends ProjectCommonRes {

    private String paId;
    private String pId;
    private String paName;
    private String paCode;
    private String paType;
    private String paScale;
    private String paOverview;
    private String paConstructionUnitId;
    private Date paPlanStart;
    private Date paPlanEnd;
    private String paPmId;
    private String paPmName;
    private String paPmTel;
    private String paNature;
    private BigDecimal paTotalInv;
    private String paRemark;
    private Integer paState;
    private Date paStateTime;
    private Date paCreateTime;
    private String paCreateUid;
    private String paCreateUname;
    private String paGroupDoc;
    private String paSasacDoc;
    private String paReformDoc;
    private Integer paIsParticipate;

    private String paConstructionUnitName;
    private String paConstructionUnitNameHistory;
    private String paConsAbbName;
    private String paGroupDocIds;
    private String paSasacDocIds;
    private String paReformDocIds;
    private List<String> paTotalInvs;
    private List<String> paCreateTimes;
    private List<ProFundingSourceModel> proFundingSources;

    private List<PreProApprovalDtlModel> preProApprovalDtl;

    public String getPaId() {
        return paId;
    }

    public void setPaId(String paId) {
        this.paId = paId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPaName() {
        return paName;
    }

    public void setPaName(String paName) {
        this.paName = paName;
    }

    public String getPaCode() {
        return paCode;
    }

    public void setPaCode(String paCode) {
        this.paCode = paCode;
    }

    public String getPaType() {
        return paType;
    }

    public void setPaType(String paType) {
        this.paType = paType;
    }

    public String getPaScale() {
        return paScale;
    }

    public void setPaScale(String paScale) {
        this.paScale = paScale;
    }

    public String getPaOverview() {
        return paOverview;
    }

    public void setPaOverview(String paOverview) {
        this.paOverview = paOverview;
    }

    public String getPaConstructionUnitId() {
        return paConstructionUnitId;
    }

    public void setPaConstructionUnitId(String paConstructionUnitId) {
        this.paConstructionUnitId = paConstructionUnitId;
    }

    public Date getPaPlanStart() {
        return paPlanStart;
    }

    public void setPaPlanStart(Date paPlanStart) {
        this.paPlanStart = paPlanStart;
    }

    public Date getPaPlanEnd() {
        return paPlanEnd;
    }

    public void setPaPlanEnd(Date paPlanEnd) {
        this.paPlanEnd = paPlanEnd;
    }

    public String getPaPmId() {
        return paPmId;
    }

    public void setPaPmId(String paPmId) {
        this.paPmId = paPmId;
    }

    public String getPaPmName() {
        return paPmName;
    }

    public void setPaPmName(String paPmName) {
        this.paPmName = paPmName;
    }

    public String getPaPmTel() {
        return paPmTel;
    }

    public void setPaPmTel(String paPmTel) {
        this.paPmTel = paPmTel;
    }

    public String getPaNature() {
        return paNature;
    }

    public void setPaNature(String paNature) {
        this.paNature = paNature;
    }

    public BigDecimal getPaTotalInv() {
        return paTotalInv;
    }

    public void setPaTotalInv(BigDecimal paTotalInv) {
        this.paTotalInv = paTotalInv;
    }

    public String getPaRemark() {
        return paRemark;
    }

    public void setPaRemark(String paRemark) {
        this.paRemark = paRemark;
    }

    public Integer getPaState() {
        return paState;
    }

    public void setPaState(Integer paState) {
        this.paState = paState;
    }

    public Date getPaStateTime() {
        return paStateTime;
    }

    public void setPaStateTime(Date paStateTime) {
        this.paStateTime = paStateTime;
    }

    public Date getPaCreateTime() {
        return paCreateTime;
    }

    public void setPaCreateTime(Date paCreateTime) {
        this.paCreateTime = paCreateTime;
    }

    public String getPaCreateUid() {
        return paCreateUid;
    }

    public void setPaCreateUid(String paCreateUid) {
        this.paCreateUid = paCreateUid;
    }

    public String getPaCreateUname() {
        return paCreateUname;
    }

    public void setPaCreateUname(String paCreateUname) {
        this.paCreateUname = paCreateUname;
    }

    public String getPaConstructionUnitName() {
        return paConstructionUnitName;
    }

    public void setPaConstructionUnitName(String paConstructionUnitName) {
        this.paConstructionUnitName = paConstructionUnitName;
    }

    public String getPaConstructionUnitNameHistory() {
        return paConstructionUnitNameHistory;
    }

    public void setPaConstructionUnitNameHistory(String paConstructionUnitNameHistory) {
        this.paConstructionUnitNameHistory = paConstructionUnitNameHistory;
    }


    public List<String> getPaTotalInvs() {
        return paTotalInvs;
    }

    public void setPaTotalInvs(List<String> paTotalInvs) {
        this.paTotalInvs = paTotalInvs;
    }

    public List<ProFundingSourceModel> getProFundingSources() {
        return proFundingSources;
    }

    public void setProFundingSources(List<ProFundingSourceModel> proFundingSources) {
        this.proFundingSources = proFundingSources;
    }

    public String getPaGroupDoc() {
        return paGroupDoc;
    }

    public void setPaGroupDoc(String paGroupDoc) {
        this.paGroupDoc = paGroupDoc;
    }

    public String getPaSasacDoc() {
        return paSasacDoc;
    }

    public void setPaSasacDoc(String paSasacDoc) {
        this.paSasacDoc = paSasacDoc;
    }

    public String getPaReformDoc() {
        return paReformDoc;
    }

    public void setPaReformDoc(String paReformDoc) {
        this.paReformDoc = paReformDoc;
    }

    public Integer getPaIsParticipate() {
        return paIsParticipate;
    }

    public void setPaIsParticipate(Integer paIsParticipate) {
        this.paIsParticipate = paIsParticipate;
    }

    public String getPaGroupDocIds() {
        return paGroupDocIds;
    }

    public void setPaGroupDocIds(String paGroupDocIds) {
        this.paGroupDocIds = paGroupDocIds;
    }

    public String getPaSasacDocIds() {
        return paSasacDocIds;
    }

    public void setPaSasacDocIds(String paSasacDocIds) {
        this.paSasacDocIds = paSasacDocIds;
    }

    public String getPaReformDocIds() {
        return paReformDocIds;
    }

    public void setPaReformDocIds(String paReformDocIds) {
        this.paReformDocIds = paReformDocIds;
    }

    public String getPaConsAbbName() {
        return paConsAbbName;
    }

    public void setPaConsAbbName(String paConsAbbName) {
        this.paConsAbbName = paConsAbbName;
    }

    public List<String> getPaCreateTimes() {
        return paCreateTimes;
    }

    public void setPaCreateTimes(List<String> paCreateTimes) {
        this.paCreateTimes = paCreateTimes;
    }


    public List<PreProApprovalDtlModel> getPreProApprovalDtl() {
        return preProApprovalDtl;
    }

    public void setPreProApprovalDtl(List<PreProApprovalDtlModel> preProApprovalDtl) {
        this.preProApprovalDtl = preProApprovalDtl;
    }

}
