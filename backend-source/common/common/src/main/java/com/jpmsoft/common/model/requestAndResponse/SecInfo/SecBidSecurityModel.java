package com.jpmsoft.common.model.requestAndResponse.SecInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 投标保证金
 */
public class SecBidSecurityModel extends BaseFileRequest {

    private String sbsId;
    private String secId;
    private String sbsTitle;
    private String sbsRemarks;
    private Integer sbsNumberEnterprises;
    private BigDecimal sbsCumulativePaymentAmount;
    private String sbsCreateUid;
    private String sbsCreateUname;
    private Date sbsCreateTime;
    private Integer sbsState;
    private Date sbsStateTime;

    private String secName;
    private String secType;
    private List<SecProjectResponse> secProjects;
    private List<String> sbsCreateTimes;
    private List<String> sbsEnterPrices;
    private List<String> sbsPayAmounts;


    public String getSecType() {
        return secType;
    }

    public void setSecType(String secType) {
        this.secType = secType;
    }

    public String getSbsId() {
        return sbsId;
    }

    public void setSbsId(String sbsId) {
        this.sbsId = sbsId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getSbsTitle() {
        return sbsTitle;
    }

    public void setSbsTitle(String sbsTitle) {
        this.sbsTitle = sbsTitle;
    }

    public String getSbsRemarks() {
        return sbsRemarks;
    }

    public void setSbsRemarks(String sbsRemarks) {
        this.sbsRemarks = sbsRemarks;
    }

    public Integer getSbsNumberEnterprises() {
        return sbsNumberEnterprises;
    }

    public void setSbsNumberEnterprises(Integer sbsNumberEnterprises) {
        this.sbsNumberEnterprises = sbsNumberEnterprises;
    }

    public BigDecimal getSbsCumulativePaymentAmount() {
        return sbsCumulativePaymentAmount;
    }

    public void setSbsCumulativePaymentAmount(BigDecimal sbsCumulativePaymentAmount) {
        this.sbsCumulativePaymentAmount = sbsCumulativePaymentAmount;
    }

    public String getSbsCreateUid() {
        return sbsCreateUid;
    }

    public void setSbsCreateUid(String sbsCreateUid) {
        this.sbsCreateUid = sbsCreateUid;
    }

    public String getSbsCreateUname() {
        return sbsCreateUname;
    }

    public void setSbsCreateUname(String sbsCreateUname) {
        this.sbsCreateUname = sbsCreateUname;
    }

    public Date getSbsCreateTime() {
        return sbsCreateTime;
    }

    public void setSbsCreateTime(Date sbsCreateTime) {
        this.sbsCreateTime = sbsCreateTime;
    }

    public Integer getSbsState() {
        return sbsState;
    }

    public void setSbsState(Integer sbsState) {
        this.sbsState = sbsState;
    }

    public Date getSbsStateTime() {
        return sbsStateTime;
    }

    public void setSbsStateTime(Date sbsStateTime) {
        this.sbsStateTime = sbsStateTime;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public List<SecProjectResponse> getSecProjects() {
        return secProjects;
    }

    public void setSecProjects(List<SecProjectResponse> secProjects) {
        this.secProjects = secProjects;
    }

    public List<String> getSbsCreateTimes() {
        return sbsCreateTimes;
    }

    public void setSbsCreateTimes(List<String> sbsCreateTimes) {
        this.sbsCreateTimes = sbsCreateTimes;
    }

    public List<String> getSbsEnterPrices() {
        return sbsEnterPrices;
    }

    public void setSbsEnterPrices(List<String> sbsEnterPrices) {
        this.sbsEnterPrices = sbsEnterPrices;
    }

    public List<String> getSbsPayAmounts() {
        return sbsPayAmounts;
    }

    public void setSbsPayAmounts(List<String> sbsPayAmounts) {
        this.sbsPayAmounts = sbsPayAmounts;
    }
}
