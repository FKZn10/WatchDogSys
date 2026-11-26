package com.jpmsoft.common.model.requestAndResponse.Oa;

import com.jpmsoft.common.base.BaseFileDto;

import java.util.Date;
import java.util.List;

/**
 * 知识库文件
 */
public class OaKnowledgeBaseFileModel extends BaseFileDto {

    private String kbfId;
    private String kbId;
    //文件名称
    private String kbfName;
    private String kbfCreateUid;
    private String kbfCreateUname;
    private Date kbfCreateTime;
    private String kbfType;
    private Float kbfSize;
    //状态
    private Integer kbfState;
    //文件文号
    private String kbfCode;
    private Date kbfPrintTime;
    //是否鉴权 0 不鉴权 1 鉴权
    private String kbfPermission;
    //文件标题
    private String kbfTitle;
    //内容摘要
    private String kbfSummary;
    //文档内容
    private String kbfContent;
    //标签
    private String kbfTag;
    //封面
    private String kbfCover;
    private String kbfVersionNumber;
    //文档附件
    private String kbfFile;

    private String kbfFileIds;
    private String kbfCoverIds;

    private String tId;

    private String tCode;

    private String approveStatus;

    private Date approveTime;

    private Date kbfPublishDate;
    private Date kbfPublishDate0;

    private Date kbfPublishDate1;

    private String kbName;

    private Integer status;

    private Date statusTime;
    private String fileRelationId;
    private String cId;
    private Boolean constructionControl;
    private Boolean constructionUnit;
    private List<String> departments;

    private Boolean KbfIsDelete;

    private String executionStatus;
    private Boolean groupFiling;
    private Date effectiveDate;
    private Date expiringDate;
    private String effectiveDateType;

    private List<String> executionStatusList;

    private Boolean hasNotOverChange;

    private String relateKbfId;

    // 发文单位
    private String partyAsUnitName;

    // 是否向量化 00-未向量化 01-已向量化
    private String isVectorized;

    public String getIsVectorized() {
        return isVectorized;
    }

    public void setIsVectorized(String isVectorized) {
        this.isVectorized = isVectorized;
    }

    public String getPartyAsUnitName() {
        return partyAsUnitName;
    }

    public void setPartyAsUnitName(String partyAsUnitName) {
        this.partyAsUnitName = partyAsUnitName;
    }

    public String getRelateKbfId() {
        return relateKbfId;
    }

    public void setRelateKbfId(String relateKbfId) {
        this.relateKbfId = relateKbfId;
    }

    public Boolean getHasNotOverChange() {
        return hasNotOverChange;
    }

    public void setHasNotOverChange(Boolean hasNotOverChange) {
        this.hasNotOverChange = hasNotOverChange;
    }

    public List<String> getExecutionStatusList() {
        return executionStatusList;
    }

    public void setExecutionStatusList(List<String> executionStatusList) {
        this.executionStatusList = executionStatusList;
    }

