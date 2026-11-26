package com.jpmsoft.common.model.response.SecInfo;

import com.jpmsoft.common.model.BaseFileResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInfoSubjectRemarkDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 招标计划
 */
public class SecBiddingPlanResponse extends BaseFileResponse {

    private String bpId;
    private String bpName;
    private Integer bpYear;
    private Integer bpMonth;
    private String bpRemark;
    private String bpCreateUid;
    private String bpCreateUname;
    private String bpCreateCid;
    private Date bpCreateTime;
    private Date stateTime;
    private Integer state;
    private String bpTenderee;
    private String bpApprovalTitle;
    private String bpApprovalNum;
    private Date bpApprovalTime;
    private String bpApprovalFile;

    private Date bpSecTime;
    private String bpCid;
    private String bpDid;
    private String bpCnature;
    private String bpDnature;
    private Date bpTime;
    private String bpSecContent;
    private BigDecimal bpGsAmt;
    private String bpBiddingType;
    private Date bpEndTime;
    private String bpSecName;
    private Boolean bpGczb;
    private String bpProScale;
    private BigDecimal bpProAmt;
    private String bpFundSource;
    private String bpScopeOverview;
    private String bpSm;
    private BigDecimal bpEsAmt;
    //委托类型
    private String bpEntrustmentType;
    /**
     * 集团是否为见证方 1：是 0：否
     */
    private Integer bpGroupWitness;
    /**
     * 项目投资控制情况
     */
    private String bpInvControlSituation;
    //管理类别
    private String bpManagementCategory;

    private String bpCname;

    private String bpTendereeName;
    private String pId;
    private BigDecimal secMoneys;
    private Integer secInfoTotal;
    /**
     * 已完成数量（根据招标方式统计）
     */
    private Integer finishCount;
    private String bpSign;
    private List<SecProjectResponse> secProjects;
    private List<SecInfoResponse> secInfos;

    private Integer isAllProcessAsk;

    private String isAllProcessMsg;

    /**
     * 计划阶段
     */
    private String bpPlanPhase;
    /*
     * 项目规模
     */
    private String projectCategory;
    /*
     * 目发起单位
     */
    private String projectInitiatingUnit;

    private String isNew;

    private Boolean hasChange;

    private List<SecInfoSubjectRemarkDTO> remarkList;

    private List<SecInfoChangeRes> secInfoChangeList;

    private String responsibleLeader;

    private String responsibleLeaderName;

    private String reserveItems;

    /**
     * 是否录入估算
     */
    private String enterEstimate;

    public String getEnterEstimate() {
        return enterEstimate;
    }

    public void setEnterEstimate(String enterEstimate) {
        this.enterEstimate = enterEstimate;
    }

    public String getReserveItems() {
        return reserveItems;
    }

    public void setReserveItems(String reserveItems) {
        this.reserveItems = reserveItems;
    }

    public String getBpCreateCid() {
        return bpCreateCid;
    }

    public void setBpCreateCid(String bpCreateCid) {
        this.bpCreateCid = bpCreateCid;
    }

    public List<SecInfoChangeRes> getSecInfoChangeList() {
        return secInfoChangeList;
    }

    public void setSecInfoChangeList(List<SecInfoChangeRes> secInfoChangeList) {
        this.secInfoChangeList = secInfoChangeList;
    }

    public String getResponsibleLeader() {
        return responsibleLeader;
    }

    public void setResponsibleLeader(String responsibleLeader) {
        this.responsibleLeader = responsibleLeader;
    }

    public String getResponsibleLeaderName() {
        return responsibleLeaderName;
    }

    public void setResponsibleLeaderName(String responsibleLeaderName) {
        this.responsibleLeaderName = responsibleLeaderName;
    }

    public List<SecInfoSubjectRemarkDTO> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(List<SecInfoSubjectRemarkDTO> remarkList) {
        this.remarkList = remarkList;
    }

    public Boolean getHasChange() {
        return hasChange;
    }

