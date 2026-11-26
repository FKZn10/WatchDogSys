package com.jpmsoft.common.model.requestAndResponse.PushData;

import com.jpmsoft.common.annotations.SecretField;
import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;
import java.util.List;

/**
 * 推送记录
 */
public class PushRecordModel extends BaseModeRequest {

    @SecretField
    private String prId ;
    @SecretField
    private Date prTime;
    @SecretField
    private Integer prState;
    @SecretField
    private String prDataId;
    @SecretField
    private String prType;
    @SecretField
    private String prUrl;
    @SecretField
    private String prRequest;
    @SecretField
    private String prResponse;
    @SecretField
    private String prMsgId;

    private String sign;

    private List<String> prTimes;

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

    public Date getPrTime() {
        return prTime;
    }

    public void setPrTime(Date prTime) {
        this.prTime = prTime;
    }

    public Integer getPrState() {
        return prState;
    }

    public void setPrState(Integer prState) {
        this.prState = prState;
    }

    public String getPrDataId() {
        return prDataId;
    }

    public void setPrDataId(String prDataId) {
        this.prDataId = prDataId;
    }

    public String getPrType() {
        return prType;
    }

    public void setPrType(String prType) {
        this.prType = prType;
    }

    public String getPrUrl() {
        return prUrl;
    }

    public void setPrUrl(String prUrl) {
        this.prUrl = prUrl;
    }

    public String getPrRequest() {
        return prRequest;
    }

    public void setPrRequest(String prRequest) {
        this.prRequest = prRequest;
    }

    public String getPrResponse() {
        return prResponse;
    }

    public void setPrResponse(String prResponse) {
        this.prResponse = prResponse;
    }

    public String getPrMsgId() {
        return prMsgId;
    }

    public void setPrMsgId(String prMsgId) {
        this.prMsgId = prMsgId;
    }

    public List<String> getPrTimes() {
        return prTimes;
    }

    public void setPrTimes(List<String> prTimes) {
        this.prTimes = prTimes;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
