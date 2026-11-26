package com.jpmsoft.common.model.request.PeInfo;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;
import java.util.List;

/**
 * 履约评价表
 */

public class PeFormRequest extends BaseModeRequest {

    private String fId;
    private String fName;
    private String fType;
    private Integer fVersion;
    private String fCreateUid;
    private String fCreateUname;
    private Date fCreateTime;
    private String fState;
    private String fRemark;
    private List<PeFormDetailRequest> peFormDetails;

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public Integer getfVersion() {
        return fVersion;
    }

    public void setfVersion(Integer fVersion) {
        this.fVersion = fVersion;
    }

    public String getfCreateUid() {
        return fCreateUid;
    }

    public void setfCreateUid(String fCreateUid) {
        this.fCreateUid = fCreateUid;
    }

    public String getfCreateUname() {
        return fCreateUname;
    }

    public void setfCreateUname(String fCreateUname) {
        this.fCreateUname = fCreateUname;
    }

    public Date getfCreateTime() {
        return fCreateTime;
    }

    public void setfCreateTime(Date fCreateTime) {
        this.fCreateTime = fCreateTime;
    }

    public String getfState() {
        return fState;
    }

    public void setfState(String fState) {
        this.fState = fState;
    }

    public List<PeFormDetailRequest> getPeFormDetails() {
        return peFormDetails;
    }

    public void setPeFormDetails(List<PeFormDetailRequest> peFormDetails) {
        this.peFormDetails = peFormDetails;
    }

    public String getfRemark() {
        return fRemark;
    }

    public void setfRemark(String fRemark) {
        this.fRemark = fRemark;
    }
}
