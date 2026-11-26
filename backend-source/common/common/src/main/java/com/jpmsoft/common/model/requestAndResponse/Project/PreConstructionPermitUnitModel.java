package com.jpmsoft.common.model.requestAndResponse.Project;

import java.util.Date;

/**
 * 参建单位明细
 */
public class PreConstructionPermitUnitModel {

    private String cpuId;
    private String cpId;
    private String cpuCid;
    private String cpuPmUid;
    private String cpuPmUname;
    private String cpuTel;
    private Date cpuCreateTime;
    private Integer cpuOrder;

    private String cpuCName;
    private String cpuCNameHistory;
    private String cpuCType;
    private String cpuCAbbName;


    public String getCpuId() {
        return cpuId;
    }

    public void setCpuId(String cpuId) {
        this.cpuId = cpuId;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getCpuCid() {
        return cpuCid;
    }

    public void setCpuCid(String cpuCid) {
        this.cpuCid = cpuCid;
    }

    public String getCpuPmUid() {
        return cpuPmUid;
    }

    public void setCpuPmUid(String cpuPmUid) {
        this.cpuPmUid = cpuPmUid;
    }

    public String getCpuPmUname() {
        return cpuPmUname;
    }

    public void setCpuPmUname(String cpuPmUname) {
        this.cpuPmUname = cpuPmUname;
    }

    public String getCpuTel() {
        return cpuTel;
    }

    public void setCpuTel(String cpuTel) {
        this.cpuTel = cpuTel;
    }

    public Date getCpuCreateTime() {
        return cpuCreateTime;
    }

    public void setCpuCreateTime(Date cpuCreateTime) {
        this.cpuCreateTime = cpuCreateTime;
    }

    public Integer getCpuOrder() {
        return cpuOrder;
    }

    public void setCpuOrder(Integer cpuOrder) {
        this.cpuOrder = cpuOrder;
    }

    public String getCpuCName() {
        return cpuCName;
    }

    public void setCpuCName(String cpuCName) {
        this.cpuCName = cpuCName;
    }

    public String getCpuCNameHistory() {
        return cpuCNameHistory;
    }

    public void setCpuCNameHistory(String cpuCNameHistory) {
        this.cpuCNameHistory = cpuCNameHistory;
    }

    public String getCpuCType() {
        return cpuCType;
    }

    public void setCpuCType(String cpuCType) {
        this.cpuCType = cpuCType;
    }

    public String getCpuCAbbName() {
        return cpuCAbbName;
    }

    public void setCpuCAbbName(String cpuCAbbName) {
        this.cpuCAbbName = cpuCAbbName;
    }
}