    public String getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
    }

    public Boolean getGroupFiling() {
        return groupFiling;
    }

    public void setGroupFiling(Boolean groupFiling) {
        this.groupFiling = groupFiling;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }

    public String getEffectiveDateType() {
        return effectiveDateType;
    }

    public void setEffectiveDateType(String effectiveDateType) {
        this.effectiveDateType = effectiveDateType;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Boolean getConstructionControl() {
        return constructionControl;
    }

    public void setConstructionControl(Boolean constructionControl) {
        this.constructionControl = constructionControl;
    }

    public Boolean getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(Boolean constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }
    public String getKbName() {
        return kbName;
    }

    public void setKbName(String kbName) {
        this.kbName = kbName;
    }

    public Date getKbfPublishDate0() {
        return kbfPublishDate0;
    }

    public void setKbfPublishDate0(Date kbfPublishDate0) {
        this.kbfPublishDate0 = kbfPublishDate0;
    }

    public Date getKbfPublishDate1() {
        return kbfPublishDate1;
    }

    public void setKbfPublishDate1(Date kbfPublishDate1) {
        this.kbfPublishDate1 = kbfPublishDate1;
    }

    public Date getKbfPublishDate(){
        return kbfPublishDate;
    }
    public void setKbfPublishDate(Date kbfPublishDate){this.kbfPublishDate=kbfPublishDate;}

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getKbfId() {
        return kbfId;
    }

    public void setKbfId(String kbfId) {
        this.kbfId = kbfId;
    }

    public String getKbId() {
        return kbId;
    }

    public void setKbId(String kbId) {
        this.kbId = kbId;
    }

    public String getKbfName() {
        return kbfName;
    }

    public void setKbfName(String kbfName) {
        this.kbfName = kbfName;
    }

    public String getKbfCreateUid() {
        return kbfCreateUid;
    }

    public void setKbfCreateUid(String kbfCreateUid) {
        this.kbfCreateUid = kbfCreateUid;
    }

    public String getKbfCreateUname() {
        return kbfCreateUname;
    }

    public void setKbfCreateUname(String kbfCreateUname) {
        this.kbfCreateUname = kbfCreateUname;
    }

    public Date getKbfCreateTime() {
        return kbfCreateTime;
    }

    public void setKbfCreateTime(Date kbfCreateTime) {
        this.kbfCreateTime = kbfCreateTime;
    }

    public String getKbfType() {
        return kbfType;
    }

    public void setKbfType(String kbfType) {
        this.kbfType = kbfType;
    }

    public Float getKbfSize() {
        return kbfSize;
    }

    public void setKbfSize(Float kbfSize) {
        this.kbfSize = kbfSize;
    }

    public Integer getKbfState() {
        return kbfState;
    }

    public void setKbfState(Integer kbfState) {
        this.kbfState = kbfState;
    }

    public String getKbfCode() {
        return kbfCode;
    }

    public void setKbfCode(String kbfCode) {
        this.kbfCode = kbfCode;
    }

    public String getKbfPermission() {
        return kbfPermission;
    }

    public void setKbfPermission(String kbfPermission) {
        this.kbfPermission = kbfPermission;
    }

    public String getKbfTitle() {
        return kbfTitle;
    }

    public void setKbfTitle(String kbfTitle) {
        this.kbfTitle = kbfTitle;
    }

    public String getKbfSummary() {
        return kbfSummary;
    }

    public void setKbfSummary(String kbfSummary) {
        this.kbfSummary = kbfSummary;
    }

    public String getKbfContent() {
        return kbfContent;
    }

    public void setKbfContent(String kbfContent) {
        this.kbfContent = kbfContent;
    }

    public String getKbfTag() {
        return kbfTag;
    }

    public void setKbfTag(String kbfTag) {
        this.kbfTag = kbfTag;
    }

    public String getKbfCover() {
        return kbfCover;
    }

    public void setKbfCover(String kbfCover) {
        this.kbfCover = kbfCover;
    }

    public String getKbfVersionNumber() {
        return kbfVersionNumber;
    }

    public void setKbfVersionNumber(String kbfVersionNumber) {
        this.kbfVersionNumber = kbfVersionNumber;
    }

    public String getKbfFile() {
        return kbfFile;
    }

    public void setKbfFile(String kbfFile) {
        this.kbfFile = kbfFile;
    }

    public String getKbfFileIds() {
        return kbfFileIds;
    }

    public void setKbfFileIds(String kbfFileIds) {
        this.kbfFileIds = kbfFileIds;
    }

    public String getKbfCoverIds() {
        return kbfCoverIds;
    }

    public void setKbfCoverIds(String kbfCoverIds) {
        this.kbfCoverIds = kbfCoverIds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Date statusTime) {
        this.statusTime = statusTime;
    }

    public String getFileRelationId() {
        return fileRelationId;
    }

    public void setFileRelationId(String fileRelationId) {
        this.fileRelationId = fileRelationId;
    }

    public Date getKbfPrintTime() {
        return kbfPrintTime;
    }

    public void setKbfPrintTime(Date kbfPrintTime) {
        this.kbfPrintTime = kbfPrintTime;
    }

    public Boolean getKbfIsDelete() {
        return KbfIsDelete;
    }

    public void setKbfIsDelete(Boolean kbfIsDelete) {
        KbfIsDelete = kbfIsDelete;
    }
}
