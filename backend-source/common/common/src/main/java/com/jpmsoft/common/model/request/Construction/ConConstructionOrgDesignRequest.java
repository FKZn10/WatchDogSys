package com.jpmsoft.common.model.request.Construction;

import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.Objects;

/**
 * 施工组织设计
 */

public class ConConstructionOrgDesignRequest extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String codId;
    private String pId;
    /**
     * 工程id
     */
    private String secId;
    /**
     * 工程id
     */
    private String secName;
    /**
     * 编制单位
     */
    private String compilationUnitId;
    /**
     * 项目经理
     */
    private String projectManager;
    private String conId;
    private String codTitle;
    private String codContent;
    private String codCode;
    private String codRemark;
    @FileFields(index = 1)
    private String codFile;
    @FileFields(index = 2)
    private String codSchemeFile;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String codCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String codCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date codCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer codState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date codStateTime;

    @FileFields(index = 1, type = "codFileIds")
    private String codFileIds;
    @FileFields(index = 2, type = "codSchemeFileIds")
    private String codSchemeFileIds;
    private String pName;
    private String conName;
    /**
     * 是否涉及费用
     */
    private Integer isInvolvedFees ;
    /**
     * 是否按合同计取
     */
    private Integer isCalculatedByContract;
    private String calculatedWithoutContractContext;
    public String getCodId() {
        return codId;
    }

    public void setCodId(String codId) {
        this.codId = codId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public String getSecId() {
        return secId;
    }

    @Override
    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getCompilationUnitId() {
        return compilationUnitId;
    }

    public void setCompilationUnitId(String compilationUnitId) {
        this.compilationUnitId = compilationUnitId;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    @Override
    public String getConId() {
        return conId;
    }

    @Override
    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getCodTitle() {
        return codTitle;
    }

    public void setCodTitle(String codTitle) {
        this.codTitle = codTitle;
    }

    public String getCodContent() {
        return codContent;
    }

    public void setCodContent(String codContent) {
        this.codContent = codContent;
    }

    public String getCodRemark() {
        return codRemark;
    }

    public void setCodRemark(String codRemark) {
        this.codRemark = codRemark;
    }

    public String getCodFile() {
        return codFile;
    }

    public void setCodFile(String codFile) {
        this.codFile = codFile;
    }

    public String getCodSchemeFile() {
        return codSchemeFile;
    }

    public void setCodSchemeFile(String codSchemeFile) {
        this.codSchemeFile = codSchemeFile;
    }

    public String getCodCreateUid() {
        return codCreateUid;
    }

    public void setCodCreateUid(String codCreateUid) {
        this.codCreateUid = codCreateUid;
    }

    public String getCodCreateUname() {
        return codCreateUname;
    }

    public void setCodCreateUname(String codCreateUname) {
        this.codCreateUname = codCreateUname;
    }

    public Date getCodCreateTime() {
        return codCreateTime;
    }

    public void setCodCreateTime(Date codCreateTime) {
        this.codCreateTime = codCreateTime;
    }

    public Integer getCodState() {
        return codState;
    }

    public void setCodState(Integer codState) {
        this.codState = codState;
    }

    public Date getCodStateTime() {
        return codStateTime;
    }

    public void setCodStateTime(Date codStateTime) {
        this.codStateTime = codStateTime;
    }

    public String getCodFileIds() {
        return codFileIds;
    }

    public void setCodFileIds(String codFileIds) {
        this.codFileIds = codFileIds;
    }

    public String getCodSchemeFileIds() {
        return codSchemeFileIds;
    }

    public void setCodSchemeFileIds(String codSchemeFileIds) {
        this.codSchemeFileIds = codSchemeFileIds;
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

    public String getCodCode() {
        return codCode;
    }

    public void setCodCode(String codCode) {
        this.codCode = codCode;
    }

    public Integer getIsInvolvedFees() {
        return isInvolvedFees;
    }

    public void setIsInvolvedFees(Integer isInvolvedFees) {
        this.isInvolvedFees = isInvolvedFees;
    }

    public Integer getIsCalculatedByContract() {
        return isCalculatedByContract;
    }

    public void setIsCalculatedByContract(Integer isCalculatedByContract) {
        this.isCalculatedByContract = isCalculatedByContract;
    }

    public String getCalculatedWithoutContractContext() {
        return calculatedWithoutContractContext;
    }

    public void setCalculatedWithoutContractContext(String calculatedWithoutContractContext) {
        this.calculatedWithoutContractContext = calculatedWithoutContractContext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConConstructionOrgDesignRequest that = (ConConstructionOrgDesignRequest) o;
        return Objects.equals(getCodId(),that.getCodId()) && Objects.equals(getpId(),that.getpId()) && Objects.equals(getSecId(),that.getSecId()) && Objects.equals(getSecName(),that.getSecName()) && Objects.equals(getCompilationUnitId(),that.getCompilationUnitId()) && Objects.equals(getProjectManager(),that.getProjectManager()) && Objects.equals(getConId(),that.getConId()) && Objects.equals(getCodTitle(),that.getCodTitle()) && Objects.equals(getCodContent(),that.getCodContent()) && Objects.equals(getCodRemark(),that.getCodRemark()) && Objects.equals(getCodFile(),that.getCodFile()) && Objects.equals(getCodSchemeFile(),that.getCodSchemeFile()) && Objects.equals(getCodCreateUid(),that.getCodCreateUid()) && Objects.equals(getCodCreateUname(),that.getCodCreateUname()) && Objects.equals(getCodCreateTime(),that.getCodCreateTime()) && Objects.equals(getCodState(),that.getCodState()) && Objects.equals(getCodStateTime(),that.getCodStateTime()) && Objects.equals(getCodFileIds(),that.getCodFileIds()) && Objects.equals(getCodSchemeFileIds(),that.getCodSchemeFileIds()) && Objects.equals(getpName(),that.getpName()) && Objects.equals(getConName(),that.getConName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodId(),getpId(),getSecId(),getSecName(),getCompilationUnitId(),getProjectManager(),getConId(),getCodTitle(),getCodContent(),getCodRemark(),getCodFile(),getCodSchemeFile(),getCodCreateUid(),getCodCreateUname(),getCodCreateTime(),getCodState(),getCodStateTime(),getCodFileIds(),getCodSchemeFileIds(),getpName(),getConName());
    }
}
