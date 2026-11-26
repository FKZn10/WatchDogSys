package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;
import java.util.List;

public class SecJGAgencySelectionRecordDtlDto extends BaseFileRequest {

    private String srdId;
    private String exId;
    private String srId;
    //参与会议/咨询次数
    private Integer srdCount;
    /**
     * 选取类型(抽取|自选)
     */
    private String srdType;
    //是否需要回避
    private Integer srdAvoid;
    //是否按时参加
    private Integer srdAttend;
    //是否请假
    private Integer srdVacation;
    //抽取时间
    private Date srdTime;
    //确认时间
    private Date srdConfirmTime;
    //技术类专家申请专业详情
    private String srdEsType;

    //需要排除的标段id
    private List<String> notSecIdList;

    //同一开标时间时间
    private String sameBidTime;



    public String getSrdId() {
        return srdId;
    }

    public void setSrdId(String srdId) {
        this.srdId = srdId;
    }

    public String getExId() {
        return exId;
    }

    public void setExId(String exId) {
        this.exId = exId;
    }

    public String getSrId() {
        return srId;
    }

    public void setSrId(String srId) {
        this.srId = srId;
    }

    public Integer getSrdCount() {
        return srdCount;
    }

    public void setSrdCount(Integer srdCount) {
        this.srdCount = srdCount;
    }

    public String getSrdType() {
        return srdType;
    }

    public void setSrdType(String srdType) {
        this.srdType = srdType;
    }

    public Integer getSrdAvoid() {
        return srdAvoid;
    }

    public void setSrdAvoid(Integer srdAvoid) {
        this.srdAvoid = srdAvoid;
    }

    public Integer getSrdAttend() {
        return srdAttend;
    }

    public void setSrdAttend(Integer srdAttend) {
        this.srdAttend = srdAttend;
    }

    public Integer getSrdVacation() {
        return srdVacation;
    }

    public void setSrdVacation(Integer srdVacation) {
        this.srdVacation = srdVacation;
    }

    public Date getSrdTime() {
        return srdTime;
    }

    public void setSrdTime(Date srdTime) {
        this.srdTime = srdTime;
    }

    public Date getSrdConfirmTime() {
        return srdConfirmTime;
    }

    public void setSrdConfirmTime(Date srdConfirmTime) {
        this.srdConfirmTime = srdConfirmTime;
    }

    public String getSrdEsType() {
        return srdEsType;
    }

    public void setSrdEsType(String srdEsType) {
        this.srdEsType = srdEsType;
    }

    public List<String> getNotSecIdList() {
        return notSecIdList;
    }

    public void setNotSecIdList(List<String> notSecIdList) {
        this.notSecIdList = notSecIdList;
    }

    public String getSameBidTime() {
        return sameBidTime;
    }

    public void setSameBidTime(String sameBidTime) {
        this.sameBidTime = sameBidTime;
    }
}
