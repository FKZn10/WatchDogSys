package com.jpmsoft.common.model.requestAndResponse.Audit;

import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 预算审计
 */
public class AuditBudgetModel extends ProjectCommonRes implements Serializable {
    private static final long serialVersionUID = -3580996354093610883L;
    @ProcessFields(type = ProcessFieldEnum.id)
    private String abId;
    private String pId;
    private String secId;
    private BigDecimal abAmount;
    private String abCreateCid;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String abCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String abCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date abCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer abState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date abStateTime;
    private String abContactName;
    private String abContactTel;
    @FileFields(index = 1)
    private String abFileApproval;
    @FileFields(index = 2)
    private String abFileEstimate;
    @FileFields(index = 3)
    private String abFileBidDoc;
    @FileFields(index = 4)
    private String abFileDesign;
    @FileFields(index = 5)
    private String abFileOther;
    private String abRemark;
    @FileFields(index = 6)
    private String abAuditResult;

    private String secName;
    private String secCode;
    private String cName;
    private String cNameHistory;
    private String cAbbName;
    @FileFields(index = 1, type = "abFileApprovalIds")
    private String abFileApprovalIds;
    @FileFields(index = 2, type = "abFileEstimateIds")
    private String abFileEstimateIds;
    @FileFields(index = 3, type = "abFileBidDocIds")
    private String abFileBidDocIds;
    @FileFields(index = 4, type = "abFileDesignIds")
    private String abFileDesignIds;
    @FileFields(index = 5, type = "abFileOtherIds")
    private String abFileOtherIds;
    @FileFields(index = 6, type = "abAuditResultIds")
    private String abAuditResultIds;

    public String getAbId() {
        return abId;
    }

    public void setAbId(String abId) {
        this.abId = abId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public BigDecimal getAbAmount() {
        return abAmount;
    }

    public void setAbAmount(BigDecimal abAmount) {
        this.abAmount = abAmount;
    }

    public String getAbCreateCid() {
        return abCreateCid;
    }

    public void setAbCreateCid(String abCreateCid) {
        this.abCreateCid = abCreateCid;
    }

    public String getAbCreateUid() {
        return abCreateUid;
    }

    public void setAbCreateUid(String abCreateUid) {
        this.abCreateUid = abCreateUid;
    }

    public String getAbCreateUname() {
        return abCreateUname;
    }

    public void setAbCreateUname(String abCreateUname) {
        this.abCreateUname = abCreateUname;
    }

    public Date getAbCreateTime() {
        return abCreateTime;
    }

    public void setAbCreateTime(Date abCreateTime) {
        this.abCreateTime = abCreateTime;
    }

    public Integer getAbState() {
        return abState;
    }

    public void setAbState(Integer abState) {
        this.abState = abState;
    }

    public Date getAbStateTime() {
        return abStateTime;
    }

    public void setAbStateTime(Date abStateTime) {
        this.abStateTime = abStateTime;
    }

    public String getAbContactName() {
        return abContactName;
    }

    public void setAbContactName(String abContactName) {
        this.abContactName = abContactName;
    }

    public String getAbContactTel() {
        return abContactTel;
    }

    public void setAbContactTel(String abContactTel) {
        this.abContactTel = abContactTel;
    }

    public String getAbFileApproval() {
        return abFileApproval;
    }

    public void setAbFileApproval(String abFileApproval) {
        this.abFileApproval = abFileApproval;
    }

    public String getAbFileEstimate() {
        return abFileEstimate;
    }

    public void setAbFileEstimate(String abFileEstimate) {
        this.abFileEstimate = abFileEstimate;
    }

    public String getAbFileBidDoc() {
        return abFileBidDoc;
    }

    public void setAbFileBidDoc(String abFileBidDoc) {
        this.abFileBidDoc = abFileBidDoc;
    }

    public String getAbFileDesign() {
        return abFileDesign;
    }

    public void setAbFileDesign(String abFileDesign) {
        this.abFileDesign = abFileDesign;
    }

    public String getAbFileOther() {
        return abFileOther;
    }

    public void setAbFileOther(String abFileOther) {
        this.abFileOther = abFileOther;
    }

    public String getAbRemark() {
        return abRemark;
    }

    public void setAbRemark(String abRemark) {
        this.abRemark = abRemark;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcNameHistory() {
        return cNameHistory;
    }

    public void setcNameHistory(String cNameHistory) {
        this.cNameHistory = cNameHistory;
    }

    public String getcAbbName() {
        return cAbbName;
    }

    public void setcAbbName(String cAbbName) {
        this.cAbbName = cAbbName;
    }

    public String getAbFileApprovalIds() {
        return abFileApprovalIds;
    }

    public void setAbFileApprovalIds(String abFileApprovalIds) {
        this.abFileApprovalIds = abFileApprovalIds;
    }

    public String getAbFileEstimateIds() {
        return abFileEstimateIds;
    }

    public void setAbFileEstimateIds(String abFileEstimateIds) {
        this.abFileEstimateIds = abFileEstimateIds;
    }

    public String getAbFileBidDocIds() {
        return abFileBidDocIds;
    }

    public void setAbFileBidDocIds(String abFileBidDocIds) {
        this.abFileBidDocIds = abFileBidDocIds;
    }

    public String getAbFileDesignIds() {
        return abFileDesignIds;
    }

    public void setAbFileDesignIds(String abFileDesignIds) {
        this.abFileDesignIds = abFileDesignIds;
    }

    public String getAbFileOtherIds() {
        return abFileOtherIds;
    }

    public void setAbFileOtherIds(String abFileOtherIds) {
        this.abFileOtherIds = abFileOtherIds;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

    public String getAbAuditResult() {
        return abAuditResult;
    }

    public void setAbAuditResult(String abAuditResult) {
        this.abAuditResult = abAuditResult;
    }

    public String getAbAuditResultIds() {
        return abAuditResultIds;
    }

    public void setAbAuditResultIds(String abAuditResultIds) {
        this.abAuditResultIds = abAuditResultIds;
    }
}
