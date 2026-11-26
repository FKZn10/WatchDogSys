package com.jpmsoft.common.model.request.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 招标公告
 */

public class SecNoticeRequest extends BaseFileRequest {

    private String nId;
    private String secId;
    private String nBiddingAgency;
    private BigDecimal nAmount;
    private String nScope;
    private Date nPlandate;
    private Date nPlanenddate;
    private Integer nRelease;
    private String nContact;
    private String nContacttel;
    private String nCreateUid;
    private String nCreateUname;
    private Date nCreateTime;
    private Integer nState;
    private Date nStateTime;
    private Date nDate;
    private Date nEnddate;
    private String nBiddingType;
    private String nQeType;
    private String nRemark;
    private BigDecimal nRate;
    private String nOtherValuation;
    private Date nOfficeHours;
    private Date nQuestiTime;
    private Date nPlanDateEnd;
    private Date nQuestiTimeEnd;
    private Date nOfficeHoursEnd;
    private String nInvestorsQuali;
    private String nBidderManagerCon;
    private String nApprovalMatter;
    private String nUrl;
    private String nReceiveJointBidd;
    private String nDeterminType;
    private String nFunction;


    private String secName;
    private String nBiddingAgencyName;
    //招标金额（范围筛选）、
    private List<String> nAmounts;
    //公告计划发布时间（范围筛选）、
    private List<String> nPlandates;
    //计划截标时间（范围筛选）、
    private List<String> nPlanenddates;
    //创建时间（范围筛选）、
    private List<String> nCreateTimes;

    public String getnBiddingAgencyName() {
        return nBiddingAgencyName;
    }

