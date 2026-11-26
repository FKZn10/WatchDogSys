package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 编码规则配置
 */
public class SysSequenceSetDto extends BaseModeRequest  implements Serializable {

    private static final long serialVersionUID = 3393899363637060979L;
    private String sId;
    private String sName;
    private String tCode;
    private String sDelimiter;
    private String sReset;
    private Integer sStep;
    private Integer sNo;
    private String sCode;
    private String sCreateUid;
    private String sCreateUname;
    private Date sCreateTime;
    private String sUpdateUid;
    private String sUpdateUname;
    private Date sUpdateTime;
    private Boolean sEnable;

    private List<SysSequenceSetDtlDto> sysSequenceSetDtls;

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    public String getsDelimiter() {
        return sDelimiter;
    }

    public void setsDelimiter(String sDelimiter) {
        this.sDelimiter = sDelimiter;
    }

    public String getsReset() {
        return sReset;
    }

    public void setsReset(String sReset) {
        this.sReset = sReset;
    }

    public Integer getsStep() {
        return sStep;
    }

    public void setsStep(Integer sStep) {
        this.sStep = sStep;
    }

    public Integer getsNo() {
        return sNo;
    }

    public void setsNo(Integer sNo) {
        this.sNo = sNo;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public String getsCreateUid() {
        return sCreateUid;
    }

    public void setsCreateUid(String sCreateUid) {
        this.sCreateUid = sCreateUid;
    }

    public String getsCreateUname() {
        return sCreateUname;
    }

    public void setsCreateUname(String sCreateUname) {
        this.sCreateUname = sCreateUname;
    }

    public Date getsCreateTime() {
        return sCreateTime;
    }

    public void setsCreateTime(Date sCreateTime) {
        this.sCreateTime = sCreateTime;
    }

    public String getsUpdateUid() {
        return sUpdateUid;
    }

    public void setsUpdateUid(String sUpdateUid) {
        this.sUpdateUid = sUpdateUid;
    }

    public String getsUpdateUname() {
        return sUpdateUname;
    }

    public void setsUpdateUname(String sUpdateUname) {
        this.sUpdateUname = sUpdateUname;
    }

    public Date getsUpdateTime() {
        return sUpdateTime;
    }

    public void setsUpdateTime(Date sUpdateTime) {
        this.sUpdateTime = sUpdateTime;
    }

    public Boolean getsEnable() {
        return sEnable;
    }

    public void setsEnable(Boolean sEnable) {
        this.sEnable = sEnable;
    }

    public List<SysSequenceSetDtlDto> getSysSequenceSetDtls() {
        return sysSequenceSetDtls;
    }

    public void setSysSequenceSetDtls(List<SysSequenceSetDtlDto> sysSequenceSetDtls) {
        this.sysSequenceSetDtls = sysSequenceSetDtls;
    }
}
