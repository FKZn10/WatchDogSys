package com.jpmsoft.common.model.request.MeEnter;


import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

/**
 * 进场验货
 */
public class MeEnterInspectionRequest extends BaseFileRequest {

    private String eiId;
    private String eaId;
    private String pId;
    private String conId;
    private String eiSupervisorId;
    private String eiDesignId;
    private String eiContractorId;
    private String eiSupplierId;
    private String eiAcceptPic;
    private Date eiAcceptTime;
    private String eiAcceptorId;
    private String eiAcceptorName;
    private String eiCreateUid;
    private String eiCreateUname;
    private Date eiCreateTime;
    private Integer eiState;
    private Date eiStateTime;
    private String eiCode;

    /******************************/
    private String conName;
    private String conPartyBId;
    private String pName;
    private String metName;
    private String eaCode;
    private String eiAcceptPicIds;

    private List<MeEnterApplyDtlRequest> applyDtls;

    public String getEiId() {
        return eiId;
    }

    public void setEiId(String eiId) {
        this.eiId = eiId;
    }

    public String getEaId() {
        return eaId;
    }

    public void setEaId(String eaId) {
        this.eaId = eaId;
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

    public String getEiSupervisorId() {
        return eiSupervisorId;
    }

    public void setEiSupervisorId(String eiSupervisorId) {
        this.eiSupervisorId = eiSupervisorId;
    }

    public String getEiDesignId() {
        return eiDesignId;
    }

    public void setEiDesignId(String eiDesignId) {
        this.eiDesignId = eiDesignId;
    }

    public String getEiContractorId() {
        return eiContractorId;
    }

    public void setEiContractorId(String eiContractorId) {
        this.eiContractorId = eiContractorId;
    }

    public String getEiSupplierId() {
        return eiSupplierId;
    }

    public void setEiSupplierId(String eiSupplierId) {
        this.eiSupplierId = eiSupplierId;
    }

    public String getEiAcceptPic() {
        return eiAcceptPic;
    }

    public void setEiAcceptPic(String eiAcceptPic) {
        this.eiAcceptPic = eiAcceptPic;
    }

    public Date getEiAcceptTime() {
        return eiAcceptTime;
    }

    public void setEiAcceptTime(Date eiAcceptTime) {
        this.eiAcceptTime = eiAcceptTime;
    }

    public String getEiAcceptorId() {
        return eiAcceptorId;
    }

    public void setEiAcceptorId(String eiAcceptorId) {
        this.eiAcceptorId = eiAcceptorId;
    }

    public String getEiAcceptorName() {
        return eiAcceptorName;
    }

    public void setEiAcceptorName(String eiAcceptorName) {
        this.eiAcceptorName = eiAcceptorName;
    }

    public String getEiCreateUid() {
        return eiCreateUid;
    }

    public void setEiCreateUid(String eiCreateUid) {
        this.eiCreateUid = eiCreateUid;
    }

    public String getEiCreateUname() {
        return eiCreateUname;
    }

    public void setEiCreateUname(String eiCreateUname) {
        this.eiCreateUname = eiCreateUname;
    }

    public Date getEiCreateTime() {
        return eiCreateTime;
    }

    public void setEiCreateTime(Date eiCreateTime) {
        this.eiCreateTime = eiCreateTime;
    }

    public Integer getEiState() {
        return eiState;
    }

    public void setEiState(Integer eiState) {
        this.eiState = eiState;
    }

    public Date getEiStateTime() {
        return eiStateTime;
    }

    public void setEiStateTime(Date eiStateTime) {
        this.eiStateTime = eiStateTime;
    }

    public String getEiCode() {
        return eiCode;
    }

    public void setEiCode(String eiCode) {
        this.eiCode = eiCode;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConPartyBId() {
        return conPartyBId;
    }

    public void setConPartyBId(String conPartyBId) {
        this.conPartyBId = conPartyBId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public List<MeEnterApplyDtlRequest> getApplyDtls() {
        return applyDtls;
    }

    public void setApplyDtls(List<MeEnterApplyDtlRequest> applyDtls) {
        this.applyDtls = applyDtls;
    }

    public String getMetName() {
        return metName;
    }

    public void setMetName(String metName) {
        this.metName = metName;
    }

    public String getEaCode() {
        return eaCode;
    }

    public void setEaCode(String eaCode) {
        this.eaCode = eaCode;
    }

    public String getEiAcceptPicIds() {
        return eiAcceptPicIds;
    }

    public void setEiAcceptPicIds(String eiAcceptPicIds) {
        this.eiAcceptPicIds = eiAcceptPicIds;
    }
}
