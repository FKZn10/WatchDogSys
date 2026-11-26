package com.jpmsoft.common.model.requestAndResponse.SecInfo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 专家选取记录（专家库、建管专家库等）
 */
public class SecSelectionRecordMultipleModel  extends BaseFileRequest {

    private String id;
    //专家库类型：1、专家库 2、建管专家库
    private Integer expertDbType;
    //对应库的选取记录表id
    private String srId;
    //项目id
    private String pId;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pCode")
    private String pCode;
    //标段id
    private String secId;
    //标段名称
    private String secName;
    //招标人Id
    private String secTenderee;
    //代理单位ID
    private String secAgency;
    //招标人(单位)
    private String secTendereeName;
    //代理单位
    private String secAgencyName;
    //招标方式
    private String secBiddingType;
    //组织形式
    private String secOrganizeForm;
    private String secType;

    private Integer state;
    private Date stateTime;
    private Date createTime;
    private String createUid;
    private String createUname;
    private String teId;
    private String currentUid;
    /**
     * 备注
     */
    private String srmRemark;


    //专家库
    private SecSelectionRecordModel secSelectionRecordModel;

    //建管专家库
    private SecJGSelectionRecordModel secJGSelectionRecordModel;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getExpertDbType() {
        return expertDbType;
    }

    public void setExpertDbType(Integer expertDbType) {
        this.expertDbType = expertDbType;
    }

    public String getSrId() {
        return srId;
    }

    public void setSrId(String srId) {
        this.srId = srId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
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

    public String getSecTenderee() {
        return secTenderee;
    }

    public void setSecTenderee(String secTenderee) {
        this.secTenderee = secTenderee;
    }

    public String getSecAgency() {
        return secAgency;
    }

    public void setSecAgency(String secAgency) {
        this.secAgency = secAgency;
    }

    public String getSecTendereeName() {
        return secTendereeName;
    }

    public void setSecTendereeName(String secTendereeName) {
        this.secTendereeName = secTendereeName;
    }

    public String getSecAgencyName() {
        return secAgencyName;
    }

    public void setSecAgencyName(String secAgencyName) {
        this.secAgencyName = secAgencyName;
    }

    public String getSecBiddingType() {
        return secBiddingType;
    }

    public void setSecBiddingType(String secBiddingType) {
        this.secBiddingType = secBiddingType;
    }

    public String getSecOrganizeForm() {
        return secOrganizeForm;
    }

    public void setSecOrganizeForm(String secOrganizeForm) {
        this.secOrganizeForm = secOrganizeForm;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public String getCreateUname() {
        return createUname;
    }

    public void setCreateUname(String createUname) {
        this.createUname = createUname;
    }

    @Override
    public String getTeId() {
        return teId;
    }

    @Override
    public void setTeId(String teId) {
        this.teId = teId;
    }

    public String getCurrentUid() {
        return currentUid;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }

    public String getSrmRemark() {
        return srmRemark;
    }

    public void setSrmRemark(String srmRemark) {
        this.srmRemark = srmRemark;
    }

    public SecSelectionRecordModel getSecSelectionRecordModel() {
        return secSelectionRecordModel;
    }

    public void setSecSelectionRecordModel(SecSelectionRecordModel secSelectionRecordModel) {
        this.secSelectionRecordModel = secSelectionRecordModel;
    }

    public SecJGSelectionRecordModel getSecJGSelectionRecordModel() {
        return secJGSelectionRecordModel;
    }

    public void setSecJGSelectionRecordModel(SecJGSelectionRecordModel secJGSelectionRecordModel) {
        this.secJGSelectionRecordModel = secJGSelectionRecordModel;
    }
}
