package com.jpmsoft.common.model.requestAndResponse.Project;

import java.util.Date;

/**
 * 指挥部架构职务明细
 */

public class PreCommandPostDtlModel {

    private String comdId;
    private String comId;
    private String comdPostCode;
    private String comdCid;
    private String comdUid;
    private String comdUname;
    private String comdTel;
    private Date comdCreateTime;
    private Integer comdOrder;
    private String comdCname;

    private String cName;
    private String cNameHistory;
    private String cAbbName;

    public String getComdId() {
        return comdId;
    }

    public void setComdId(String comdId) {
        this.comdId = comdId;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getComdPostCode() {
        return comdPostCode;
    }

    public void setComdPostCode(String comdPostCode) {
        this.comdPostCode = comdPostCode;
    }

    public String getComdCid() {
        return comdCid;
    }

    public void setComdCid(String comdCid) {
        this.comdCid = comdCid;
    }

    public String getComdUid() {
        return comdUid;
    }

    public void setComdUid(String comdUid) {
        this.comdUid = comdUid;
    }

    public String getComdUname() {
        return comdUname;
    }

    public void setComdUname(String comdUname) {
        this.comdUname = comdUname;
    }

    public String getComdTel() {
        return comdTel;
    }

    public void setComdTel(String comdTel) {
        this.comdTel = comdTel;
    }

    public Date getComdCreateTime() {
        return comdCreateTime;
    }

    public void setComdCreateTime(Date comdCreateTime) {
        this.comdCreateTime = comdCreateTime;
    }

    public Integer getComdOrder() {
        return comdOrder;
    }

    public void setComdOrder(Integer comdOrder) {
        this.comdOrder = comdOrder;
    }

    public String getComdCname() {
        return comdCname;
    }

    public void setComdCname(String comdCname) {
        this.comdCname = comdCname;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcNameHistory() {
        return cNameHistory;
    }

    public void setcNameHistory(String cNameHistory) {
        this.cNameHistory = cNameHistory;
    }

    public String getcAbbName() {
        return cAbbName;
    }

    public void setcAbbName(String cAbbName) {
        this.cAbbName = cAbbName;
    }
}
