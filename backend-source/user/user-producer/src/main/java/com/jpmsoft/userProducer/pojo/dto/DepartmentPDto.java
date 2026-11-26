package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class DepartmentPDto extends BaseModeRequest implements Serializable {
    private static final long serialVersionUID = 1545577519698326008L;
    public String pdId;
    public String pId;
    public String pdName;
    public String pdCode;
    public String pdStatus;
    public String pdParentid;
    public String pdPath;
    public String pdType;
    public String pdMdmNo;
    public String pdRemark;
    public String pdSort;
    public String pdSolution;
    public String pdCreateuID;
    public String pdCreateuName;
    @NotBlank(message = BaseMsg.noOperationTypeSelected)
    public String type;
    public String newPdId;
    public String newPdSort;
    /**
     * 项目名称
     */
    public String pNam;

    public String getPdId() {
        return pdId;
    }

    public void setPdId(String pdId) {
        this.pdId = pdId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPdName() {
        return pdName;
    }

    public void setPdName(String pdName) {
        this.pdName = pdName;
    }

    public String getPdCode() {
        return pdCode;
    }

    public void setPdCode(String pdCode) {
        this.pdCode = pdCode;
    }

    public String getPdStatus() {
        return pdStatus;
    }

    public void setPdStatus(String pdStatus) {
        this.pdStatus = pdStatus;
    }

    public String getPdParentid() {
        return pdParentid;
    }

    public void setPdParentid(String pdParentid) {
        this.pdParentid = pdParentid;
    }

    public String getPdPath() {
        return pdPath;
    }

    public void setPdPath(String pdPath) {
        this.pdPath = pdPath;
    }

    public String getPdType() {
        return pdType;
    }

    public void setPdType(String pdType) {
        this.pdType = pdType;
    }

    public String getPdMdmNo() {
        return pdMdmNo;
    }

    public void setPdMdmNo(String pdMdmNo) {
        this.pdMdmNo = pdMdmNo;
    }

    public String getPdRemark() {
        return pdRemark;
    }

    public void setPdRemark(String pdRemark) {
        this.pdRemark = pdRemark;
    }

    public String getPdSort() {
        return pdSort;
    }

    public void setPdSort(String pdSort) {
        this.pdSort = pdSort;
    }

    public String getPdSolution() {
        return pdSolution;
    }

    public void setPdSolution(String pdSolution) {
        this.pdSolution = pdSolution;
    }

    public String getPdCreateuID() {
        return pdCreateuID;
    }

    public void setPdCreateuID(String pdCreateuID) {
        this.pdCreateuID = pdCreateuID;
    }

    public String getPdCreateuName() {
        return pdCreateuName;
    }

    public void setPdCreateuName(String pdCreateuName) {
        this.pdCreateuName = pdCreateuName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNewPdId() {
        return newPdId;
    }

    public void setNewPdId(String newPdId) {
        this.newPdId = newPdId;
    }

    public String getNewPdSort() {
        return newPdSort;
    }

    public void setNewPdSort(String newPdSort) {
        this.newPdSort = newPdSort;
    }

    public String getpNam() {
        return pNam;
    }

    public void setpNam(String pNam) {
        this.pNam = pNam;
    }
}
