package com.jpmsoft.common.model.requestAndResponse.SecInfo;


import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 其它采购
 */
public class SecOtherProcurementModel extends BaseFileRequest {

    private String sopId;
    private String secId;
    private String sopTitle;
    private String sopCode;
    private String sopRemarks;
    private String sopExplain;
    private String sopCreateUid;
    private String sopCreateUname;
    private Date sopCreateTime;
    private Integer sopState;
    private Date sopStateTime;
    private String sopType;
    private BigDecimal sopBiddingAmount;
    private String sopPurchaseMethod;

    private String sopEvaluation;

    private String secName;
    private String secType;
    private List<SecProjectResponse> secProjects;
    private List<SecPurchaserDTO> secPurchasers;

    public String getSopId() {
        return sopId;
    }

    public void setSopId(String sopId) {
        this.sopId = sopId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSopTitle() {
        return sopTitle;
    }

    public void setSopTitle(String sopTitle) {
        this.sopTitle = sopTitle;
    }

    public String getSopCode() {
        return sopCode;
    }

    public void setSopCode(String sopCode) {
        this.sopCode = sopCode;
    }

    public String getSopRemarks() {
        return sopRemarks;
    }

    public void setSopRemarks(String sopRemarks) {
        this.sopRemarks = sopRemarks;
    }

    public String getSopExplain() {
        return sopExplain;
    }

    public void setSopExplain(String sopExplain) {
        this.sopExplain = sopExplain;
    }

    public String getSopCreateUid() {
        return sopCreateUid;
    }

    public void setSopCreateUid(String sopCreateUid) {
        this.sopCreateUid = sopCreateUid;
    }

    public String getSopCreateUname() {
        return sopCreateUname;
    }

    public void setSopCreateUname(String sopCreateUname) {
        this.sopCreateUname = sopCreateUname;
    }

    public Date getSopCreateTime() {
        return sopCreateTime;
    }

    public void setSopCreateTime(Date sopCreateTime) {
        this.sopCreateTime = sopCreateTime;
    }

    public Integer getSopState() {
        return sopState;
    }

    public void setSopState(Integer sopState) {
        this.sopState = sopState;
    }

    public Date getSopStateTime() {
        return sopStateTime;
    }

    public void setSopStateTime(Date sopStateTime) {
        this.sopStateTime = sopStateTime;
    }

    public String getSopType() {
        return sopType;
    }

    public void setSopType(String sopType) {
        this.sopType = sopType;
    }

    public BigDecimal getSopBiddingAmount() {
        return sopBiddingAmount;
    }

    public void setSopBiddingAmount(BigDecimal sopBiddingAmount) {
        this.sopBiddingAmount = sopBiddingAmount;
    }

    public String getSopPurchaseMethod() {
        return sopPurchaseMethod;
    }

    public void setSopPurchaseMethod(String sopPurchaseMethod) {
        this.sopPurchaseMethod = sopPurchaseMethod;
    }

    public String getSopEvaluation() {
        return sopEvaluation;
    }

    public void setSopEvaluation(String sopEvaluation) {
        this.sopEvaluation = sopEvaluation;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public List<SecPurchaserDTO> getSecPurchasers() {
        return secPurchasers;
    }

    public void setSecPurchasers(List<SecPurchaserDTO> secPurchasers) {
        this.secPurchasers = secPurchasers;
    }
}
