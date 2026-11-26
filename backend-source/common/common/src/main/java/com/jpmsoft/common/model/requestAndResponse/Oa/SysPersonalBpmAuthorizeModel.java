package com.jpmsoft.common.model.requestAndResponse.Oa;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

/**
 * 文档信息
 */
public class SysPersonalBpmAuthorizeModel extends BaseFileRequest {

    public String baId;
    public String baAuthorizerUid;
    public String baAuthorizerUname;
    public Date baStartTime;
    public Date baEndTime;
    public String baCreateUid;
    public String baCreateUname;
    public Date baCreateTime;
    public Integer baState;
    public Date baStateTime;
    public String baRemark;
    public String baTcode;
    private String baPromoter;

    public String getBaId() {
        return baId;
    }

    public void setBaId(String baId) {
        this.baId = baId;
    }

    public String getBaAuthorizerUid() {
        return baAuthorizerUid;
    }

    public void setBaAuthorizerUid(String baAuthorizerUid) {
        this.baAuthorizerUid = baAuthorizerUid;
    }

    public String getBaAuthorizerUname() {
        return baAuthorizerUname;
    }

    public void setBaAuthorizerUname(String baAuthorizerUname) {
        this.baAuthorizerUname = baAuthorizerUname;
    }

    public Date getBaStartTime() {
        return baStartTime;
    }

    public void setBaStartTime(Date baStartTime) {
        this.baStartTime = baStartTime;
    }

    public Date getBaEndTime() {
        return baEndTime;
    }

    public void setBaEndTime(Date baEndTime) {
        this.baEndTime = baEndTime;
    }

    public String getBaCreateUid() {
        return baCreateUid;
    }

    public void setBaCreateUid(String baCreateUid) {
        this.baCreateUid = baCreateUid;
    }

    public String getBaCreateUname() {
        return baCreateUname;
    }

    public void setBaCreateUname(String baCreateUname) {
        this.baCreateUname = baCreateUname;
    }

    public Date getBaCreateTime() {
        return baCreateTime;
    }

    public void setBaCreateTime(Date baCreateTime) {
        this.baCreateTime = baCreateTime;
    }

    public Integer getBaState() {
        return baState;
    }

    public void setBaState(Integer baState) {
        this.baState = baState;
    }

    public Date getBaStateTime() {
        return baStateTime;
    }

    public void setBaStateTime(Date baStateTime) {
        this.baStateTime = baStateTime;
    }

    public String getBaRemark() {
        return baRemark;
    }

    public void setBaRemark(String baRemark) {
        this.baRemark = baRemark;
    }

    public String getBaTcode() {
        return baTcode;
    }

    public void setBaTcode(String baTcode) {
        this.baTcode = baTcode;
    }

    public String getBaPromoter() {
        return baPromoter;
    }

    public void setBaPromoter(String baPromoter) {
        this.baPromoter = baPromoter;
    }
}
