package com.jpmsoft.common.model.response.MeEnter;

import java.util.Date;

/**
 * 材料设备品种库
 */
public class MeTypeLibraryResponse {

    private String metId;
    private String metName;
    private String metCode;
    private Integer metSort;
    private String metManageType;
    private String metUnit;
    private String metParentId;
    private String metType;
    private String metTag;
    private Integer metState;
    private String metCreateUid;
    private String metCreateUname;
    private Date metCreateTime;

    private String metUnitName;
    //单位简称
    private String metUnitAbbName;

    // 源id
    private String originId;

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getMetId() {
        return metId;
    }

    public void setMetId(String metId) {
        this.metId = metId;
    }

    public String getMetName() {
        return metName;
    }

    public void setMetName(String metName) {
        this.metName = metName;
    }

    public String getMetCode() {
        return metCode;
    }

    public void setMetCode(String metCode) {
        this.metCode = metCode;
    }

    public Integer getMetSort() {
        return metSort;
    }

    public void setMetSort(Integer metSort) {
        this.metSort = metSort;
    }

    public String getMetManageType() {
        return metManageType;
    }

    public void setMetManageType(String metManageType) {
        this.metManageType = metManageType;
    }

    public String getMetUnit() {
        return metUnit;
    }

    public void setMetUnit(String metUnit) {
        this.metUnit = metUnit;
    }

    public String getMetParentId() {
        return metParentId;
    }

    public void setMetParentId(String metParentId) {
        this.metParentId = metParentId;
    }

    public String getMetType() {
        return metType;
    }

    public void setMetType(String metType) {
        this.metType = metType;
    }

    public String getMetTag() {
        return metTag;
    }

    public void setMetTag(String metTag) {
        this.metTag = metTag;
    }

    public Integer getMetState() {
        return metState;
    }

    public void setMetState(Integer metState) {
        this.metState = metState;
    }

    public String getMetCreateUid() {
        return metCreateUid;
    }

    public void setMetCreateUid(String metCreateUid) {
        this.metCreateUid = metCreateUid;
    }

    public String getMetCreateUname() {
        return metCreateUname;
    }

    public void setMetCreateUname(String metCreateUname) {
        this.metCreateUname = metCreateUname;
    }

    public Date getMetCreateTime() {
        return metCreateTime;
    }

    public void setMetCreateTime(Date metCreateTime) {
        this.metCreateTime = metCreateTime;
    }

    public String getMetUnitName() {
        return metUnitName;
    }

    public void setMetUnitName(String metUnitName) {
        this.metUnitName = metUnitName;
    }

    public String getMetUnitAbbName() {
        return metUnitAbbName;
    }

    public void setMetUnitAbbName(String metUnitAbbName) {
        this.metUnitAbbName = metUnitAbbName;
    }
}
