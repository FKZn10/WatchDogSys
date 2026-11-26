package com.jpmsoft.common.model.response.Contract;


import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同
 */
public class ConContractList {

    //合同ID
    private String conId;
    //合同名称
    private String conName;
    //合同文本（word版）
    private String conWord;
    private String secName;
    //合同编号
    private String conCode;
    //合同类型编码
    private String conType;
    //【进度款】应用方式
    private String conProgressWay;
    private String conPartyAName;
    private String conPartyAOtherName;
    //监理机构
    private String conSupervisionOrg;
    //总监理工程师
    private String conGeneralEngineer;
    //乙方经办人姓名
    private String conPartyBAgent;
    //乙方经办人电话
    private String conPartyBAgentTel;
    //乙方项目经理/总监
    private String conPartyBPm;
    //乙方项目经理身份证号
    private String conPartyBPmIdcard;
    //项目经理建造师执业印章号
    private String conPartyBPmConsYzh;
    //项目经理建造师注册号
    private String conPartyBPmConsNu;
    //乙方技术负责人
    private String conPartyBCto;
    //乙方安全负责人
    private String conPartyBSm;
    //合同币种编码
    private String conCurrency;
    //合同状态编码
    private String conState;
    //合同执行情况
    private String conExecution;
    //是否需要预付款保函
    private String conApg;
    //合同种类
    private String conCategory;
    //创建人姓名
    private String conCreateUserName;
    //计划开工日期
    private Date conPlanStartDate;
    //计划竣工日期
    private Date conPlanEndDate;
    //合同签订日期
    private Date conDate;
    //正式开工日期
    private Date conStartDate;
    //创建时间
    private Date conCreateTime;
    //审批时间
    private Date conApprovalTime;
    //总工期（日历天）
    private Integer conTotalDuration;
    //暂定合同总价（含税）【P】
    private BigDecimal conMoneyTotal;
    //暂列金额
    private BigDecimal conMoneyTentative;
    //预付款保函金额
    private BigDecimal conApgMoney;
    private String conPartyBName;
    private String conProjectName;
    private Integer  conYear;
    private String conPayClause;
    private Integer  conIsHistory;
    //审批状态
    private Integer conApprovalState;
    //【技术负责人】姓名
    private String conTdName;
    //【技术负责人】社保号
    private String conTdNum;
    //【技术负责人】手机号
    private String conTdMobileNumber;
    //是否多方主体合同
    private String conMultiSubject;
    //【补充合同】是否补充合同
    private String conSup;
    //招标方式
    private String conBiddingType;


    //预留金
    private BigDecimal conReserveMoney;

    /**
     *云上建工审批状态： 0-被驳回； 1-审批中； 3-审批通过
     */
    private String thirdStatus;

    /**
     * 云上建工审批意见
     */
    private String thirdApprovalComment;

    /**
     *
     */
    private String thirdRequestId;

    /**
     * 云上建工用户id
     */
    private String thirdUserId;

    /**
     * 内容摘要
     */
    private String conContentSummary;

    /**
     * 【进度款】支付额【P*Q】
     */
    private BigDecimal conProgressMoney;

    public BigDecimal getConProgressMoney() {
        return conProgressMoney;
    }

    public void setConProgressMoney(BigDecimal conProgressMoney) {
        this.conProgressMoney = conProgressMoney;
    }

    public String getThirdStatus() {
        return thirdStatus;
    }

    public void setThirdStatus(String thirdStatus) {
        this.thirdStatus = thirdStatus;
    }

    public String getThirdApprovalComment() {
        return thirdApprovalComment;
    }

    public void setThirdApprovalComment(String thirdApprovalComment) {
        this.thirdApprovalComment = thirdApprovalComment;
    }

    public String getThirdRequestId() {
        return thirdRequestId;
    }

    public void setThirdRequestId(String thirdRequestId) {
        this.thirdRequestId = thirdRequestId;
    }

    public String getThirdUserId() {
        return thirdUserId;
    }

    public void setThirdUserId(String thirdUserId) {
        this.thirdUserId = thirdUserId;
    }

