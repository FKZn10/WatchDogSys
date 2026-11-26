package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;

/**
 * 当前编码
 */
public class SysSequenceCurrentDto extends BaseModeRequest  implements Serializable {

    private static final long serialVersionUID = 8885837168908321923L;
    private String ccId;
    private String sId;
    private String pId;
    private String conId;
    private Integer ccNo;
    private String ccCode;
    private String ccReset;
    private String secId;
    private String secName;
    private String conName;
    private String pName;

    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
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

    public Integer getCcNo() {
        return ccNo;
    }

    public void setCcNo(Integer ccNo) {
        this.ccNo = ccNo;
    }

    public String getCcCode() {
        return ccCode;
    }

    public void setCcCode(String ccCode) {
        this.ccCode = ccCode;
    }

    public String getCcReset() {
        return ccReset;
    }

    public void setCcReset(String ccReset) {
        this.ccReset = ccReset;
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

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}
