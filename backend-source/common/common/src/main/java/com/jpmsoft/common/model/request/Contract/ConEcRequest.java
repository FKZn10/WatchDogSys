package com.jpmsoft.common.model.request.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.annotations.ClassInfo;
import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 变更事项申请
 */

public class ConEcRequest extends BaseFileRequest {
    private String ecId;
    private String conId;
    private String preEcId;
    private String secId;
    private String ecSupervisorId;
    private String ecConsultingUnitId;
    private String ecCode;
    private String ecRemark;
    private String clId;
    private String ecResponsibleParty;
    private String ecMainCause;
    private BigDecimal ecApplyMoney;
    private Integer ecApplyMoneyType;
    private Integer ecDuration;
    private Integer ecDurationType;
    private String ecGrade;
    private BigDecimal ecTotalMoney;
    private BigDecimal ecTotalMoneyPlus;
    private BigDecimal ecTotalMoneyMinus;
    private BigDecimal ecFinalMoney;
    private BigDecimal ecTotalMoneyC;
    private Float ecTotalMoneyCRate;
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
    private String ecHbFileYj;
    private String ecHbFileYjIds;
    private String ecHbFileYsd;
    private String ecHbFileYsdIds;
    private String ecHbFileSdd;
    private String ecHbFileSddIds;
    private String ecHbFileBgd;
    private String ecHbFileBgdIds;
    private String ecHbFileHyjy;
    private String ecHbFileHyjyIds;
    private String ecHbFileBsys;
    private String ecHbFileBsysIds;
    private String ecHbFileTz;
    private String ecHbFileTzIds;

    private String ecDrawing;
    private String ecCostBook;
    private String ecDep;
    private String ecReqFile;
    private String ecApprovalFile;
    private String ecRelevantFile;
    private String ecApplyFile;
    private String ecMaterialFile;
    private String ecOpinionFile;
    private String ecIsBgzl;
    private String ecFilingsFile;
    private String ecEnisFile;
    private String ecPutForward;
    private String ecPart;
    private String ecConstruction;
    private String ecCostConsulting;
    private String ecAuditType;
    private BigDecimal ecFinalMoneyAfter;
    private String ecName;
    private String ecApprovalMatter;
    private String enisId;
    private String ecChangeType;
    private String ecBgzlId;
    private String ecCnature;
    private String pId;
    private String ecSjFile;
    private String ecJlFile;
    private Boolean ecNewEc;
    private String ecConstructionName;

    private String ecSjFileIds;
    private String ecJlFileIds;
    private String enisName;
    private String ecEnisFileIds;
    private String ecReqFileIds;
    private String ecApprovalFileIds;
    private String ecRelevantFileIds;
    private String ecApplyFileIds;
    private String ecMaterialFileIds;
    private String ecOpinionFileIds;
    private String ecFilingsFileIds;
    private String ecDepName;
    private String pName;
    private String conName;

    private BigDecimal ecTotalMoneyMinusC;

    private BigDecimal ecTotalMoneyPlusC;

    private String cmnId;

    /**
     * 是否根据变更费用申报筛选
     * 0：全部
     * 1:有变更费用申报
     * 2：没有变更费用申报
     */
    private Integer conEcFeeFilter;

    private String pConstructionUnitName;
    private String ecDocIds;
    private String ecDrawingIds;
    private String ecCostBookIds;
    private List<String> ecApplyMoneys;
    private List<String> ecDurations;
    private List<String> ecCreateTimes;

    private String ecConstName;
    private String ecCostConsulName;
    private String ecSupervisorName;
    private String enisCode;
    private List<String> ecTotalMoneys;
    private List<String> ecTotalMoneyPluses;
    private List<String> ecTotalMoneyMinuses;
    private List<String> ecFinalMoneys;
    private List<String> ecFinalMoneyAfters;
    private List<String> ecTotalMoneyCs;
    private List<String> ecTotalMoneyCRates;

    // 合同累计变更工期（包含本次）
    private BigDecimal ecTotalDayC;

    // 累计正变更工期
    private BigDecimal ecTotalDayPlusC;
    // 累计负变更工期
    private BigDecimal ecTotalDayMinusC;

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
    //是否历史数据 0是非历史数据 1是历史数据
    private String isHistory;

    // 数据进行分离 01 以前 02 当前
    private String ecDataSeparate;

    @JsonProperty("pNameList")
    @ClassInfo(title = "项目名称", type = "string", value = "pNameList", dbValue = "pro1.P_Name",no = 1)
    private List<String> pNameList;

