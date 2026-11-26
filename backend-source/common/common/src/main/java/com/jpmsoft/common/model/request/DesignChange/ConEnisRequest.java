package com.jpmsoft.common.model.request.DesignChange;


import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 工程洽商指令单
 */
public class ConEnisRequest extends BaseFileRequest {

    private String enisId;
    private String enisCode;
    private String enisProjectName;
    private String enisProposedId;
    private String enisMajor;
    private String enisDrawingNo;
    private Date enisDate;
    private String enisContentSummary;
    private String enisContent;
    private String enisCreateUid;
    private String enisCreateUname;
    private Date enisCreateTime;
    private Integer enisState;
    private Date enisStateTime;
    private String enisType;
    private String enisLocation;
    private String enisDep;
    private Date enisFinishDate;
    private String enisName;
    private String enisApprovalMatter;
    private String enisCategory;
    private BigDecimal enisContractorAmt;
    private String enisMainCode;
    private String enisSubCode;
    private BigDecimal enisHbMoney;
    private String enisHbRemark;
    private String enisHbCheck;
    /**
     * 监理预算
     */
    private BigDecimal enisSupervisionAmt;


    private String pName;
    private String conName;
    private String enisDepName;
    private String enisProposedName;
    private List<String> enisCreateTimes;
    private List<String> enisDates;
    private List<String> enisFinishDates;
    /**
     * true过滤
     * 北林需求
     * Con_En_Budget 表选 Con_Enis 除作废的， 仅仅只能关联一次
     */
    private boolean filterEnBudget;

    // 是否过滤掉已经在变更费用申请中选择的项目
    private boolean filterEnChangeApply;

    /**
     * 是否筛选掉在Con_Dco表里的数据（未发起和已结束算不在）
     */
    private String filterConDco;


    /**
     * 定制鉴权
     * 外部用户能看到创建人同单位发起的所有数据
     * 01 鉴权 02 不鉴权
     * @return
     */
    private String filterEnisAuth;

    private String currentCompanyId;

    public String getCurrentCompanyId() {
        return currentCompanyId;
    }

    public void setCurrentCompanyId(String currentCompanyId) {
        this.currentCompanyId = currentCompanyId;
    }

    public String getFilterEnisAuth() {
        return filterEnisAuth;
    }

    public void setFilterEnisAuth(String filterEnisAuth) {
        this.filterEnisAuth = filterEnisAuth;
    }

    public String getFilterConDco() {
        return filterConDco;
    }

    public void setFilterConDco(String filterConDco) {
        this.filterConDco = filterConDco;
    }

    public Boolean getFilterEnChangeApply() {
        return filterEnChangeApply;
    }

    public void setFilterEnChangeApply(Boolean filterEnChangeApply) {
        this.filterEnChangeApply = filterEnChangeApply;
    }

    public String getEnisId() {
        return enisId;
    }

    public void setEnisId(String enisId) {
        this.enisId = enisId;
    }

    public String getEnisCode() {
        return enisCode;
    }

    public void setEnisCode(String enisCode) {
        this.enisCode = enisCode;
    }

    public String getEnisProjectName() {
        return enisProjectName;
    }

    public void setEnisProjectName(String enisProjectName) {
        this.enisProjectName = enisProjectName;
    }

    public String getEnisProposedId() {
        return enisProposedId;
    }

    public void setEnisProposedId(String enisProposedId) {
        this.enisProposedId = enisProposedId;
    }

    public String getEnisMajor() {
        return enisMajor;
    }

    public void setEnisMajor(String enisMajor) {
        this.enisMajor = enisMajor;
    }

    public String getEnisDrawingNo() {
        return enisDrawingNo;
    }

    public void setEnisDrawingNo(String enisDrawingNo) {
        this.enisDrawingNo = enisDrawingNo;
    }

    public Date getEnisDate() {
        return enisDate;
    }

    public void setEnisDate(Date enisDate) {
        this.enisDate = enisDate;
    }

    public String getEnisContentSummary() {
        return enisContentSummary;
    }

    public void setEnisContentSummary(String enisContentSummary) {
        this.enisContentSummary = enisContentSummary;
    }

    public String getEnisContent() {
        return enisContent;
    }

    public void setEnisContent(String enisContent) {
        this.enisContent = enisContent;
    }

    public String getEnisCreateUid() {
        return enisCreateUid;
    }

    public void setEnisCreateUid(String enisCreateUid) {
        this.enisCreateUid = enisCreateUid;
    }