    public void setnBiddingAgencyName(String nBiddingAgencyName) {
        this.nBiddingAgencyName = nBiddingAgencyName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getnBiddingAgency() {
        return nBiddingAgency;
    }

    public void setnBiddingAgency(String nBiddingAgency) {
        this.nBiddingAgency = nBiddingAgency;
    }

    public BigDecimal getnAmount() {
        return nAmount;
    }

    public void setnAmount(BigDecimal nAmount) {
        this.nAmount = nAmount;
    }

    public String getnScope() {
        return nScope;
    }

    public void setnScope(String nScope) {
        this.nScope = nScope;
    }

    public Date getnPlandate() {
        return nPlandate;
    }

    public void setnPlandate(Date nPlandate) {
        this.nPlandate = nPlandate;
    }

    public Date getnPlanenddate() {
        return nPlanenddate;
    }

    public void setnPlanenddate(Date nPlanenddate) {
        this.nPlanenddate = nPlanenddate;
    }

    public Integer getnRelease() {
        return nRelease;
    }

    public void setnRelease(Integer nRelease) {
        this.nRelease = nRelease;
    }

    public String getnContact() {
        return nContact;
    }

    public void setnContact(String nContact) {
        this.nContact = nContact;
    }

    public String getnContacttel() {
        return nContacttel;
    }

    public void setnContacttel(String nContacttel) {
        this.nContacttel = nContacttel;
    }

    public String getnCreateUid() {
        return nCreateUid;
    }

    public void setnCreateUid(String nCreateUid) {
        this.nCreateUid = nCreateUid;
    }

    public String getnCreateUname() {
        return nCreateUname;
    }

    public void setnCreateUname(String nCreateUname) {
        this.nCreateUname = nCreateUname;
    }

    public Date getnCreateTime() {
        return nCreateTime;
    }

    public void setnCreateTime(Date nCreateTime) {
        this.nCreateTime = nCreateTime;
    }

    public Integer getnState() {
        return nState;
    }

    public void setnState(Integer nState) {
        this.nState = nState;
    }

    public Date getnStateTime() {
        return nStateTime;
    }

    public void setnStateTime(Date nStateTime) {
        this.nStateTime = nStateTime;
    }

    public Date getnDate() {
        return nDate;
    }

    public void setnDate(Date nDate) {
        this.nDate = nDate;
    }

    public Date getnEnddate() {
        return nEnddate;
    }

    public void setnEnddate(Date nEnddate) {
        this.nEnddate = nEnddate;
    }

    public String getnBiddingType() {
        return nBiddingType;
    }

    public void setnBiddingType(String nBiddingType) {
        this.nBiddingType = nBiddingType;
    }

    public String getnQeType() {
        return nQeType;
    }

    public void setnQeType(String nQeType) {
        this.nQeType = nQeType;
    }

    public String getnRemark() {
        return nRemark;
    }

    public void setnRemark(String nRemark) {
        this.nRemark = nRemark;
    }

    public BigDecimal getnRate() {
        return nRate;
    }

    public void setnRate(BigDecimal nRate) {
        this.nRate = nRate;
    }

    public String getnOtherValuation() {
        return nOtherValuation;
    }

    public void setnOtherValuation(String nOtherValuation) {
        this.nOtherValuation = nOtherValuation;
    }

    public Date getnOfficeHours() {
        return nOfficeHours;
    }

    public void setnOfficeHours(Date nOfficeHours) {
        this.nOfficeHours = nOfficeHours;
    }

    public Date getnQuestiTime() {
        return nQuestiTime;
    }

    public void setnQuestiTime(Date nQuestiTime) {
        this.nQuestiTime = nQuestiTime;
    }

    public Date getnPlanDateEnd() {
        return nPlanDateEnd;
    }

    public void setnPlanDateEnd(Date nPlanDateEnd) {
        this.nPlanDateEnd = nPlanDateEnd;
    }

    public Date getnQuestiTimeEnd() {
        return nQuestiTimeEnd;
    }

    public void setnQuestiTimeEnd(Date nQuestiTimeEnd) {
        this.nQuestiTimeEnd = nQuestiTimeEnd;
    }

    public Date getnOfficeHoursEnd() {
        return nOfficeHoursEnd;
    }

    public void setnOfficeHoursEnd(Date nOfficeHoursEnd) {
        this.nOfficeHoursEnd = nOfficeHoursEnd;
    }

    public String getnInvestorsQuali() {
        return nInvestorsQuali;
    }

    public void setnInvestorsQuali(String nInvestorsQuali) {
        this.nInvestorsQuali = nInvestorsQuali;
    }

    public String getnBidderManagerCon() {
        return nBidderManagerCon;
    }

    public void setnBidderManagerCon(String nBidderManagerCon) {
        this.nBidderManagerCon = nBidderManagerCon;
    }

    public String getnApprovalMatter() {
        return nApprovalMatter;
    }

    public void setnApprovalMatter(String nApprovalMatter) {
        this.nApprovalMatter = nApprovalMatter;
    }

    public String getnUrl() {
        return nUrl;
    }

    public void setnUrl(String nUrl) {
        this.nUrl = nUrl;
    }

    public String getnReceiveJointBidd() {
        return nReceiveJointBidd;
    }

    public void setnReceiveJointBidd(String nReceiveJointBidd) {
        this.nReceiveJointBidd = nReceiveJointBidd;
    }

    public String getnDeterminType() {
        return nDeterminType;
    }

    public void setnDeterminType(String nDeterminType) {
        this.nDeterminType = nDeterminType;
    }

    public String getnFunction() {
        return nFunction;
    }

    public void setnFunction(String nFunction) {
        this.nFunction = nFunction;
    }

    public List<String> getnAmounts() {
        return nAmounts;
    }

    public void setnAmounts(List<String> nAmounts) {
        this.nAmounts = nAmounts;
    }

    public List<String> getnPlandates() {
        return nPlandates;
    }

    public void setnPlandates(List<String> nPlandates) {
        this.nPlandates = nPlandates;
    }

    public List<String> getnPlanenddates() {
        return nPlanenddates;
    }

    public void setnPlanenddates(List<String> nPlanenddates) {
        this.nPlanenddates = nPlanenddates;
    }

    public List<String> getnCreateTimes() {
        return nCreateTimes;
    }

    public void setnCreateTimes(List<String> nCreateTimes) {
        this.nCreateTimes = nCreateTimes;
    }
}