    public String getConContentSummary() {
        return conContentSummary;
    }

    public void setConContentSummary(String conContentSummary) {
        this.conContentSummary = conContentSummary;
    }

    public String getConApprovalMatters() {
        return conApprovalMatters;
    }

    public void setConApprovalMatters(String conApprovalMatters) {
        this.conApprovalMatters = conApprovalMatters;
    }

    /**
     * 呈批事项
     */
    private String conApprovalMatters;

    public BigDecimal getConReserveMoney() {
        return conReserveMoney;
    }

    public void setConReserveMoney(BigDecimal conId) {
        this.conReserveMoney = conReserveMoney;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConWord() {
        return conWord;
    }

    public void setConWord(String conWord) {
        this.conWord = conWord;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getConType() {
        return conType;
    }

    public void setConType(String conType) {
        this.conType = conType;
    }

    public String getConProgressWay() {
        return conProgressWay;
    }

    public void setConProgressWay(String conProgressWay) {
        this.conProgressWay = conProgressWay;
    }

    public String getConPartyAName() {
        return conPartyAName;
    }

    public void setConPartyAName(String conPartyAName) {
        this.conPartyAName = conPartyAName;
    }

    public String getConPartyAOtherName() {
        return conPartyAOtherName;
    }

    public void setConPartyAOtherName(String conPartyAOtherName) {
        this.conPartyAOtherName = conPartyAOtherName;
    }

    public String getConSupervisionOrg() {
        return conSupervisionOrg;
    }

    public void setConSupervisionOrg(String conSupervisionOrg) {
        this.conSupervisionOrg = conSupervisionOrg;
    }

    public String getConGeneralEngineer() {
        return conGeneralEngineer;
    }

    public void setConGeneralEngineer(String conGeneralEngineer) {
        this.conGeneralEngineer = conGeneralEngineer;
    }

    public String getConPartyBAgent() {
        return conPartyBAgent;
    }

    public void setConPartyBAgent(String conPartyBAgent) {
        this.conPartyBAgent = conPartyBAgent;
    }

    public String getConPartyBAgentTel() {
        return conPartyBAgentTel;
    }

    public void setConPartyBAgentTel(String conPartyBAgentTel) {
        this.conPartyBAgentTel = conPartyBAgentTel;
    }

    public String getConPartyBPm() {
        return conPartyBPm;
    }

    public void setConPartyBPm(String conPartyBPm) {
        this.conPartyBPm = conPartyBPm;
    }

    public String getConPartyBPmIdcard() {
        return conPartyBPmIdcard;
    }

    public void setConPartyBPmIdcard(String conPartyBPmIdcard) {
        this.conPartyBPmIdcard = conPartyBPmIdcard;
    }

    public String getConPartyBPmConsYzh() {
        return conPartyBPmConsYzh;
    }

    public void setConPartyBPmConsYzh(String conPartyBPmConsYzh) {
        this.conPartyBPmConsYzh = conPartyBPmConsYzh;
    }

    public String getConPartyBPmConsNu() {
        return conPartyBPmConsNu;
    }

    public void setConPartyBPmConsNu(String conPartyBPmConsNu) {
        this.conPartyBPmConsNu = conPartyBPmConsNu;
    }

    public String getConPartyBCto() {
        return conPartyBCto;
    }

    public void setConPartyBCto(String conPartyBCto) {
        this.conPartyBCto = conPartyBCto;
    }

    public String getConPartyBSm() {
        return conPartyBSm;
    }

    public void setConPartyBSm(String conPartyBSm) {
        this.conPartyBSm = conPartyBSm;
    }

    public String getConCurrency() {
        return conCurrency;
    }

    public void setConCurrency(String conCurrency) {
        this.conCurrency = conCurrency;
    }

    public String getConState() {
        return conState;
    }

    public void setConState(String conState) {
        this.conState = conState;
    }

    public String getConExecution() {
        return conExecution;
    }

    public void setConExecution(String conExecution) {
        this.conExecution = conExecution;
    }

    public String getConApg() {
        return conApg;
    }

    public void setConApg(String conApg) {
        this.conApg = conApg;
    }

    public String getConCategory() {
        return conCategory;
    }

    public void setConCategory(String conCategory) {
        this.conCategory = conCategory;
    }

    public String getConCreateUserName() {
        return conCreateUserName;
    }

    public void setConCreateUserName(String conCreateUserName) {
        this.conCreateUserName = conCreateUserName;
    }

    public Date getConPlanStartDate() {
        return conPlanStartDate;
    }

    public void setConPlanStartDate(Date conPlanStartDate) {
        this.conPlanStartDate = conPlanStartDate;
    }

    public Date getConPlanEndDate() {
        return conPlanEndDate;
    }

    public void setConPlanEndDate(Date conPlanEndDate) {
        this.conPlanEndDate = conPlanEndDate;
    }

    public Date getConDate() {
        return conDate;
    }

    public void setConDate(Date conDate) {
        this.conDate = conDate;
    }

    public Date getConStartDate() {
        return conStartDate;
    }

    public void setConStartDate(Date conStartDate) {
        this.conStartDate = conStartDate;
    }

    public Date getConCreateTime() {
        return conCreateTime;
    }

    public void setConCreateTime(Date conCreateTime) {
        this.conCreateTime = conCreateTime;
    }

    public Date getConApprovalTime() {
        return conApprovalTime;
    }

    public void setConApprovalTime(Date conApprovalTime) {
        this.conApprovalTime = conApprovalTime;
    }

    public Integer getConTotalDuration() {
        return conTotalDuration;
    }

    public void setConTotalDuration(Integer conTotalDuration) {
        this.conTotalDuration = conTotalDuration;
    }

    public BigDecimal getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(BigDecimal conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public BigDecimal getConMoneyTentative() {
        return conMoneyTentative;
    }

    public void setConMoneyTentative(BigDecimal conMoneyTentative) {
        this.conMoneyTentative = conMoneyTentative;
    }

    public BigDecimal getConApgMoney() {
        return conApgMoney;
    }

    public void setConApgMoney(BigDecimal conApgMoney) {
        this.conApgMoney = conApgMoney;
    }

    public String getConPartyBName() {
        return conPartyBName;
    }

    public void setConPartyBName(String conPartyBName) {
        this.conPartyBName = conPartyBName;
    }

    public String getConProjectName() {
        return conProjectName;
    }

    public void setConProjectName(String conProjectName) {
        this.conProjectName = conProjectName;
    }

    public Integer getConYear() {
        return conYear;
    }

    public void setConYear(Integer conYear) {
        this.conYear = conYear;
    }

    public String getConPayClause() {
        return conPayClause;
    }

    public void setConPayClause(String conPayClause) {
        this.conPayClause = conPayClause;
    }

    public Integer getConIsHistory() {
        return conIsHistory;
    }

    public void setConIsHistory(Integer conIsHistory) {
        this.conIsHistory = conIsHistory;
    }

    public Integer getConApprovalState() {
        return conApprovalState;
    }

    public void setConApprovalState(Integer conApprovalState) {
        this.conApprovalState = conApprovalState;
    }

    public String getConTdName() {
        return conTdName;
    }

    public void setConTdName(String conTdName) {
        this.conTdName = conTdName;
    }

    public String getConTdNum() {
        return conTdNum;
    }

    public void setConTdNum(String conTdNum) {
        this.conTdNum = conTdNum;
    }

    public String getConTdMobileNumber() {
        return conTdMobileNumber;
    }

    public void setConTdMobileNumber(String conTdMobileNumber) {
        this.conTdMobileNumber = conTdMobileNumber;
    }

    public String getConMultiSubject() {
        return conMultiSubject;
    }

    public void setConMultiSubject(String conMultiSubject) {
        this.conMultiSubject = conMultiSubject;
    }

    public String getConSup() {
        return conSup;
    }

    public void setConSup(String conSup) {
        this.conSup = conSup;
    }

    public String getConBiddingType() {
        return conBiddingType;
    }

    public void setConBiddingType(String conBiddingType) {
        this.conBiddingType = conBiddingType;
    }
}
