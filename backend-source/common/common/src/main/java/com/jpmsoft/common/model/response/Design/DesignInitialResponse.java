package com.jpmsoft.common.model.response.Design;

import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProjectInfoModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 初步设计
 */
public class DesignInitialResponse extends ProjectCommonRes {

    private String diId;
    //项目ID
    private String pId;
    //初步设计标题
    private String diTitle;
    //估算金额
    private BigDecimal diEstimate;
    //初步设计概算金额
    private BigDecimal diBudget;
    //是否超可研批复标准
    private Integer diEfs;
    //是否符合设计任务书
    private Integer diDesignBrief;
    //设计单位ID
    private String diDesignUnit;
    //是否符合政府指标
    private Integer diGovernment;
    //使用单位确认
    private Integer diUserConfirm;
    private String diCreateUid;
    private String diCreateUname;
    private Date diCreateTime;
    private Integer diState;
    private Date diStateTime;
    //建安控制价
    private BigDecimal diControlPrice;
    //建安概算价
    private BigDecimal diEstimatePrice;
    //设计内容
    private String diContent;
    //开始时间
    private Date diStart;
    //结束时间
    private Date diEnd;
    //合同
    private String conId;
    //设计单位联系方式
    private String diDesignUnitTel;
    //专业审查会议
    private String diReviewMeetings;

    //设计管理的项目信息
    private DesignProjectInfoModel designProjectInfoModel;

    //概算
    List<DesignInitialBudgetDtlResponse> diBudgetDtls;

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
     * 是否盖公司章
     */
    private Boolean companySealFlag;

    /**
     * 印章类别  0-公章，1-技术专用章
     *
     */
    private String sealCategory;

    /**
     * 专业审核 ： 0、道路 1、电气 2、房建 3、给排水 4、工程造价 5、公交规划 6、轨道交通 7、景观绿化 8、交通工程 9、交通规划
     * 10、交通综治 11、结构 12、路基路面 13、路线交叉 14、桥梁涵洞 15、燃气 16、水土保持 17、隧道 18、岩土 19、智能交通
     */
    private String professionalReview;

    /**
     * 项目类别：0、A类，1、B类，3、C类
     */
    private String projectCategory;

    /**
     * 事由
     */
    private String reason;

    /**
     * 云上建工用户id
     */
    private String thirdUserId;


    /**************************/

    private String diDesignUnitName;
    private String conName;
    private String conCode;
    private String conPartyBAgent;
    private String conPartyBName;
    private String secName;
    //单位简称
    private String diDesignUnitAbbName;
    private String conPartyBAbbName;
    private String conPartyBPm;

    public String getDiId() {
        return diId;
    }