    @ClassInfo(title = "合同名称", type = "string", value = "conNameList", dbValue = "con.Con_Name",no = 2)
    private List<String> conNameList;

    @ClassInfo(title = "变更类型", type = "dictionary", value = "ecChangeTypeList", dbValue = "mn.Ec_Change_Type",dictionary = "JPMsoft_BGLX",no = 3)
    private List<String> ecChangeTypeList;

    @ClassInfo(title = "创建人", type = "string", value = "ecCreateUnameList", dbValue = "mn.Ec_Create_Uname",no = 4)
    private List<String> ecCreateUnameList;


    @ClassInfo(title = "流程状态", type = "dictionary", value = "ecStateList", dbValue = "mn.ec_state",dictionary = "JPMsoft_LCZT",no = 5)
    private List<Integer> ecStateList;

    @ClassInfo(title = "实施单位", type = "string", value = "ecConstructionNameList", dbValue = "mn.Ec_Construction_Name",no = 6)
    private List<String> ecConstructionNameList;

    private String ecDataSeparateType;

    private BigDecimal vfApplyMoney;

    private String vfDataSeparateType;

    private String type;

    private String itemCategory;

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVfDataSeparateType() {
        return vfDataSeparateType;
    }

    public void setVfDataSeparateType(String vfDataSeparateType) {
        this.vfDataSeparateType = vfDataSeparateType;
    }

    public BigDecimal getVfApplyMoney() {
        return vfApplyMoney;
    }

    public void setVfApplyMoney(BigDecimal vfApplyMoney) {
        this.vfApplyMoney = vfApplyMoney;
    }

    public String getEcDataSeparateType() {
        return ecDataSeparateType;
    }

    public void setEcDataSeparateType(String ecDataSeparateType) {
        this.ecDataSeparateType = ecDataSeparateType;
    }

    public List<String> getpNameList() {
        return pNameList;
    }

    public void setpNameList(List<String> pNameList) {
        this.pNameList = pNameList;
    }

    public List<String> getConNameList() {
        return conNameList;
    }

    public void setConNameList(List<String> conNameList) {
        this.conNameList = conNameList;
    }

    public List<String> getEcChangeTypeList() {
        return ecChangeTypeList;
    }

    public void setEcChangeTypeList(List<String> ecChangeTypeList) {
        this.ecChangeTypeList = ecChangeTypeList;
    }

    public List<String> getEcCreateUnameList() {
        return ecCreateUnameList;
    }

    public void setEcCreateUnameList(List<String> ecCreateUnameList) {
        this.ecCreateUnameList = ecCreateUnameList;
    }

    public List<Integer> getEcStateList() {
        return ecStateList;
    }

    public void setEcStateList(List<Integer> ecStateList) {
        this.ecStateList = ecStateList;
    }

    public List<String> getEcConstructionNameList() {
        return ecConstructionNameList;
    }

    public void setEcConstructionNameList(List<String> ecConstructionNameList) {
        this.ecConstructionNameList = ecConstructionNameList;
    }

    public String getEcDataSeparate() {
        return ecDataSeparate;
    }