    public String getEnisCreateUname() {
        return enisCreateUname;
    }

    public void setEnisCreateUname(String enisCreateUname) {
        this.enisCreateUname = enisCreateUname;
    }

    public Date getEnisCreateTime() {
        return enisCreateTime;
    }

    public void setEnisCreateTime(Date enisCreateTime) {
        this.enisCreateTime = enisCreateTime;
    }

    public Integer getEnisState() {
        return enisState;
    }

    public void setEnisState(Integer enisState) {
        this.enisState = enisState;
    }

    public Date getEnisStateTime() {
        return enisStateTime;
    }

    public void setEnisStateTime(Date enisStateTime) {
        this.enisStateTime = enisStateTime;
    }

    public String getEnisType() {
        return enisType;
    }

    public void setEnisType(String enisType) {
        this.enisType = enisType;
    }

    public String getEnisLocation() {
        return enisLocation;
    }

    public void setEnisLocation(String enisLocation) {
        this.enisLocation = enisLocation;
    }

    public String getEnisDep() {
        return enisDep;
    }

    public void setEnisDep(String enisDep) {
        this.enisDep = enisDep;
    }

    public Date getEnisFinishDate() {
        return enisFinishDate;
    }

    public void setEnisFinishDate(Date enisFinishDate) {
        this.enisFinishDate = enisFinishDate;
    }

    public String getEnisName() {
        return enisName;
    }

    public void setEnisName(String enisName) {
        this.enisName = enisName;
    }

    public String getEnisApprovalMatter() {
        return enisApprovalMatter;
    }

    public void setEnisApprovalMatter(String enisApprovalMatter) {
        this.enisApprovalMatter = enisApprovalMatter;
    }

    public String getEnisCategory() {
        return enisCategory;
    }

    public void setEnisCategory(String enisCategory) {
        this.enisCategory = enisCategory;
    }

    public BigDecimal getEnisContractorAmt() {
        return enisContractorAmt;
    }

    public void setEnisContractorAmt(BigDecimal enisContractorAmt) {
        this.enisContractorAmt = enisContractorAmt;
    }

    public String getEnisMainCode() {
        return enisMainCode;
    }

    public void setEnisMainCode(String enisMainCode) {
        this.enisMainCode = enisMainCode;
    }

    public String getEnisSubCode() {
        return enisSubCode;
    }

    public void setEnisSubCode(String enisSubCode) {
        this.enisSubCode = enisSubCode;
    }

    public BigDecimal getEnisHbMoney() {
        return enisHbMoney;
    }

    public void setEnisHbMoney(BigDecimal enisHbMoney) {
        this.enisHbMoney = enisHbMoney;
    }

    public String getEnisHbRemark() {
        return enisHbRemark;
    }

    public void setEnisHbRemark(String enisHbRemark) {
        this.enisHbRemark = enisHbRemark;
    }

    public String getEnisHbCheck() {
        return enisHbCheck;
    }

    public void setEnisHbCheck(String enisHbCheck) {
        this.enisHbCheck = enisHbCheck;
    }

    public BigDecimal getEnisSupervisionAmt() {
        return enisSupervisionAmt;
    }

    public void setEnisSupervisionAmt(BigDecimal enisSupervisionAmt) {
        this.enisSupervisionAmt = enisSupervisionAmt;
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

    public String getEnisDepName() {
        return enisDepName;
    }

    public void setEnisDepName(String enisDepName) {
        this.enisDepName = enisDepName;
    }

    public String getEnisProposedName() {
        return enisProposedName;
    }

    public void setEnisProposedName(String enisProposedName) {
        this.enisProposedName = enisProposedName;
    }

    public List<String> getEnisCreateTimes() {
        return enisCreateTimes;
    }

    public void setEnisCreateTimes(List<String> enisCreateTimes) {
        this.enisCreateTimes = enisCreateTimes;
    }

    public List<String> getEnisDates() {
        return enisDates;
    }

    public void setEnisDates(List<String> enisDates) {
        this.enisDates = enisDates;
    }

    public List<String> getEnisFinishDates() {
        return enisFinishDates;
    }

    public void setEnisFinishDates(List<String> enisFinishDates) {
        this.enisFinishDates = enisFinishDates;
    }

    public boolean isFilterEnBudget() {
        return filterEnBudget;
    }

    public void setFilterEnBudget(boolean filterEnBudget) {
        this.filterEnBudget = filterEnBudget;
    }
}
