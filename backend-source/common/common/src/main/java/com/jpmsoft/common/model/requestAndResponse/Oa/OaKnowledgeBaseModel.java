package com.jpmsoft.common.model.requestAndResponse.Oa;


import java.util.Date;
import java.util.List;

/**
 * 知识库目录
 */
public class OaKnowledgeBaseModel {

    private String kbId;
    private String kbName;
    private String kbParentId;
    private String kbRemark;
    private Date kbCreateTime;
    private String kbCreateUid;
    private String kbCreateUname;
    private Integer kbState;
    private String kbCode;
    private String kbType;
    private String kbPermission;
    private Integer kbSort;
    //性质
    private String kbNature;
    //用途
    private String kbPurpose;
    //链接
    private String kbLink;
    private List<OaKnowledgeBaseModel>  childNodes;
    private List<String> powerCodes;
    private String kbFunctionCode;
    public String getKbId() {
        return kbId;
    }

    public void setKbId(String kbId) {
        this.kbId = kbId;
    }

    public String getKbName() {
        return kbName;
    }

    public void setKbName(String kbName) {
        this.kbName = kbName;
    }

    public String getKbParentId() {
        return kbParentId;
    }

    public void setKbParentId(String kbParentId) {
        this.kbParentId = kbParentId;
    }

    public String getKbRemark() {
        return kbRemark;
    }

    public void setKbRemark(String kbRemark) {
        this.kbRemark = kbRemark;
    }

    public Date getKbCreateTime() {
        return kbCreateTime;
    }

    public void setKbCreateTime(Date kbCreateTime) {
        this.kbCreateTime = kbCreateTime;
    }

    public String getKbCreateUid() {
        return kbCreateUid;
    }

    public void setKbCreateUid(String kbCreateUid) {
        this.kbCreateUid = kbCreateUid;
    }

    public String getKbCreateUname() {
        return kbCreateUname;
    }

    public void setKbCreateUname(String kbCreateUname) {
        this.kbCreateUname = kbCreateUname;
    }

    public Integer getKbState() {
        return kbState;
    }

    public void setKbState(Integer kbState) {
        this.kbState = kbState;
    }

    public String getKbCode() {
        return kbCode;
    }

    public void setKbCode(String kbCode) {
        this.kbCode = kbCode;
    }

    public String getKbType() {
        return kbType;
    }

    public void setKbType(String kbType) {
        this.kbType = kbType;
    }

    public String getKbPermission() {
        return kbPermission;
    }

    public void setKbPermission(String kbPermission) {
        this.kbPermission = kbPermission;
    }

    public Integer getKbSort() {
        return kbSort;
    }

    public void setKbSort(Integer kbSort) {
        this.kbSort = kbSort;
    }

    public String getKbNature() {
        return kbNature;
    }

    public void setKbNature(String kbNature) {
        this.kbNature = kbNature;
    }

    public String getKbPurpose() {
        return kbPurpose;
    }

    public void setKbPurpose(String kbPurpose) {
        this.kbPurpose = kbPurpose;
    }

    public String getKbLink() {
        return kbLink;
    }

    public void setKbLink(String kbLink) {
        this.kbLink = kbLink;
    }

    public List<OaKnowledgeBaseModel> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<OaKnowledgeBaseModel> childNodes) {
        this.childNodes = childNodes;
    }

    public List<String> getPowerCodes() {
        return powerCodes;
    }

    public void setPowerCodes(List<String> powerCodes) {
        this.powerCodes = powerCodes;
    }

    public String getKbFunctionCode() {
        return kbFunctionCode;
    }

    public void setKbFunctionCode(String kbFunctionCode) {
        this.kbFunctionCode = kbFunctionCode;
    }
}