    public void setDiId(String diId) {
        this.diId = diId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getDiTitle() {
        return diTitle;
    }

    public void setDiTitle(String diTitle) {
        this.diTitle = diTitle;
    }

    public BigDecimal getDiEstimate() {
        return diEstimate;
    }

    public void setDiEstimate(BigDecimal diEstimate) {
        this.diEstimate = diEstimate;
    }

    public BigDecimal getDiBudget() {
        return diBudget;
    }

    public void setDiBudget(BigDecimal diBudget) {
        this.diBudget = diBudget;
    }

    public Integer getDiEfs() {
        return diEfs;
    }

    public void setDiEfs(Integer diEfs) {
        this.diEfs = diEfs;
    }

    public Integer getDiDesignBrief() {
        return diDesignBrief;
    }

    public void setDiDesignBrief(Integer diDesignBrief) {
        this.diDesignBrief = diDesignBrief;
    }

    public String getDiDesignUnit() {
        return diDesignUnit;
    }

    public void setDiDesignUnit(String diDesignUnit) {
        this.diDesignUnit = diDesignUnit;
    }

    public Integer getDiGovernment() {
        return diGovernment;
    }

    public void setDiGovernment(Integer diGovernment) {
        this.diGovernment = diGovernment;
    }

    public Integer getDiUserConfirm() {
        return diUserConfirm;
    }

    public void setDiUserConfirm(Integer diUserConfirm) {
        this.diUserConfirm = diUserConfirm;
    }

    public String getDiCreateUid() {
        return diCreateUid;
    }

    public void setDiCreateUid(String diCreateUid) {
        this.diCreateUid = diCreateUid;
    }

    public String getDiCreateUname() {
        return diCreateUname;
    }

    public void setDiCreateUname(String diCreateUname) {
        this.diCreateUname = diCreateUname;
    }

    public Date getDiCreateTime() {
        return diCreateTime;
    }

    public void setDiCreateTime(Date diCreateTime) {
        this.diCreateTime = diCreateTime;
    }

    public Integer getDiState() {
        return diState;
    }

    public void setDiState(Integer diState) {
        this.diState = diState;
    }

    public Date getDiStateTime() {
        return diStateTime;
    }

    public void setDiStateTime(Date diStateTime) {
        this.diStateTime = diStateTime;
    }

    public BigDecimal getDiControlPrice() {
        return diControlPrice;
    }

    public void setDiControlPrice(BigDecimal diControlPrice) {
        this.diControlPrice = diControlPrice;
    }

    public BigDecimal getDiEstimatePrice() {
        return diEstimatePrice;
    }

    public void setDiEstimatePrice(BigDecimal diEstimatePrice) {
        this.diEstimatePrice = diEstimatePrice;
    }


    public String getDiContent() {
        return diContent;
    }

    public void setDiContent(String diContent) {
        this.diContent = diContent;
    }

    public Date getDiStart() {
        return diStart;
    }

    public void setDiStart(Date diStart) {
        this.diStart = diStart;
    }

    public Date getDiEnd() {
        return diEnd;
    }

    public void setDiEnd(Date diEnd) {
        this.diEnd = diEnd;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getDiDesignUnitTel() {
        return diDesignUnitTel;
    }

    public void setDiDesignUnitTel(String diDesignUnitTel) {
        this.diDesignUnitTel = diDesignUnitTel;
    }

    public String getDiReviewMeetings() {
        return diReviewMeetings;
    }

    public void setDiReviewMeetings(String diReviewMeetings) {
        this.diReviewMeetings = diReviewMeetings;
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

    public Boolean getCompanySealFlag() {
        return companySealFlag;
    }

    public void setCompanySealFlag(Boolean companySealFlag) {
        this.companySealFlag = companySealFlag;
    }

    public String getSealCategory() {
        return sealCategory;
    }

    public void setSealCategory(String sealCategory) {
        this.sealCategory = sealCategory;
    }

    public String getProfessionalReview() {
        return professionalReview;
    }

    public void setProfessionalReview(String professionalReview) {
        this.professionalReview = professionalReview;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getThirdUserId() {
        return thirdUserId;
    }

    public void setThirdUserId(String thirdUserId) {
        this.thirdUserId = thirdUserId;
    }

    public String getDiDesignUnitName() {
        return diDesignUnitName;
    }

    public void setDiDesignUnitName(String diDesignUnitName) {
        this.diDesignUnitName = diDesignUnitName;
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

    public String getConPartyBAgent() {
        return conPartyBAgent;
    }

    public void setConPartyBAgent(String conPartyBAgent) {
        this.conPartyBAgent = conPartyBAgent;
    }

    public String getConPartyBName() {
        return conPartyBName;
    }

    public void setConPartyBName(String conPartyBName) {
        this.conPartyBName = conPartyBName;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getDiDesignUnitAbbName() {
        return diDesignUnitAbbName;
    }

    public void setDiDesignUnitAbbName(String diDesignUnitAbbName) {
        this.diDesignUnitAbbName = diDesignUnitAbbName;
    }

    public String getConPartyBAbbName() {
        return conPartyBAbbName;
    }

    public void setConPartyBAbbName(String conPartyBAbbName) {
        this.conPartyBAbbName = conPartyBAbbName;
    }

    public String getConPartyBPm() {
        return conPartyBPm;
    }

    public void setConPartyBPm(String conPartyBPm) {
        this.conPartyBPm = conPartyBPm;
    }

    public DesignProjectInfoModel getDesignProjectInfoModel() {
        return designProjectInfoModel;
    }

    public void setDesignProjectInfoModel(DesignProjectInfoModel designProjectInfoModel) {
        this.designProjectInfoModel = designProjectInfoModel;
    }

    public List<DesignInitialBudgetDtlResponse> getDiBudgetDtls() {
        return diBudgetDtls;
    }

    public void setDiBudgetDtls(List<DesignInitialBudgetDtlResponse> diBudgetDtls) {
        this.diBudgetDtls = diBudgetDtls;
    }
}