    public void setHasChange(Boolean hasChange) {
        this.hasChange = hasChange;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getProjectInitiatingUnit() {
        return projectInitiatingUnit;
    }

    public void setProjectInitiatingUnit(String projectInitiatingUnit) {
        this.projectInitiatingUnit = projectInitiatingUnit;
    }

    public String getBpPlanPhase() {
        return bpPlanPhase;
    }

    public void setBpPlanPhase(String bpPlanPhase) {
        this.bpPlanPhase = bpPlanPhase;
    }

    public Integer getIsAllProcessAsk() {
        return isAllProcessAsk;
    }

    public void setIsAllProcessAsk(Integer isAllProcessAsk) {
        this.isAllProcessAsk = isAllProcessAsk;
    }

    public String getIsAllProcessMsg() {
        return isAllProcessMsg;
    }

    public void setIsAllProcessMsg(String isAllProcessMsg) {
        this.isAllProcessMsg = isAllProcessMsg;
    }

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }

    public String getBpName() {
        return bpName;
    }

    public void setBpName(String bpName) {
        this.bpName = bpName;
    }

    public Integer getBpYear() {
        return bpYear;
    }

    public void setBpYear(Integer bpYear) {
        this.bpYear = bpYear;
    }

    public Integer getBpMonth() {
        return bpMonth;
    }

    public void setBpMonth(Integer bpMonth) {
        this.bpMonth = bpMonth;
    }

    public String getBpRemark() {
        return bpRemark;
    }

    public void setBpRemark(String bpRemark) {
        this.bpRemark = bpRemark;
    }

    public String getBpCreateUid() {
        return bpCreateUid;
    }

    public void setBpCreateUid(String bpCreateUid) {
        this.bpCreateUid = bpCreateUid;
    }

    public String getBpCreateUname() {
        return bpCreateUname;
    }

    public void setBpCreateUname(String bpCreateUname) {
        this.bpCreateUname = bpCreateUname;
    }

    public Date getBpCreateTime() {
        return bpCreateTime;
    }

    public void setBpCreateTime(Date bpCreateTime) {
        this.bpCreateTime = bpCreateTime;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }


    public String getBpTenderee() {
        return bpTenderee;
    }

    public void setBpTenderee(String bpTenderee) {
        this.bpTenderee = bpTenderee;
    }

    public String getBpApprovalTitle() {
        return bpApprovalTitle;
    }

    public void setBpApprovalTitle(String bpApprovalTitle) {
        this.bpApprovalTitle = bpApprovalTitle;
    }

    public String getBpApprovalNum() {
        return bpApprovalNum;
    }

    public void setBpApprovalNum(String bpApprovalNum) {
        this.bpApprovalNum = bpApprovalNum;
    }

    public Date getBpApprovalTime() {
        return bpApprovalTime;
    }

    public void setBpApprovalTime(Date bpApprovalTime) {
        this.bpApprovalTime = bpApprovalTime;
    }

    public String getBpApprovalFile() {
        return bpApprovalFile;
    }

    public void setBpApprovalFile(String bpApprovalFile) {
        this.bpApprovalFile = bpApprovalFile;
    }

    public Date getBpSecTime() {
        return bpSecTime;
    }

    public void setBpSecTime(Date bpSecTime) {
        this.bpSecTime = bpSecTime;
    }

    public String getBpCid() {
        return bpCid;
    }

    public void setBpCid(String bpCid) {
        this.bpCid = bpCid;
    }

    public String getBpDid() {
        return bpDid;
    }

    public void setBpDid(String bpDid) {
        this.bpDid = bpDid;
    }

    public String getBpCnature() {
        return bpCnature;
    }

    public void setBpCnature(String bpCnature) {
        this.bpCnature = bpCnature;
    }

    public String getBpDnature() {
        return bpDnature;
    }

    public void setBpDnature(String bpDnature) {
        this.bpDnature = bpDnature;
    }

    public Date getBpTime() {
        return bpTime;
    }

    public void setBpTime(Date bpTime) {
        this.bpTime = bpTime;
    }

