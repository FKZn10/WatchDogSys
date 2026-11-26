package com.jpmsoft.common.model.response.Construction;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 施工组织设计
 */

public class ConConstructionOrgDesignResponse extends ProjectCommonRes {

    private String codId;
    private String pId;
    private String conId;
    /**
     * 工程id
     */
    private String secId;
    /**
     * 工程id
     */
    private String secName;
        /**
     * 工程id
     */
    private String secInfoName;
    /**
     * 编制单位
     */
    private String compilationUnitId;
    /**
     * 项目经理
     */
    private String projectManager;
    private String codTitle;
    private String codContent;
    private String codRemark;
    private String codFile;
    private String codSchemeFile;
    private String codCreateUid;
    private String codCreateUname;
    private Date codCreateTime;
    private Integer codState;
    private Date codStateTime;

    private String conName;
    private String codCode;
    /**
     * 是否涉及费用 0 否 1 是
     */
    private Integer isInvolvedFees;
    /**
     * 是否按合同计取 0 否 1 是
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
    public String getConId() {
        return conId;
    }

    @Override
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

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getSecInfoName() {
        return secInfoName;
    }

    public void setSecInfoName(String secInfoName) {
        this.secInfoName = secInfoName;
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
}
