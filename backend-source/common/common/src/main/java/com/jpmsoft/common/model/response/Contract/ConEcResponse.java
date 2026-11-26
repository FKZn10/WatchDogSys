package com.jpmsoft.common.model.response.Contract;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 变更事项申请
 */

public class ConEcResponse extends ProjectCommonRes {
    private static final long serialVersionUID = 6549380487432651389L;
    private String ecId;
    private String conId;
    private String preEcId;
    private String secId;
    private String ecSupervisorId;
    private String ecConsultingUnitId;
    private String ecCode;
    private String clId;
    private String ecResponsibleParty;
    private String ecMainCause;
    private BigDecimal ecApplyMoney;
    private Integer ecApplyMoneyType;
    private Integer ecDuration;
    private Integer ecDurationType;
    private String ecGrade;
    private String ecRemark;
    private String ecHbFileYj;
    private String ecHbFileYsd;
    private String ecHbFileSdd;
    private String ecHbFileBgd;
    private String ecHbFileHyjy;
    private String ecHbFileBsys;
    private String ecHbFileTz;
    private BigDecimal ecTotalMoney;

    private BigDecimal ecTotalMoneySum;

    private BigDecimal ecTotalMoneyPlus;
    private BigDecimal ecTotalMoneyMinus;
    private BigDecimal ecFinalMoney;
    private BigDecimal ecTotalMoneyC;
    private Float ecTotalMoneyCRate;
    private String ecBgzlId;
    private String ecWarn;
    private String ecContent;
    private String ecReason;
    private Integer ecDesignOrder;
    private String ecDesignOrderCode;
    private String ecDesignOrderDoc;
    private String ecDesignDrawing;
    private String ecCunitCostDoc;
    private String ecNegotiationDoc;
    private String ecCreateUid;
    private String ecCreateUname;
    private Date ecCreateTime;
    private String ecCreateCId;
    private Integer ecState;
    private Date ecStateTime;
    private Integer ecDrawingLibrary;
    private String ecDesignId;
    private String ecPartName;
    private String ecType;
    private String ecChangeGist;
    private String ecGistOther;
    private String dcoId;
    private String ecDoc;
    private String ecDrawing;
    private String ecIsBgzl;
    private String ecCostBook;
    private String ecDep;
    private String ecReqFile;
    private String ecApprovalFile;
    private String ecRelevantFile;
    private String ecApplyFile;
    private String ecMaterialFile;
    private String ecOpinionFile;
    private String ecFilingsFile;
    private String ecEnisFile;
    private String ecPutForward;
    private String ecPart;
    private String ecName;
    private String ecApprovalMatter;
    private String enisId;
    private String ecChangeType;
    private String ecCnature;
    private String projectId;
    private String ecSjFile;
    private String ecJlFile;
    private Boolean ecNewEc;
    private String ecConstructionName;
    /*******************/
    private String projectName;
    private String projectCode;
    private String enisName;
    private String enisCodeSelf;
    private String ecPutForwardName;
    private String ecDepName;
    private String ecDesignName;
    private String ecDesignAbbName;
    private String secName;
    private String ecSupervisorName;
    private String ecConsultingUnitName;
    private String clTitle;
    private String conName;
    private String ecCreateCName;
    private String pId;
    private String conCode;
    private String conMoneyTotal;
    private String conPartyBId;
    private String conPartyBName;
    private String ecSupervisorNameHistory;
    private String ecConsultingUnitNameHistory;
    private String ecSupervisorAbbName;
    private String ecConsultingUnitAbbName;
    private String ecCreateCAbbName;
    private String conPartyBAbbName;
    private String dcoCode;
    private String dcoProjectName;
    private String dcoMajor;
    private String enisCode;
    private String ecConstruction;
    private String ecCostConsulting;
    private String ecAuditType;
    private BigDecimal ecFinalMoneyAfter;
    private String ecConstName;
    private String ecCostConsulName;

    private String preEcName;
    private String preEcCode;

    /**
     *云上建工审批状态： 0-被驳回； 1-审批中； 3-审批通过
     */
    private String thirdStatus;