    public void setEcDataSeparate(String ecDataSeparate) {
        this.ecDataSeparate = ecDataSeparate;
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


    public String getEcHbFileYjIds() {
        return ecHbFileYjIds;
    }

    public void setEcHbFileYjIds(String ecHbFileYjIds) {
        this.ecHbFileYjIds = ecHbFileYjIds;
    }


    public String getEcHbFileYsd() {
        return ecHbFileYsd;
    }

    public void setEcHbFileYsd(String ecHbFileYsd) {
        this.ecHbFileYsd = ecHbFileYsd;
    }


    public String getEcHbFileYsdIds() {
        return ecHbFileYsdIds;
    }

    public void setEcHbFileYsdIds(String ecHbFileYsdIds) {
        this.ecHbFileYsdIds = ecHbFileYsdIds;
    }

    public String getEcHbFileSdd() {
        return ecHbFileSdd;
    }

    public void setEcHbFileSdd(String ecHbFileSdd) {
        this.ecHbFileSdd = ecHbFileSdd;
    }


    public String getEcHbFileSddIds() {
        return ecHbFileSddIds;
    }

    public void setEcHbFileSddIds(String ecHbFileSddIds) {
        this.ecHbFileSddIds = ecHbFileSddIds;
    }

    public String getEcHbFileBgd() {
        return ecHbFileBgd;
    }

    public void setEcHbFileBgd(String ecHbFileBgd) {
        this.ecHbFileBgd = ecHbFileBgd;
    }


    public String getEcHbFileBgdIds() {
        return ecHbFileBgdIds;
    }

    public void setEcHbFileBgdIds(String ecHbFileBgdIds) {
        this.ecHbFileBgdIds = ecHbFileBgdIds;
    }

    public String getEcHbFileHyjy() {
        return ecHbFileHyjy;
    }

    public void setEcHbFileHyjy(String ecHbFileHyjy) {
        this.ecHbFileHyjy = ecHbFileHyjy;
    }


    public String getEcHbFileHyjyIds() {
        return ecHbFileHyjyIds;
    }

    public void setEcHbFileHyjyIds(String ecHbFileHyjyIds) {
        this.ecHbFileHyjyIds = ecHbFileHyjyIds;
    }

    public String getEcHbFileBsys() {
        return ecHbFileBsys;
    }

    public void setEcHbFileBsys(String ecHbFileBsys) {
        this.ecHbFileBsys = ecHbFileBsys;
    }


    public String getEcHbFileBsysIds() {
        return ecHbFileBsysIds;
    }

    public void setEcHbFileBsysIds(String ecHbFileBsysIds) {
        this.ecHbFileBsysIds = ecHbFileBsysIds;
    }

    public String getEcHbFileTz() {
        return ecHbFileTz;
    }

    public void setEcHbFileTz(String ecHbFileTz) {
        this.ecHbFileTz = ecHbFileTz;
    }


    public String getEcHbFileTzIds() {
        return ecHbFileTzIds;
    }

    public void setEcHbFileTzIds(String ecHbFileTzIds) {
        this.ecHbFileTzIds = ecHbFileTzIds;
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

    public Integer getEcDrawingLibrary() {
        return ecDrawingLibrary;
    }

    public void setEcDrawingLibrary(Integer ecDrawingLibrary) {
        this.ecDrawingLibrary = ecDrawingLibrary;
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

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
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

    public String getEcSjFileIds() {
        return ecSjFileIds;
    }

    public void setEcSjFileIds(String ecSjFileIds) {
        this.ecSjFileIds = ecSjFileIds;
    }

    public String getEcJlFileIds() {
        return ecJlFileIds;
    }

    public void setEcJlFileIds(String ecJlFileIds) {
        this.ecJlFileIds = ecJlFileIds;
    }

    public String getEnisName() {
        return enisName;
    }

    public void setEnisName(String enisName) {
        this.enisName = enisName;
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

    public String getEcBgzlId() {
        return ecBgzlId;
    }

    public void setEcBgzlId(String ecBgzlId) {
        this.ecBgzlId = ecBgzlId;
    }

    public String getEcIsBgzl() {
        return ecIsBgzl;
    }

    public void setEcIsBgzl(String ecIsBgzl) {
        this.ecIsBgzl = ecIsBgzl;
    }

    public void setEcDrawing(String ecDrawing) {
        this.ecDrawing = ecDrawing;
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

    public String getEcEnisFileIds() {
        return ecEnisFileIds;
    }

    public void setEcEnisFileIds(String ecEnisFileIds) {
        this.ecEnisFileIds = ecEnisFileIds;
    }

    public String getEcReqFileIds() {
        return ecReqFileIds;
    }

    public void setEcReqFileIds(String ecReqFileIds) {
        this.ecReqFileIds = ecReqFileIds;
    }

    public String getEcApprovalFileIds() {
        return ecApprovalFileIds;
    }

    public void setEcApprovalFileIds(String ecApprovalFileIds) {
        this.ecApprovalFileIds = ecApprovalFileIds;
    }

    public String getEcRelevantFileIds() {
        return ecRelevantFileIds;
    }

    public void setEcRelevantFileIds(String ecRelevantFileIds) {
        this.ecRelevantFileIds = ecRelevantFileIds;
    }

    public String getEcApplyFileIds() {
        return ecApplyFileIds;
    }

    public void setEcApplyFileIds(String ecApplyFileIds) {
        this.ecApplyFileIds = ecApplyFileIds;
    }

    public String getEcMaterialFileIds() {
        return ecMaterialFileIds;
    }

    public void setEcMaterialFileIds(String ecMaterialFileIds) {
        this.ecMaterialFileIds = ecMaterialFileIds;
    }

    public String getEcOpinionFileIds() {
        return ecOpinionFileIds;
    }

    public void setEcOpinionFileIds(String ecOpinionFileIds) {
        this.ecOpinionFileIds = ecOpinionFileIds;
    }

    public String getEcFilingsFileIds() {
        return ecFilingsFileIds;
    }

    public void setEcFilingsFileIds(String ecFilingsFileIds) {
        this.ecFilingsFileIds = ecFilingsFileIds;
    }

    public String getEcDepName() {
        return ecDepName;
    }

    public String getCmnId() {
        return cmnId;
    }

    public void setCmnId(String cmnId) {
        this.cmnId = cmnId;
    }

    public void setEcDepName(String ecDepName) {
        this.ecDepName = ecDepName;
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

    public Integer getConEcFeeFilter() {
        return conEcFeeFilter;
    }

    public void setConEcFeeFilter(Integer conEcFeeFilter) {
        this.conEcFeeFilter = conEcFeeFilter;
    }

    @Override
    public String getpConstructionUnitName() {
        return pConstructionUnitName;
    }

    @Override
    public void setpConstructionUnitName(String pConstructionUnitName) {
        this.pConstructionUnitName = pConstructionUnitName;
    }

    public String getEcDocIds() {
        return ecDocIds;
    }

    public void setEcDocIds(String ecDocIds) {
        this.ecDocIds = ecDocIds;
    }

    public String getEcDrawingIds() {
        return ecDrawingIds;
    }

    public void setEcDrawingIds(String ecDrawingIds) {
        this.ecDrawingIds = ecDrawingIds;
    }

    public String getEcCostBookIds() {
        return ecCostBookIds;
    }

    public void setEcCostBookIds(String ecCostBookIds) {
        this.ecCostBookIds = ecCostBookIds;
    }

    public List<String> getEcApplyMoneys() {
        return ecApplyMoneys;
    }

    public void setEcApplyMoneys(List<String> ecApplyMoneys) {
        this.ecApplyMoneys = ecApplyMoneys;
    }

    public List<String> getEcDurations() {
        return ecDurations;
    }

    public void setEcDurations(List<String> ecDurations) {
        this.ecDurations = ecDurations;
    }

    public List<String> getEcCreateTimes() {
        return ecCreateTimes;
    }

    public void setEcCreateTimes(List<String> ecCreateTimes) {
        this.ecCreateTimes = ecCreateTimes;
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

    public String getEcSupervisorName() {
        return ecSupervisorName;
    }

    public void setEcSupervisorName(String ecSupervisorName) {
        this.ecSupervisorName = ecSupervisorName;
    }

    public String getEnisCode() {
        return enisCode;
    }

    public void setEnisCode(String enisCode) {
        this.enisCode = enisCode;
    }

    public List<String> getEcTotalMoneys() {
        return ecTotalMoneys;
    }

    public void setEcTotalMoneys(List<String> ecTotalMoneys) {
        this.ecTotalMoneys = ecTotalMoneys;
    }

    public List<String> getEcTotalMoneyPluses() {
        return ecTotalMoneyPluses;
    }

    public void setEcTotalMoneyPluses(List<String> ecTotalMoneyPluses) {
        this.ecTotalMoneyPluses = ecTotalMoneyPluses;
    }

    public List<String> getEcTotalMoneyMinuses() {
        return ecTotalMoneyMinuses;
    }

    public void setEcTotalMoneyMinuses(List<String> ecTotalMoneyMinuses) {
        this.ecTotalMoneyMinuses = ecTotalMoneyMinuses;
    }

    public List<String> getEcFinalMoneys() {
        return ecFinalMoneys;
    }

    public void setEcFinalMoneys(List<String> ecFinalMoneys) {
        this.ecFinalMoneys = ecFinalMoneys;
    }

    public List<String> getEcFinalMoneyAfters() {
        return ecFinalMoneyAfters;
    }

    public void setEcFinalMoneyAfters(List<String> ecFinalMoneyAfters) {
        this.ecFinalMoneyAfters = ecFinalMoneyAfters;
    }

    public List<String> getEcTotalMoneyCs() {
        return ecTotalMoneyCs;
    }

    public void setEcTotalMoneyCs(List<String> ecTotalMoneyCs) {
        this.ecTotalMoneyCs = ecTotalMoneyCs;
    }

    public List<String> getEcTotalMoneyCRates() {
        return ecTotalMoneyCRates;
    }

    public void setEcTotalMoneyCRates(List<String> ecTotalMoneyCRates) {
        this.ecTotalMoneyCRates = ecTotalMoneyCRates;
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