    public String getBpSecContent() {
        return bpSecContent;
    }

    public void setBpSecContent(String bpSecContent) {
        this.bpSecContent = bpSecContent;
    }

    public BigDecimal getBpGsAmt() {
        return bpGsAmt;
    }

    public void setBpGsAmt(BigDecimal bpGsAmt) {
        this.bpGsAmt = bpGsAmt;
    }

    public String getBpBiddingType() {
        return bpBiddingType;
    }

    public void setBpBiddingType(String bpBiddingType) {
        this.bpBiddingType = bpBiddingType;
    }

    public Date getBpEndTime() {
        return bpEndTime;
    }

    public void setBpEndTime(Date bpEndTime) {
        this.bpEndTime = bpEndTime;
    }

    public String getBpSecName() {
        return bpSecName;
    }

    public void setBpSecName(String bpSecName) {
        this.bpSecName = bpSecName;
    }

    public Boolean getBpGczb() {
        return bpGczb;
    }

    public void setBpGczb(Boolean bpGczb) {
        this.bpGczb = bpGczb;
    }

    public String getBpProScale() {
        return bpProScale;
    }

    public void setBpProScale(String bpProScale) {
        this.bpProScale = bpProScale;
    }

    public BigDecimal getBpProAmt() {
        return bpProAmt;
    }

    public void setBpProAmt(BigDecimal bpProAmt) {
        this.bpProAmt = bpProAmt;
    }

    public String getBpFundSource() {
        return bpFundSource;
    }

    public void setBpFundSource(String bpFundSource) {
        this.bpFundSource = bpFundSource;
    }

    public String getBpScopeOverview() {
        return bpScopeOverview;
    }

    public void setBpScopeOverview(String bpScopeOverview) {
        this.bpScopeOverview = bpScopeOverview;
    }

    public String getBpSm() {
        return bpSm;
    }

    public void setBpSm(String bpSm) {
        this.bpSm = bpSm;
    }

    public BigDecimal getBpEsAmt() {
        return bpEsAmt;
    }

    public void setBpEsAmt(BigDecimal bpEsAmt) {
        this.bpEsAmt = bpEsAmt;
    }

    public String getBpEntrustmentType() {
        return bpEntrustmentType;
    }

    public void setBpEntrustmentType(String bpEntrustmentType) {
        this.bpEntrustmentType = bpEntrustmentType;
    }

    public Integer getBpGroupWitness() {
        return bpGroupWitness;
    }

    public void setBpGroupWitness(Integer bpGroupWitness) {
        this.bpGroupWitness = bpGroupWitness;
    }

    public String getBpInvControlSituation() {
        return bpInvControlSituation;
    }

    public void setBpInvControlSituation(String bpInvControlSituation) {
        this.bpInvControlSituation = bpInvControlSituation;
    }

    public String getBpManagementCategory() {
        return bpManagementCategory;
    }

    public void setBpManagementCategory(String bpManagementCategory) {
        this.bpManagementCategory = bpManagementCategory;
    }

    public String getBpCname() {
        return bpCname;
    }

    public void setBpCname(String bpCname) {
        this.bpCname = bpCname;
    }

    public String getBpTendereeName() {
        return bpTendereeName;
    }

    public void setBpTendereeName(String bpTendereeName) {
        this.bpTendereeName = bpTendereeName;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public List<SecInfoResponse> getSecInfos() {
        return secInfos;
    }

    public void setSecInfos(List<SecInfoResponse> secInfos) {
        this.secInfos = secInfos;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public BigDecimal getSecMoneys() {
        return secMoneys;
    }

    public void setSecMoneys(BigDecimal secMoneys) {
        this.secMoneys = secMoneys;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }

    public String getBpSign() {
        return bpSign;
    }

    public void setBpSign(String bpSign) {
        this.bpSign = bpSign;
    }

    public Integer getSecInfoTotal() {
        return secInfoTotal;
    }

    public void setSecInfoTotal(Integer secInfoTotal) {
        this.secInfoTotal = secInfoTotal;
    }
}
