package com.jpmsoft.common.model.request.Qs;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 质量安全违规处罚
 */
public class QsViolationPunishmentRequest extends BaseFileRequest {

    private String vpId;
    private String pId;
    private String secId;
    private String conId;
    private String vpCId;
    private String vpCName;
    private String vpType;
    private String vpReason;
    private String vpCode;
    private String vpUId;
    private BigDecimal vpLd;
    private String vpWarningSituation;
    private String vpSignUid;
    private String vpSignUname;
    private Date vpSignTime;
    private String vpCreateUid;
    private String vpCreateUname;
    private Date vpCreateTime;
    private Integer vpState;
    private Date vpStateTime;
    private String vpRemark;
    private Date vpDate;
    private String vpDId;
    private String vpPageCode;
    private String vpUName;
    /**
     * 问题严重等级
     */
    private String vpLevel;
    private String qstId;
    /**
     * 类别名称
     */
    private String qstName;
    private BigDecimal vpScore;

    private String pName;
    private String conName;
    private List<Integer> vpStates;

    public String getVpId() {
        return vpId;
    }

    public void setVpId(String vpId) {
        this.vpId = vpId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getVpCId() {
        return vpCId;
    }

    public void setVpCId(String vpCId) {
        this.vpCId = vpCId;
    }

    public String getVpCName() {
        return vpCName;
    }

    public void setVpCName(String vpCName) {
        this.vpCName = vpCName;
    }

    public String getVpType() {
        return vpType;
    }

    public void setVpType(String vpType) {
        this.vpType = vpType;
    }

    public String getVpReason() {
        return vpReason;
    }

    public void setVpReason(String vpReason) {
        this.vpReason = vpReason;
    }

    public String getVpCode() {
        return vpCode;
    }

    public void setVpCode(String vpCode) {
        this.vpCode = vpCode;
    }

    public String getVpUId() {
        return vpUId;
    }

    public void setVpUId(String vpUId) {
        this.vpUId = vpUId;
    }

    public BigDecimal getVpLd() {
        return vpLd;
    }

    public void setVpLd(BigDecimal vpLd) {
        this.vpLd = vpLd;
    }

    public String getVpWarningSituation() {
        return vpWarningSituation;
    }

    public void setVpWarningSituation(String vpWarningSituation) {
        this.vpWarningSituation = vpWarningSituation;
    }

    public String getVpSignUid() {
        return vpSignUid;
    }

    public void setVpSignUid(String vpSignUid) {
        this.vpSignUid = vpSignUid;
    }

    public String getVpSignUname() {
        return vpSignUname;
    }

    public void setVpSignUname(String vpSignUname) {
        this.vpSignUname = vpSignUname;
    }

    public Date getVpSignTime() {
        return vpSignTime;
    }

    public void setVpSignTime(Date vpSignTime) {
        this.vpSignTime = vpSignTime;
    }

    public String getVpCreateUid() {
        return vpCreateUid;
    }

    public void setVpCreateUid(String vpCreateUid) {
        this.vpCreateUid = vpCreateUid;
    }

    public String getVpCreateUname() {
        return vpCreateUname;
    }

    public void setVpCreateUname(String vpCreateUname) {
        this.vpCreateUname = vpCreateUname;
    }

    public Date getVpCreateTime() {
        return vpCreateTime;
    }

    public void setVpCreateTime(Date vpCreateTime) {
        this.vpCreateTime = vpCreateTime;
    }

    public Integer getVpState() {
        return vpState;
    }

    public void setVpState(Integer vpState) {
        this.vpState = vpState;
    }

    public String getVpRemark() {
        return vpRemark;
    }

    public void setVpRemark(String vpRemark) {
        this.vpRemark = vpRemark;
    }

    public Date getVpDate() {
        return vpDate;
    }

    public void setVpDate(Date vpDate) {
        this.vpDate = vpDate;
    }


    public String getVpDId() {
        return vpDId;
    }

    public void setVpDId(String vpDId) {
        this.vpDId = vpDId;
    }

    public String getVpUName() {
        return vpUName;
    }

    public void setVpUName(String vpUName) {
        this.vpUName = vpUName;
    }

    public String getVpLevel() {
        return vpLevel;
    }

    public void setVpLevel(String vpLevel) {
        this.vpLevel = vpLevel;
    }

    public String getQstId() {
        return qstId;
    }

    public void setQstId(String qstId) {
        this.qstId = qstId;
    }

    public String getQstName() {
        return qstName;
    }

    public void setQstName(String qstName) {
        this.qstName = qstName;
    }

    public BigDecimal getVpScore() {
        return vpScore;
    }

    public void setVpScore(BigDecimal vpScore) {
        this.vpScore = vpScore;
    }

    public Date getVpStateTime() {
        return vpStateTime;
    }

    public void setVpStateTime(Date vpStateTime) {
        this.vpStateTime = vpStateTime;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getVpPageCode() {
        return vpPageCode;
    }

    public void setVpPageCode(String vpPageCode) {
        this.vpPageCode = vpPageCode;
    }

    public List<Integer> getVpStates() {
        return vpStates;
    }

    public void setVpStates(List<Integer> vpStates) {
        this.vpStates = vpStates;
    }
}
