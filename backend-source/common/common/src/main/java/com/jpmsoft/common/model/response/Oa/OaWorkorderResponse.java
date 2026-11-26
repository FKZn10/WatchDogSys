package com.jpmsoft.common.model.response.Oa;

import com.jpmsoft.common.model.ProjectCommonRes;

import java.util.Date;

/**
 * 工单
 */
public class OaWorkorderResponse extends ProjectCommonRes {

    private String woId;
    private String woCode;
    private String woTitle;
    private String pId;
    private String conId;
    private String dId;
    private String woPhoneNumber;
    private String woContent;
    private String woType;
    private String woCreateUid;
    private String woCreateUname;
    private Date woCreateTime;
    private Integer woState;
    private Date woStateTime;
    private String woPriority;
    private Date woExpectedTime;
    private Date woActualTime;
    private Date woReplyTime;
    private String woReplyUid;
    private String woReplyUname;
    private String woReplyReason;
    private String woReplySolution;
    private String woReplyContent;
    private String woResolvedState;


    private String dName;
    private String conName;

    /**
     *类型：01-工单；02-系统联系单
     */
    private String orderType;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getWoId() {
        return woId;
    }

    public void setWoId(String woId) {
        this.woId = woId;
    }

    public String getWoCode() {
        return woCode;
    }

    public void setWoCode(String woCode) {
        this.woCode = woCode;
    }

    public String getWoTitle() {
        return woTitle;
    }

    public void setWoTitle(String woTitle) {
        this.woTitle = woTitle;
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

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getWoPhoneNumber() {
        return woPhoneNumber;
    }

    public void setWoPhoneNumber(String woPhoneNumber) {
        this.woPhoneNumber = woPhoneNumber;
    }

    public String getWoContent() {
        return woContent;
    }

    public void setWoContent(String woContent) {
        this.woContent = woContent;
    }

    public String getWoType() {
        return woType;
    }

    public void setWoType(String woType) {
        this.woType = woType;
    }

    public String getWoCreateUid() {
        return woCreateUid;
    }

    public void setWoCreateUid(String woCreateUid) {
        this.woCreateUid = woCreateUid;
    }

    public String getWoCreateUname() {
        return woCreateUname;
    }

    public void setWoCreateUname(String woCreateUname) {
        this.woCreateUname = woCreateUname;
    }

    public Date getWoCreateTime() {
        return woCreateTime;
    }

    public void setWoCreateTime(Date woCreateTime) {
        this.woCreateTime = woCreateTime;
    }

    public Integer getWoState() {
        return woState;
    }

    public void setWoState(Integer woState) {
        this.woState = woState;
    }

    public Date getWoStateTime() {
        return woStateTime;
    }

    public void setWoStateTime(Date woStateTime) {
        this.woStateTime = woStateTime;
    }

    public String getWoPriority() {
        return woPriority;
    }

    public void setWoPriority(String woPriority) {
        this.woPriority = woPriority;
    }

    public Date getWoExpectedTime() {
        return woExpectedTime;
    }

    public void setWoExpectedTime(Date woExpectedTime) {
        this.woExpectedTime = woExpectedTime;
    }

    public Date getWoActualTime() {
        return woActualTime;
    }

    public void setWoActualTime(Date woActualTime) {
        this.woActualTime = woActualTime;
    }

    public Date getWoReplyTime() {
        return woReplyTime;
    }

    public void setWoReplyTime(Date woReplyTime) {
        this.woReplyTime = woReplyTime;
    }

    public String getWoReplyUid() {
        return woReplyUid;
    }

    public void setWoReplyUid(String woReplyUid) {
        this.woReplyUid = woReplyUid;
    }

    public String getWoReplyUname() {
        return woReplyUname;
    }

    public void setWoReplyUname(String woReplyUname) {
        this.woReplyUname = woReplyUname;
    }

    public String getWoReplyReason() {
        return woReplyReason;
    }

    public void setWoReplyReason(String woReplyReason) {
        this.woReplyReason = woReplyReason;
    }

    public String getWoReplySolution() {
        return woReplySolution;
    }

    public void setWoReplySolution(String woReplySolution) {
        this.woReplySolution = woReplySolution;
    }

    public String getWoReplyContent() {
        return woReplyContent;
    }

    public void setWoReplyContent(String woReplyContent) {
        this.woReplyContent = woReplyContent;
    }

    public String getWoResolvedState() {
        return woResolvedState;
    }

    public void setWoResolvedState(String woResolvedState) {
        this.woResolvedState = woResolvedState;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

}