    /**
     * 云上建工审批意见
     */
    private String thirdApprovalComment;

    /**
     * 第三方请求id
     */
    private String thirdRequestId;

    /**
     * 云上建工用户id
     */
    private String thirdUserId;

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
     * 项目类别（0-A类，1-B类，3-C类）
     */
    private String projectCategory;

    /**
     * 事由
     */
    private String reason;

    // 多合同变更签证id
    private String multiContractConEcId;

    // 合同可用金额
    private BigDecimal contractAvailableMoney;

    // 概算可用金额
    private BigDecimal estimateAvailableMoney;

    // 其他附件关联id
    private String otherFileRelationId;

    // 合同概算拆分金额
    private BigDecimal conDeclareMoney;

    private BigDecimal ecTotalMoneyMinusC;

    private BigDecimal ecTotalMoneyPlusC;

    // 合同累计变更工期（包含本次）
    private BigDecimal ecTotalDayC;

    // 累计正变更工期
    private BigDecimal ecTotalDayPlusC;
    // 累计负变更工期
    private BigDecimal ecTotalDayMinusC;
    private String cmnId;



    //Change_Meeting_Notification表
    private String cmnName;
    private String cmnType;
    private String cmnIsRoom;
    private Date cmnStartTime;
    private Date cmnEndTime;
    private String cmnLocations;
    private String cmnRoom;
    private String cmnSigner;
    private String cmnIssuingTechnology;
    private String cmnPerson;
    private String cmnPhone;
    private String cmnInternalParticipants;
    private String cmnExternalParticipants;
    private String cmnMainContent;
    private String cmnRemark;
    private String cmnBriefingContent;
    private String cmnBriefingAnnex;
    /**
     * 会议锁定状态
     * 1 未锁定 2 锁定  3 审核解锁
     */
    private Integer cmnCsState;
    //是否历史数据 0是非历史数据 1是历史数据
    private String isHistory;

    private BigDecimal conMoneyTentative;

    private BigDecimal conProvisionalPe;

    private String ecDataSeparateType;

    private String itemCategory;

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getEcDataSeparateType() {
        return ecDataSeparateType;
    }

    public void setEcDataSeparateType(String ecDataSeparateType) {
        this.ecDataSeparateType = ecDataSeparateType;
    }

    public BigDecimal getConMoneyTentative() {
        return conMoneyTentative;
    }

    public void setConMoneyTentative(BigDecimal conMoneyTentative) {
        this.conMoneyTentative = conMoneyTentative;
    }

    public BigDecimal getConProvisionalPe() {
        return conProvisionalPe;
    }

    public void setConProvisionalPe(BigDecimal conProvisionalPe) {
        this.conProvisionalPe = conProvisionalPe;
    }

    public BigDecimal getEcTotalMoneySum() {
        return ecTotalMoneySum;
    }

    public void setEcTotalMoneySum(BigDecimal ecTotalMoneySum) {
        this.ecTotalMoneySum = ecTotalMoneySum;
    }

    public String getCmnName() {
        return cmnName;
    }

    public void setCmnName(String cmnName) {
        this.cmnName = cmnName;
    }

    public String getCmnType() {
        return cmnType;
    }

    public void setCmnType(String cmnType) {
        this.cmnType = cmnType;
    }

    public String getCmnIsRoom() {
        return cmnIsRoom;
    }

    public void setCmnIsRoom(String cmnIsRoom) {
        this.cmnIsRoom = cmnIsRoom;
    }

    public Date getCmnStartTime() {
        return cmnStartTime;
    }

    public void setCmnStartTime(Date cmnStartTime) {
        this.cmnStartTime = cmnStartTime;
    }

    public Date getCmnEndTime() {
        return cmnEndTime;
    }

    public void setCmnEndTime(Date cmnEndTime) {
        this.cmnEndTime = cmnEndTime;
    }

    public String getCmnLocations() {
        return cmnLocations;
    }

    public void setCmnLocations(String cmnLocations) {
        this.cmnLocations = cmnLocations;
    }

    public String getCmnRoom() {
        return cmnRoom;
    }

    public void setCmnRoom(String cmnRoom) {
        this.cmnRoom = cmnRoom;
    }

