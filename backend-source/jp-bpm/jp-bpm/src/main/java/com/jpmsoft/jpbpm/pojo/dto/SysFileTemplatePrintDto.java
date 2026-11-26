package com.jpmsoft.jpbpm.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;
import java.util.List;

/**
 * 打印模板
 */
public class SysFileTemplatePrintDto extends BaseModeRequest {
    private String tpId;
    private String tId;
    private Integer tpOrder;
    private String tpContent;
    private String tpCreateUid;
    private String tpCreateUname;
    private Date tpCreateTime;
    private String tpApiUrl;
    private String tpName;
    private String tpRemark;
    private Integer tpState;
    private String tName;
    private String tpType;
    private String tCode;
    private String tType;
    private List<SysFileTemplateApiFieldDto> fields;

    private String entityId;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public Integer getTpOrder() {
        return tpOrder;
    }

    public void setTpOrder(Integer tpOrder) {
        this.tpOrder = tpOrder;
    }

    public String getTpContent() {
        return tpContent;
    }

    public void setTpContent(String tpContent) {
        this.tpContent = tpContent;
    }

    public String getTpCreateUid() {
        return tpCreateUid;
    }

    public void setTpCreateUid(String tpCreateUid) {
        this.tpCreateUid = tpCreateUid;
    }

    public String getTpCreateUname() {
        return tpCreateUname;
    }

    public void setTpCreateUname(String tpCreateUname) {
        this.tpCreateUname = tpCreateUname;
    }

    public Date getTpCreateTime() {
        return tpCreateTime;
    }

    public void setTpCreateTime(Date tpCreateTime) {
        this.tpCreateTime = tpCreateTime;
    }

    public String getTpApiUrl() {
        return tpApiUrl;
    }

    public void setTpApiUrl(String tpApiUrl) {
        this.tpApiUrl = tpApiUrl;
    }

    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName;
    }

    public String getTpRemark() {
        return tpRemark;
    }

    public void setTpRemark(String tpRemark) {
        this.tpRemark = tpRemark;
    }

    public Integer getTpState() {
        return tpState;
    }

    public void setTpState(Integer tpState) {
        this.tpState = tpState;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String gettType() {
        return tType;
    }

    public void settType(String tType) {
        this.tType = tType;
    }

    public List<SysFileTemplateApiFieldDto> getFields() {
        return fields;
    }

    public void setFields(List<SysFileTemplateApiFieldDto> fields) {
        this.fields = fields;
    }

    public String getTpType() {
        return tpType;
    }

    public void setTpType(String tpType) {
        this.tpType = tpType;
    }
}
