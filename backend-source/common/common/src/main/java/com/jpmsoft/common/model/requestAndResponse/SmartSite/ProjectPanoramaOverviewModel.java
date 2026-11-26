package com.jpmsoft.common.model.requestAndResponse.SmartSite;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 项目全景及工程概况
 */

public class ProjectPanoramaOverviewModel extends ProjectCommonRes {
    private String ppoId;
    private String pId;
    private Date ppoData;
    private String ppoFileType;
    private String ppoFile;
    private Integer ppoState;
    private String ppoRemarks;
    private String ppoType;
    private String ppoCreateUid;
    private String ppoCreateUname;
    private Date ppoCreateTime;

    private String ppoFileIds;

    private String contractId;

    private String contractName;

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getPpoId() {
        return ppoId;
    }

    public void setPpoId(String ppoId) {
        this.ppoId = ppoId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Date getPpoData() {
        return ppoData;
    }

    public void setPpoData(Date ppoData) {
        this.ppoData = ppoData;
    }

    public String getPpoFileType() {
        return ppoFileType;
    }

    public void setPpoFileType(String ppoFileType) {
        this.ppoFileType = ppoFileType;
    }

    public String getPpoFile() {
        return ppoFile;
    }

    public void setPpoFile(String ppoFile) {
        this.ppoFile = ppoFile;
    }

    public Integer getPpoState() {
        return ppoState;
    }

    public void setPpoState(Integer ppoState) {
        this.ppoState = ppoState;
    }

    public String getPpoRemarks() {
        return ppoRemarks;
    }

    public void setPpoRemarks(String ppoRemarks) {
        this.ppoRemarks = ppoRemarks;
    }

    public String getPpoType() {
        return ppoType;
    }

    public void setPpoType(String ppoType) {
        this.ppoType = ppoType;
    }

    public String getPpoCreateUid() {
        return ppoCreateUid;
    }

    public void setPpoCreateUid(String ppoCreateUid) {
        this.ppoCreateUid = ppoCreateUid;
    }

    public String getPpoCreateUname() {
        return ppoCreateUname;
    }

    public void setPpoCreateUname(String ppoCreateUname) {
        this.ppoCreateUname = ppoCreateUname;
    }

    public Date getPpoCreateTime() {
        return ppoCreateTime;
    }

    public void setPpoCreateTime(Date ppoCreateTime) {
        this.ppoCreateTime = ppoCreateTime;
    }

    public String getPpoFileIds() {
        return ppoFileIds;
    }

    public void setPpoFileIds(String ppoFileIds) {
        this.ppoFileIds = ppoFileIds;
    }
}