    public String getCmnSigner() {
        return cmnSigner;
    }

    public void setCmnSigner(String cmnSigner) {
        this.cmnSigner = cmnSigner;
    }

    public String getCmnIssuingTechnology() {
        return cmnIssuingTechnology;
    }

    public void setCmnIssuingTechnology(String cmnIssuingTechnology) {
        this.cmnIssuingTechnology = cmnIssuingTechnology;
    }

    public String getCmnPerson() {
        return cmnPerson;
    }

    public void setCmnPerson(String cmnPerson) {
        this.cmnPerson = cmnPerson;
    }

    public String getCmnPhone() {
        return cmnPhone;
    }

    public void setCmnPhone(String cmnPhone) {
        this.cmnPhone = cmnPhone;
    }

    public String getCmnInternalParticipants() {
        return cmnInternalParticipants;
    }

    public void setCmnInternalParticipants(String cmnInternalParticipants) {
        this.cmnInternalParticipants = cmnInternalParticipants;
    }

    public String getCmnExternalParticipants() {
        return cmnExternalParticipants;
    }

    public void setCmnExternalParticipants(String cmnExternalParticipants) {
        this.cmnExternalParticipants = cmnExternalParticipants;
    }

    public String getCmnMainContent() {
        return cmnMainContent;
    }

    public void setCmnMainContent(String cmnMainContent) {
        this.cmnMainContent = cmnMainContent;
    }

    public String getCmnRemark() {
        return cmnRemark;
    }

    public void setCmnRemark(String cmnRemark) {
        this.cmnRemark = cmnRemark;
    }

    public String getCmnBriefingContent() {
        return cmnBriefingContent;
    }

    public void setCmnBriefingContent(String cmnBriefingContent) {
        this.cmnBriefingContent = cmnBriefingContent;
    }

    public String getCmnBriefingAnnex() {
        return cmnBriefingAnnex;
    }

    public void setCmnBriefingAnnex(String cmnBriefingAnnex) {
        this.cmnBriefingAnnex = cmnBriefingAnnex;
    }

    public Integer getCmnCsState() {
        return cmnCsState;
    }

    public void setCmnCsState(Integer cmnCsState) {
        this.cmnCsState = cmnCsState;
    }

    public BigDecimal getEcTotalMoneyMinusC() {
        return ecTotalMoneyMinusC;
    }

    public void setEcTotalMoneyMinusC(BigDecimal ecTotalMoneyMinusC) {
        this.ecTotalMoneyMinusC = ecTotalMoneyMinusC;
    }

    public BigDecimal getEcTotalMoneyPlusC() {
        return ecTotalMoneyPlusC;
    }

    public void setEcTotalMoneyPlusC(BigDecimal ecTotalMoneyPlusC) {
        this.ecTotalMoneyPlusC = ecTotalMoneyPlusC;
    }

    public String getCmnId() {
        return cmnId;
    }

    public void setCmnId(String cmnId) {
        this.cmnId = cmnId;
    }

    public BigDecimal getConDeclareMoney() {
        return conDeclareMoney;
    }

    public void setConDeclareMoney(BigDecimal conDeclareMoney) {
        this.conDeclareMoney = conDeclareMoney;
    }

    public String getOtherFileRelationId() {
        return otherFileRelationId;
    }

    public void setOtherFileRelationId(String otherFileRelationId) {
        this.otherFileRelationId = otherFileRelationId;
    }

    public BigDecimal getContractAvailableMoney() {
        return contractAvailableMoney;
    }

    public void setContractAvailableMoney(BigDecimal contractAvailableMoney) {
        this.contractAvailableMoney = contractAvailableMoney;
    }

    public BigDecimal getEstimateAvailableMoney() {
        return estimateAvailableMoney;
    }

    public void setEstimateAvailableMoney(BigDecimal estimateAvailableMoney) {
        this.estimateAvailableMoney = estimateAvailableMoney;
    }

    public String getMultiContractConEcId() {
        return multiContractConEcId;
    }

