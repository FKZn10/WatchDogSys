package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目建议书(项目信息源)
 */
public class PreProProposalModel extends BaseFileRequest {

    private String ppId;
    //项目建议书名称
    private String ppName;
    //项目编码
    private String ppCode;
    //项目名称
    private String ppProName;
    //申报投资估算
    private BigDecimal ppInvestment;
    //可研编制单位ID
    private String ppFeasibilityUnitId;
    //估算审核单位ID
    private String ppEstimationUnitId;
    //估算清单文件上传
    private String ppInvestmentFile;
    //备注
    private String ppRemark;
    private String ppCreateUid;
    private String ppCreateUname;
    private Date ppCreateTime;
    private Integer ppState;
    private Date ppStateTime;
    //项目类型
    private Integer ppType;
    //是否有监理单位参与
    private Integer ppIsParticipate;

    private String ppFeasibilityUnitName;
    private String ppEstimationUnitName;
    private String ppFeasibilityUnitNameHistory;
    private String ppEstimationUnitNameHistory;
    private String ppInvestmentFileIds;
    //单位简称
    private String ppFeasibilityAbbName;
    private String ppEstimationAbbName;

    //项目所属部门
    private String ppDepartment;
    private String ppDepName;
    private List<String> ppInvestments;
    private List<String> ppCreateTimes;

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public String getPpName() {
        return ppName;
    }

    public void setPpName(String ppName) {
        this.ppName = ppName;
    }

    public String getPpProName() {
        return ppProName;
    }

    public String getPpCode() {
        return ppCode;
    }

    public void setPpCode(String ppCode) {
        this.ppCode = ppCode;
    }

    public void setPpProName(String ppProName) {
        this.ppProName = ppProName;
    }

    public BigDecimal getPpInvestment() {
        return ppInvestment;
    }

    public void setPpInvestment(BigDecimal ppInvestment) {
        this.ppInvestment = ppInvestment;
    }

    public String getPpFeasibilityUnitId() {
        return ppFeasibilityUnitId;
    }

    public void setPpFeasibilityUnitId(String ppFeasibilityUnitId) {
        this.ppFeasibilityUnitId = ppFeasibilityUnitId;
    }

    public String getPpEstimationUnitId() {
        return ppEstimationUnitId;
    }

    public void setPpEstimationUnitId(String ppEstimationUnitId) {
        this.ppEstimationUnitId = ppEstimationUnitId;
    }

    public String getPpInvestmentFile() {
        return ppInvestmentFile;
    }

    public void setPpInvestmentFile(String ppInvestmentFile) {
        this.ppInvestmentFile = ppInvestmentFile;
    }

    public String getPpRemark() {
        return ppRemark;
    }

    public void setPpRemark(String ppRemark) {
        this.ppRemark = ppRemark;
    }

    public String getPpCreateUid() {
        return ppCreateUid;
    }

    public void setPpCreateUid(String ppCreateUid) {
        this.ppCreateUid = ppCreateUid;
    }

    public String getPpCreateUname() {
        return ppCreateUname;
    }

    public void setPpCreateUname(String ppCreateUname) {
        this.ppCreateUname = ppCreateUname;
    }

    public Date getPpCreateTime() {
        return ppCreateTime;
    }

    public void setPpCreateTime(Date ppCreateTime) {
        this.ppCreateTime = ppCreateTime;
    }

    public Integer getPpState() {
        return ppState;
    }

    public void setPpState(Integer ppState) {
        this.ppState = ppState;
    }

    public Date getPpStateTime() {
        return ppStateTime;
    }

    public void setPpStateTime(Date ppStateTime) {
        this.ppStateTime = ppStateTime;
    }

    public Integer getPpType() {
        return ppType;
    }

    public void setPpType(Integer ppType) {
        this.ppType = ppType;
    }

    public Integer getPpIsParticipate() {
        return ppIsParticipate;
    }

    public void setPpIsParticipate(Integer ppIsParticipate) {
        this.ppIsParticipate = ppIsParticipate;
    }

    public String getPpFeasibilityUnitName() {
        return ppFeasibilityUnitName;
    }

    public void setPpFeasibilityUnitName(String ppFeasibilityUnitName) {
        this.ppFeasibilityUnitName = ppFeasibilityUnitName;
    }

    public String getPpEstimationUnitName() {
        return ppEstimationUnitName;
    }

    public void setPpEstimationUnitName(String ppEstimationUnitName) {
        this.ppEstimationUnitName = ppEstimationUnitName;
    }

    public String getPpFeasibilityUnitNameHistory() {
        return ppFeasibilityUnitNameHistory;
    }

    public void setPpFeasibilityUnitNameHistory(String ppFeasibilityUnitNameHistory) {
        this.ppFeasibilityUnitNameHistory = ppFeasibilityUnitNameHistory;
    }

    public String getPpEstimationUnitNameHistory() {
        return ppEstimationUnitNameHistory;
    }

    public void setPpEstimationUnitNameHistory(String ppEstimationUnitNameHistory) {
        this.ppEstimationUnitNameHistory = ppEstimationUnitNameHistory;
    }

    public String getPpInvestmentFileIds() {
        return ppInvestmentFileIds;
    }

    public void setPpInvestmentFileIds(String ppInvestmentFileIds) {
        this.ppInvestmentFileIds = ppInvestmentFileIds;
    }

    public String getPpFeasibilityAbbName() {
        return ppFeasibilityAbbName;
    }

    public void setPpFeasibilityAbbName(String ppFeasibilityAbbName) {
        this.ppFeasibilityAbbName = ppFeasibilityAbbName;
    }

    public String getPpEstimationAbbName() {
        return ppEstimationAbbName;
    }

    public void setPpEstimationAbbName(String ppEstimationAbbName) {
        this.ppEstimationAbbName = ppEstimationAbbName;
    }

    public String getPpDepartment() {
        return ppDepartment;
    }

    public void setPpDepartment(String ppDepartment) {
        this.ppDepartment = ppDepartment;
    }

    public String getPpDepName() {
        return ppDepName;
    }

    public void setPpDepName(String ppDepName) {
        this.ppDepName = ppDepName;
    }

    public List<String> getPpInvestments() {
        return ppInvestments;
    }

    public void setPpInvestments(List<String> ppInvestments) {
        this.ppInvestments = ppInvestments;
    }

    public List<String> getPpCreateTimes() {
        return ppCreateTimes;
    }

    public void setPpCreateTimes(List<String> ppCreateTimes) {
        this.ppCreateTimes = ppCreateTimes;
    }
}
