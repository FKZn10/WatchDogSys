package com.jpmsoft.common.model.response.Design;


import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignConstructionDtlDTO;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignProjectInfoModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 施工图设计
 */
public class DesignConstructionResponse extends ProjectCommonRes {

    private String dcId;
    private String pId;
    private String dcName;
    private BigDecimal dcBudget;
    private String dcSunshine;
    private String dcSpecialty;
    private String dcFps;
    private Integer dcGovernment;
    private Integer dcUserConfirm;
    private Date dcPlanStart;
    private Date dcPlanEnd;
    private String dcCreateUid;
    private String dcCreateUname;
    private Date dcCreateTime;
    private Integer dcState;
    private Date dcStateTime;
    private String dcCategory;
    private String dcContent;
    private Date dcStart;
    private Date dcEnd;
    private String conId;
    private String secId;
    private String dcDrawing;
    private String dcOther;
    private String dcDesignUnit;
    private String dcDesignUnitTel;
    private String dcDesignUnitName;
    //设计管理的项目信息
    private DesignProjectInfoModel designProjectInfoModel;


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


    private String secCurrentName;
    private String conName;
    private String conCode;
    private String conPartyBAgent;
    private String conPartyBName;
    private String secName;
    //单位简称
    private String conPartyBAbbName;
    private String conPartyBPm;
    private List<DesignConstructionDtlDTO> designConstructionDtls;

    /**
     * 是否下发
     */
    private String issued;

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public String getDcId() {
        return dcId;
    }

    public void setDcId(String dcId) {
        this.dcId = dcId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getDcName() {
        return dcName;
    }

    public void setDcName(String dcName) {
        this.dcName = dcName;
    }

    public BigDecimal getDcBudget() {
        return dcBudget;
    }

    public void setDcBudget(BigDecimal dcBudget) {
        this.dcBudget = dcBudget;
    }

    public String getDcSunshine() {
        return dcSunshine;
    }

    public void setDcSunshine(String dcSunshine) {
        this.dcSunshine = dcSunshine;
    }

    public String getDcSpecialty() {
        return dcSpecialty;
    }

    public void setDcSpecialty(String dcSpecialty) {
        this.dcSpecialty = dcSpecialty;
    }

    public String getDcFps() {
        return dcFps;
    }

    public void setDcFps(String dcFps) {
        this.dcFps = dcFps;
    }

    public Integer getDcGovernment() {
        return dcGovernment;
    }

    public void setDcGovernment(Integer dcGovernment) {
        this.dcGovernment = dcGovernment;
    }

    public Integer getDcUserConfirm() {
        return dcUserConfirm;
    }

    public void setDcUserConfirm(Integer dcUserConfirm) {
        this.dcUserConfirm = dcUserConfirm;
    }

    public Date getDcPlanStart() {
        return dcPlanStart;
    }

    public void setDcPlanStart(Date dcPlanStart) {
        this.dcPlanStart = dcPlanStart;
    }

    public Date getDcPlanEnd() {
        return dcPlanEnd;
    }

    public void setDcPlanEnd(Date dcPlanEnd) {
        this.dcPlanEnd = dcPlanEnd;
    }

    public String getDcCreateUid() {
        return dcCreateUid;
    }

    public void setDcCreateUid(String dcCreateUid) {
        this.dcCreateUid = dcCreateUid;
    }

    public String getDcCreateUname() {
        return dcCreateUname;
    }

    public void setDcCreateUname(String dcCreateUname) {
        this.dcCreateUname = dcCreateUname;
    }

    public Date getDcCreateTime() {
        return dcCreateTime;
    }

    public void setDcCreateTime(Date dcCreateTime) {
        this.dcCreateTime = dcCreateTime;
    }

    public Integer getDcState() {
        return dcState;
    }

    public void setDcState(Integer dcState) {
        this.dcState = dcState;
    }

    public Date getDcStateTime() {
        return dcStateTime;
    }

    public void setDcStateTime(Date dcStateTime) {
        this.dcStateTime = dcStateTime;
    }

    public String getDcCategory() {
        return dcCategory;
    }

    public void setDcCategory(String dcCategory) {
        this.dcCategory = dcCategory;
    }

    public String getDcContent() {
        return dcContent;
    }

    public void setDcContent(String dcContent) {
        this.dcContent = dcContent;
    }

    public Date getDcStart() {
        return dcStart;
    }

    public void setDcStart(Date dcStart) {
        this.dcStart = dcStart;
    }

    public Date getDcEnd() {
        return dcEnd;
    }

    public void setDcEnd(Date dcEnd) {
        this.dcEnd = dcEnd;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    @Override
    public String getSecId() {
        return secId;
    }

    @Override
    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getDcDrawing() {
        return dcDrawing;
    }

    public void setDcDrawing(String dcDrawing) {
        this.dcDrawing = dcDrawing;
    }

    public String getDcOther() {
        return dcOther;
    }

    public void setDcOther(String dcOther) {
        this.dcOther = dcOther;
    }

    public String getDcDesignUnit() {
        return dcDesignUnit;
    }

    public void setDcDesignUnit(String dcDesignUnit) {
        this.dcDesignUnit = dcDesignUnit;
    }

    public String getDcDesignUnitTel() {
        return dcDesignUnitTel;
    }

    public void setDcDesignUnitTel(String dcDesignUnitTel) {
        this.dcDesignUnitTel = dcDesignUnitTel;
    }

    public String getDcDesignUnitName() {
        return dcDesignUnitName;
    }

    public void setDcDesignUnitName(String dcDesignUnitName) {
        this.dcDesignUnitName = dcDesignUnitName;
    }

    public String getSecCurrentName() {
        return secCurrentName;
    }

    public void setSecCurrentName(String secCurrentName) {
        this.secCurrentName = secCurrentName;
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

    public List<DesignConstructionDtlDTO> getDesignConstructionDtls() {
        return designConstructionDtls;
    }

    public void setDesignConstructionDtls(List<DesignConstructionDtlDTO> designConstructionDtls) {
        this.designConstructionDtls = designConstructionDtls;
    }

    public DesignProjectInfoModel getDesignProjectInfoModel() {
        return designProjectInfoModel;
    }

    public void setDesignProjectInfoModel(DesignProjectInfoModel designProjectInfoModel) {
        this.designProjectInfoModel = designProjectInfoModel;
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
}
