package com.jpmsoft.projectProducer.pojo.entity.construction;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.Objects;

/**
 * 施工组织设计
 */


@TableName("Con_Construction_Org_Design")
public class ConConstructionOrgDesign {

    @TableId(type = IdType.INPUT)
    private String codId;
    /***
     * 项目id
     */
    private String pId;
    /***
     * 合同id
     */
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
     * 编制单位
     */
    private String compilationUnitId;
    /**
     * 项目经理
     */
    private String projectManager;
    //标题
    private String codTitle;
    //内容
    private String codContent;
    //备注
    private String codRemark;
    //施工组织设计文件上传
    private String codFile;
    //施工方案文件上传
    private String codSchemeFile;
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
    private String codCreateUid;
    private String codCreateUname;
    private Date codCreateTime;
    private Integer codState;
    private Date codStateTime;
    private String teId;

    public String getCodId() {
        return codId;
    }

    public void setCodId(String codId) {
        this.codId = codId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getSecId() {
        return secId;
    }

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

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
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
        ConConstructionOrgDesign that = (ConConstructionOrgDesign) o;
        return Objects.equals(getCodId(),that.getCodId()) && Objects.equals(getpId(),that.getpId()) && Objects.equals(getConId(),that.getConId()) && Objects.equals(getSecId(),that.getSecId()) && Objects.equals(getCompilationUnitId(),that.getCompilationUnitId()) && Objects.equals(getProjectManager(),that.getProjectManager()) && Objects.equals(getCodTitle(),that.getCodTitle()) && Objects.equals(getCodContent(),that.getCodContent()) && Objects.equals(getCodRemark(),that.getCodRemark()) && Objects.equals(getCodFile(),that.getCodFile()) && Objects.equals(getCodSchemeFile(),that.getCodSchemeFile()) && Objects.equals(getCodCreateUid(),that.getCodCreateUid()) && Objects.equals(getCodCreateUname(),that.getCodCreateUname()) && Objects.equals(getCodCreateTime(),that.getCodCreateTime()) && Objects.equals(getCodState(),that.getCodState()) && Objects.equals(getCodStateTime(),that.getCodStateTime()) && Objects.equals(getTeId(),that.getTeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodId(),getpId(),getConId(),getSecId(),getCompilationUnitId(),getProjectManager(),getCodTitle(),getCodContent(),getCodRemark(),getCodFile(),getCodSchemeFile(),getCodCreateUid(),getCodCreateUname(),getCodCreateTime(),getCodState(),getCodStateTime(),getTeId());
    }
}