    public void setMultiContractConEcId(String multiContractConEcId) {
        this.multiContractConEcId = multiContractConEcId;
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

    public String getEcId() {
        return ecId;
    }

    public void setEcId(String ecId) {
        this.ecId = ecId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getPreEcId() {
        return preEcId;
    }

    public void setPreEcId(String preEcId) {
        this.preEcId = preEcId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getEcSupervisorId() {
        return ecSupervisorId;
    }

    public void setEcSupervisorId(String ecSupervisorId) {
        this.ecSupervisorId = ecSupervisorId;
    }

    public String getEcConsultingUnitId() {
        return ecConsultingUnitId;
    }

    public void setEcConsultingUnitId(String ecConsultingUnitId) {
        this.ecConsultingUnitId = ecConsultingUnitId;
    }

    public String getEcCode() {
        return ecCode;
    }

    public void setEcCode(String ecCode) {
        this.ecCode = ecCode;
    }

    public String getClId() {
        return clId;
    }

    public void setClId(String clId) {
        this.clId = clId;
    }

    public String getEcResponsibleParty() {
        return ecResponsibleParty;
    }

    public void setEcResponsibleParty(String ecResponsibleParty) {
        this.ecResponsibleParty = ecResponsibleParty;
    }

    public String getEcMainCause() {
        return ecMainCause;
    }

    public void setEcMainCause(String ecMainCause) {
        this.ecMainCause = ecMainCause;
    }

    public BigDecimal getEcApplyMoney() {
        return ecApplyMoney;
    }

    public void setEcApplyMoney(BigDecimal ecApplyMoney) {
        this.ecApplyMoney = ecApplyMoney;
    }

    public Integer getEcApplyMoneyType() {
        return ecApplyMoneyType;
    }

    public void setEcApplyMoneyType(Integer ecApplyMoneyType) {
        this.ecApplyMoneyType = ecApplyMoneyType;
    }

    public Integer getEcDuration() {
        return ecDuration;
    }

    public void setEcDuration(Integer ecDuration) {
        this.ecDuration = ecDuration;
    }

    public Integer getEcDurationType() {
        return ecDurationType;
    }

    public void setEcDurationType(Integer ecDurationType) {
        this.ecDurationType = ecDurationType;
    }

    public String getEcGrade() {
        return ecGrade;
    }

    public void setEcGrade(String ecGrade) {
        this.ecGrade = ecGrade;
    }

    public BigDecimal getEcTotalMoney() {
        return ecTotalMoney;
    }

    public void setEcTotalMoney(BigDecimal ecTotalMoney) {
        this.ecTotalMoney = ecTotalMoney;
    }

    public BigDecimal getEcTotalMoneyPlus() {
        return ecTotalMoneyPlus;
    }

    public void setEcTotalMoneyPlus(BigDecimal ecTotalMoneyPlus) {
        this.ecTotalMoneyPlus = ecTotalMoneyPlus;
    }

    public BigDecimal getEcTotalMoneyMinus() {
        return ecTotalMoneyMinus;
    }

    public void setEcTotalMoneyMinus(BigDecimal ecTotalMoneyMinus) {
        this.ecTotalMoneyMinus = ecTotalMoneyMinus;
    }

    public BigDecimal getEcFinalMoney() {
        return ecFinalMoney;
    }

    public void setEcFinalMoney(BigDecimal ecFinalMoney) {
        this.ecFinalMoney = ecFinalMoney;
    }

    public BigDecimal getEcTotalMoneyC() {
        return ecTotalMoneyC;
    }

    public void setEcTotalMoneyC(BigDecimal ecTotalMoneyC) {
        this.ecTotalMoneyC = ecTotalMoneyC;
    }

    public Float getEcTotalMoneyCRate() {
        return ecTotalMoneyCRate;
    }

    public void setEcTotalMoneyCRate(Float ecTotalMoneyCRate) {
        this.ecTotalMoneyCRate = ecTotalMoneyCRate;
    }

    public String getEcWarn() {
        return ecWarn;
    }

    public void setEcWarn(String ecWarn) {
        this.ecWarn = ecWarn;
    }

    public String getEcContent() {
        return ecContent;
    }

    public void setEcContent(String ecContent) {
        this.ecContent = ecContent;
    }

    public String getEcReason() {
        return ecReason;
    }

    public void setEcReason(String ecReason) {
        this.ecReason = ecReason;
    }

    public Integer getEcDesignOrder() {
        return ecDesignOrder;
    }

    public void setEcDesignOrder(Integer ecDesignOrder) {
        this.ecDesignOrder = ecDesignOrder;
    }

    public String getEcDesignOrderCode() {
        return ecDesignOrderCode;
    }

    public void setEcDesignOrderCode(String ecDesignOrderCode) {
        this.ecDesignOrderCode = ecDesignOrderCode;
    }

    public String getEcDesignOrderDoc() {
        return ecDesignOrderDoc;
    }

    public void setEcDesignOrderDoc(String ecDesignOrderDoc) {
        this.ecDesignOrderDoc = ecDesignOrderDoc;
    }

    public String getEcDesignDrawing() {
        return ecDesignDrawing;
    }

    public void setEcDesignDrawing(String ecDesignDrawing) {
        this.ecDesignDrawing = ecDesignDrawing;
    }

    public String getEcCunitCostDoc() {
        return ecCunitCostDoc;
    }

    public void setEcCunitCostDoc(String ecCunitCostDoc) {
        this.ecCunitCostDoc = ecCunitCostDoc;
    }

    public String getEcNegotiationDoc() {
        return ecNegotiationDoc;
    }

    public void setEcNegotiationDoc(String ecNegotiationDoc) {
        this.ecNegotiationDoc = ecNegotiationDoc;
    }

    public String getEcCreateUid() {
        return ecCreateUid;
    }

    public void setEcCreateUid(String ecCreateUid) {
        this.ecCreateUid = ecCreateUid;
    }

    public String getEcCreateUname() {
        return ecCreateUname;
    }

    public void setEcCreateUname(String ecCreateUname) {
        this.ecCreateUname = ecCreateUname;
    }

    public Date getEcCreateTime() {
        return ecCreateTime;
    }

    public void setEcCreateTime(Date ecCreateTime) {
        this.ecCreateTime = ecCreateTime;
    }

    public String getEcCreateCId() {
        return ecCreateCId;
    }

    public void setEcCreateCId(String ecCreateCId) {
        this.ecCreateCId = ecCreateCId;
    }

    public Integer getEcState() {
        return ecState;
    }

    public void setEcState(Integer ecState) {
        this.ecState = ecState;
    }

    public Date getEcStateTime() {
        return ecStateTime;
    }

    public void setEcStateTime(Date ecStateTime) {
        this.ecStateTime = ecStateTime;
    }

    public String getEcDesignId() {
        return ecDesignId;
    }

    public void setEcDesignId(String ecDesignId) {
        this.ecDesignId = ecDesignId;
    }

    public String getEcPartName() {
        return ecPartName;
    }

    public void setEcPartName(String ecPartName) {
        this.ecPartName = ecPartName;
    }

    public String getEcName() {
        return ecName;
    }

    public void setEcName(String ecName) {
        this.ecName = ecName;
    }

    public String getEcApprovalMatter() {
        return ecApprovalMatter;
    }

    public void setEcApprovalMatter(String ecApprovalMatter) {
        this.ecApprovalMatter = ecApprovalMatter;
    }

    public String getEnisId() {
        return enisId;
    }

    public void setEnisId(String enisId) {
        this.enisId = enisId;
    }

    public String getEcChangeType() {
        return ecChangeType;
    }

    public void setEcChangeType(String ecChangeType) {
        this.ecChangeType = ecChangeType;
    }

    public String getEcCnature() {
        return ecCnature;
    }

    public void setEcCnature(String ecCnature) {
        this.ecCnature = ecCnature;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getEcSjFile() {
        return ecSjFile;
    }

    public void setEcSjFile(String ecSjFile) {
        this.ecSjFile = ecSjFile;
    }

    public String getEcJlFile() {
        return ecJlFile;
    }

    public void setEcJlFile(String ecJlFile) {
        this.ecJlFile = ecJlFile;
    }

    public Boolean getEcNewEc() {
        return ecNewEc;
    }

    public void setEcNewEc(Boolean ecNewEc) {
        this.ecNewEc = ecNewEc;
    }

    public String getEcConstructionName() {
        return ecConstructionName;
    }

    public void setEcConstructionName(String ecConstructionName) {
        this.ecConstructionName = ecConstructionName;
    }

    public String getEcRemark() {
        return ecRemark;
    }

    public void setEcRemark(String ecRemark) {
        this.ecRemark = ecRemark;
    }

    public String getEcHbFileYj() {
        return ecHbFileYj;
    }

    public void setEcHbFileYj(String ecHbFileYj) {
        this.ecHbFileYj = ecHbFileYj;
    }


    public String getEcHbFileYsd() {
        return ecHbFileYsd;
    }

    public void setEcHbFileYsd(String ecHbFileYsd) {
        this.ecHbFileYsd = ecHbFileYsd;
    }



    public String getEcHbFileSdd() {
        return ecHbFileSdd;
    }

    public void setEcHbFileSdd(String ecHbFileSdd) {
        this.ecHbFileSdd = ecHbFileSdd;
    }


    public String getEcHbFileBgd() {
        return ecHbFileBgd;
    }

    public void setEcHbFileBgd(String ecHbFileBgd) {
        this.ecHbFileBgd = ecHbFileBgd;
    }


    public String getEcHbFileHyjy() {
        return ecHbFileHyjy;
    }

    public void setEcHbFileHyjy(String ecHbFileHyjy) {
        this.ecHbFileHyjy = ecHbFileHyjy;
    }


    public String getEcHbFileBsys() {
        return ecHbFileBsys;
    }

    public void setEcHbFileBsys(String ecHbFileBsys) {
        this.ecHbFileBsys = ecHbFileBsys;
    }



    public String getEcHbFileTz() {
        return ecHbFileTz;
    }

    public void setEcHbFileTz(String ecHbFileTz) {
        this.ecHbFileTz = ecHbFileTz;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getEnisName() {
        return enisName;
    }

    public void setEnisName(String enisName) {
        this.enisName = enisName;
    }

    public String getEnisCodeSelf() {
        return enisCodeSelf;
    }

    public void setEnisCodeSelf(String enisCodeSelf) {
        this.enisCodeSelf = enisCodeSelf;
    }

    public String getEcPutForwardName() {
        return ecPutForwardName;
    }

    public void setEcPutForwardName(String ecPutForwardName) {
        this.ecPutForwardName = ecPutForwardName;
    }

    public String getEcType() {
        return ecType;
    }

    public void setEcType(String ecType) {
        this.ecType = ecType;
    }

    public String getEcChangeGist() {
        return ecChangeGist;
    }

    public void setEcChangeGist(String ecChangeGist) {
        this.ecChangeGist = ecChangeGist;
    }

    public String getEcIsBgzl() {
        return ecIsBgzl;
    }

    public void setEcIsBgzl(String ecIsBgzl) {
        this.ecIsBgzl = ecIsBgzl;
    }

    public String getEcGistOther() {
        return ecGistOther;
    }

    public void setEcGistOther(String ecGistOther) {
        this.ecGistOther = ecGistOther;
    }

    public String getDcoId() {
        return dcoId;
    }

    public void setDcoId(String dcoId) {
        this.dcoId = dcoId;
    }

    public String getEcDoc() {
        return ecDoc;
    }

    public void setEcDoc(String ecDoc) {
        this.ecDoc = ecDoc;
    }

    public String getEcDrawing() {
        return ecDrawing;
    }

    public void setEcDrawing(String ecDrawing) {
        this.ecDrawing = ecDrawing;
    }

    public String getEcCostBook() {
        return ecCostBook;
    }

    public void setEcCostBook(String ecCostBook) {
        this.ecCostBook = ecCostBook;
    }

    public String getEcDep() {
        return ecDep;
    }

    public void setEcDep(String ecDep) {
        this.ecDep = ecDep;
    }

    public String getEcReqFile() {
        return ecReqFile;
    }

    public void setEcReqFile(String ecReqFile) {
        this.ecReqFile = ecReqFile;
    }

    public String getEcApprovalFile() {
        return ecApprovalFile;
    }

    public void setEcApprovalFile(String ecApprovalFile) {
        this.ecApprovalFile = ecApprovalFile;
    }

    public String getEcRelevantFile() {
        return ecRelevantFile;
    }

    public void setEcRelevantFile(String ecRelevantFile) {
        this.ecRelevantFile = ecRelevantFile;
    }

    public String getEcApplyFile() {
        return ecApplyFile;
    }

    public void setEcApplyFile(String ecApplyFile) {
        this.ecApplyFile = ecApplyFile;
    }

    public String getEcMaterialFile() {
        return ecMaterialFile;
    }

    public void setEcMaterialFile(String ecMaterialFile) {
        this.ecMaterialFile = ecMaterialFile;
    }

    public String getEcOpinionFile() {
        return ecOpinionFile;
    }

    public void setEcOpinionFile(String ecOpinionFile) {
        this.ecOpinionFile = ecOpinionFile;
    }

    public String getEcFilingsFile() {
        return ecFilingsFile;
    }

    public void setEcFilingsFile(String ecFilingsFile) {
        this.ecFilingsFile = ecFilingsFile;
    }

    public String getEcEnisFile() {
        return ecEnisFile;
    }

    public void setEcEnisFile(String ecEnisFile) {
        this.ecEnisFile = ecEnisFile;
    }

    public String getEcPutForward() {
        return ecPutForward;
    }

    public void setEcPutForward(String ecPutForward) {
        this.ecPutForward = ecPutForward;
    }

    public String getEcPart() {
        return ecPart;
    }

    public void setEcPart(String ecPart) {
        this.ecPart = ecPart;
    }

    public String getEcDepName() {
        return ecDepName;
    }

    public void setEcDepName(String ecDepName) {
        this.ecDepName = ecDepName;
    }

    public String getEcDesignName() {
        return ecDesignName;
    }

    public void setEcDesignName(String ecDesignName) {
        this.ecDesignName = ecDesignName;
    }

    public String getEcDesignAbbName() {
        return ecDesignAbbName;
    }

    public void setEcDesignAbbName(String ecDesignAbbName) {
        this.ecDesignAbbName = ecDesignAbbName;
    }

    public Integer getEcDrawingLibrary() {
        return ecDrawingLibrary;
    }

    public void setEcDrawingLibrary(Integer ecDrawingLibrary) {
        this.ecDrawingLibrary = ecDrawingLibrary;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getEcSupervisorName() {
        return ecSupervisorName;
    }

    public void setEcSupervisorName(String ecSupervisorName) {
        this.ecSupervisorName = ecSupervisorName;
    }

    public String getEcConsultingUnitName() {
        return ecConsultingUnitName;
    }

    public void setEcConsultingUnitName(String ecConsultingUnitName) {
        this.ecConsultingUnitName = ecConsultingUnitName;
    }

    public String getClTitle() {
        return clTitle;
    }

    public void setClTitle(String clTitle) {
        this.clTitle = clTitle;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getEcCreateCName() {
        return ecCreateCName;
    }

    public void setEcCreateCName(String ecCreateCName) {
        this.ecCreateCName = ecCreateCName;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(String conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public String getConPartyBId() {
        return conPartyBId;
    }

    public void setConPartyBId(String conPartyBId) {
        this.conPartyBId = conPartyBId;
    }

    public String getConPartyBName() {
        return conPartyBName;
    }

    public void setConPartyBName(String conPartyBName) {
        this.conPartyBName = conPartyBName;
    }

    public String getEcSupervisorNameHistory() {
        return ecSupervisorNameHistory;
    }

    public void setEcSupervisorNameHistory(String ecSupervisorNameHistory) {
        this.ecSupervisorNameHistory = ecSupervisorNameHistory;
    }

    public String getEcConsultingUnitNameHistory() {
        return ecConsultingUnitNameHistory;
    }

    public void setEcConsultingUnitNameHistory(String ecConsultingUnitNameHistory) {
        this.ecConsultingUnitNameHistory = ecConsultingUnitNameHistory;
    }

    public String getEcSupervisorAbbName() {
        return ecSupervisorAbbName;
    }

    public void setEcSupervisorAbbName(String ecSupervisorAbbName) {
        this.ecSupervisorAbbName = ecSupervisorAbbName;
    }

    public String getEcConsultingUnitAbbName() {
        return ecConsultingUnitAbbName;
    }

    public void setEcConsultingUnitAbbName(String ecConsultingUnitAbbName) {
        this.ecConsultingUnitAbbName = ecConsultingUnitAbbName;
    }

    public String getEcCreateCAbbName() {
        return ecCreateCAbbName;
    }

    public String getEcBgzlId() {
        return ecBgzlId;
    }

    public void setEcBgzlId(String ecBgzlId) {
        this.ecBgzlId = ecBgzlId;
    }

    public void setEcCreateCAbbName(String ecCreateCAbbName) {
        this.ecCreateCAbbName = ecCreateCAbbName;
    }

    public String getConPartyBAbbName() {
        return conPartyBAbbName;
    }

    public void setConPartyBAbbName(String conPartyBAbbName) {
        this.conPartyBAbbName = conPartyBAbbName;
    }

    public String getDcoCode() {
        return dcoCode;
    }

    public void setDcoCode(String dcoCode) {
        this.dcoCode = dcoCode;
    }

    public String getDcoProjectName() {
        return dcoProjectName;
    }

    public void setDcoProjectName(String dcoProjectName) {
        this.dcoProjectName = dcoProjectName;
    }

    public String getDcoMajor() {
        return dcoMajor;
    }

    public void setDcoMajor(String dcoMajor) {
        this.dcoMajor = dcoMajor;
    }

    public String getEnisCode() {
        return enisCode;
    }

    public void setEnisCode(String enisCode) {
        this.enisCode = enisCode;
    }

    public String getEcConstruction() {
        return ecConstruction;
    }

    public void setEcConstruction(String ecConstruction) {
        this.ecConstruction = ecConstruction;
    }

    public String getEcCostConsulting() {
        return ecCostConsulting;
    }

    public void setEcCostConsulting(String ecCostConsulting) {
        this.ecCostConsulting = ecCostConsulting;
    }

    public String getEcAuditType() {
        return ecAuditType;
    }

    public void setEcAuditType(String ecAuditType) {
        this.ecAuditType = ecAuditType;
    }

    public BigDecimal getEcFinalMoneyAfter() {
        return ecFinalMoneyAfter;
    }

    public void setEcFinalMoneyAfter(BigDecimal ecFinalMoneyAfter) {
        this.ecFinalMoneyAfter = ecFinalMoneyAfter;
    }

    public String getEcConstName() {
        return ecConstName;
    }

    public void setEcConstName(String ecConstName) {
        this.ecConstName = ecConstName;
    }

    public String getEcCostConsulName() {
        return ecCostConsulName;
    }

    public void setEcCostConsulName(String ecCostConsulName) {
        this.ecCostConsulName = ecCostConsulName;
    }

    public String getPreEcName() {
        return preEcName;
    }

    public void setPreEcName(String preEcName) {
        this.preEcName = preEcName;
    }

    public String getPreEcCode() {
        return preEcCode;
    }

    public void setPreEcCode(String preEcCode) {
        this.preEcCode = preEcCode;
    }

    public BigDecimal getEcTotalDayC() {
        return ecTotalDayC;
    }

    public void setEcTotalDayC(BigDecimal ecTotalDayC) {
        this.ecTotalDayC = ecTotalDayC;
    }

    public BigDecimal getEcTotalDayPlusC() {
        return ecTotalDayPlusC;
    }

    public void setEcTotalDayPlusC(BigDecimal ecTotalDayPlusC) {
        this.ecTotalDayPlusC = ecTotalDayPlusC;
    }

    public BigDecimal getEcTotalDayMinusC() {
        return ecTotalDayMinusC;
    }

    public void setEcTotalDayMinusC(BigDecimal ecTotalDayMinusC) {
        this.ecTotalDayMinusC = ecTotalDayMinusC;
    }

    public String getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(String isHistory) {
        this.isHistory = isHistory;
    }
}