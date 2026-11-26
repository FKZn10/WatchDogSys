package com.jpmsoft.common.model.requestAndResponse.Design;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 方案设计报审
 */
public class DesignConceptModel extends ProjectCommonRes {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String dcId;
    private String pId;
    private String dcName;
    private Boolean dcFsr;
    private Boolean dcAssignment;
    private Boolean dcGovernment;
    private Boolean dcUser;
    private Boolean dcExamine;
    private String dcUnitId;
    private String dcDrawingVersion;
    private String dcDrawingSpecialty;
    private Date dcPlanStart;
    private Date dcPlanEnd;
    private Boolean dcSunlightAnalysis;


    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String dcCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String dcCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date dcCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer dcState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date dcStateTime;
    private String conId;
    private String dcUnitTel;
    private BigDecimal dcFloorArea;
    private BigDecimal dcAmount;
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

    private String conName;
    private String conCode;
    private String dcUnitNameHistory;
    private String dcUnitName;
    //单位简称
    private String dcUnitAbbName;

    public String getDcId() {
        return dcId;
    }

    public void setDcId(String dcId) {
        this.dcId = dcId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getDcName() {
        return dcName;
    }

    public void setDcName(String dcName) {
        this.dcName = dcName;
    }

    public Boolean getDcFsr() {
        return dcFsr;
    }

    public void setDcFsr(Boolean dcFsr) {
        this.dcFsr = dcFsr;
    }

    public Boolean getDcAssignment() {
        return dcAssignment;
    }

    public void setDcAssignment(Boolean dcAssignment) {
        this.dcAssignment = dcAssignment;
    }

    public Boolean getDcGovernment() {
        return dcGovernment;
    }

    public void setDcGovernment(Boolean dcGovernment) {
        this.dcGovernment = dcGovernment;
    }

    public Boolean getDcUser() {
        return dcUser;
    }

    public void setDcUser(Boolean dcUser) {
        this.dcUser = dcUser;
    }

    public Boolean getDcExamine() {
        return dcExamine;
    }

    public void setDcExamine(Boolean dcExamine) {
        this.dcExamine = dcExamine;
    }

    public String getDcUnitId() {
        return dcUnitId;
    }

    public void setDcUnitId(String dcUnitId) {
        this.dcUnitId = dcUnitId;
    }

    public String getDcDrawingVersion() {
        return dcDrawingVersion;
    }

    public void setDcDrawingVersion(String dcDrawingVersion) {
        this.dcDrawingVersion = dcDrawingVersion;
    }

    public String getDcDrawingSpecialty() {
        return dcDrawingSpecialty;
    }

    public void setDcDrawingSpecialty(String dcDrawingSpecialty) {
        this.dcDrawingSpecialty = dcDrawingSpecialty;
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

    @Override
    public String getConId() {
        return conId;
    }

    @Override
    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getDcUnitTel() {
        return dcUnitTel;
    }

    public void setDcUnitTel(String dcUnitTel) {
        this.dcUnitTel = dcUnitTel;
    }

    public BigDecimal getDcFloorArea() {
        return dcFloorArea;
    }

    public void setDcFloorArea(BigDecimal dcFloorArea) {
        this.dcFloorArea = dcFloorArea;
    }

    public BigDecimal getDcAmount() {
        return dcAmount;
    }

    public void setDcAmount(BigDecimal dcAmount) {
        this.dcAmount = dcAmount;
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

    public String getDcUnitNameHistory() {
        return dcUnitNameHistory;
    }

    public void setDcUnitNameHistory(String dcUnitNameHistory) {
        this.dcUnitNameHistory = dcUnitNameHistory;
    }

    public String getDcUnitName() {
        return dcUnitName;
    }

    public void setDcUnitName(String dcUnitName) {
        this.dcUnitName = dcUnitName;
    }

    public String getDcUnitAbbName() {
        return dcUnitAbbName;
    }

    public void setDcUnitAbbName(String dcUnitAbbName) {
        this.dcUnitAbbName = dcUnitAbbName;
    }

    public DesignProjectInfoModel getDesignProjectInfoModel() {
        return designProjectInfoModel;
    }

    public void setDesignProjectInfoModel(DesignProjectInfoModel designProjectInfoModel) {
        this.designProjectInfoModel = designProjectInfoModel;
    }

    public Boolean getDcSunlightAnalysis() {
        return dcSunlightAnalysis;
    }

    public void setDcSunlightAnalysis(Boolean dcSunlightAnalysis) {
        this.dcSunlightAnalysis = dcSunlightAnalysis;
    }
}
