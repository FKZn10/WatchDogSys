package com.jpmsoft.common.model.request.ConBop;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ConSupNoticeRequest {

    /**
     * 主键
     */
    private String SnId;

    /**
     * 项目ID
     */
    private String pid;
    /**
     * 项目名
     */
    private String pname;
    /**
     * 合同ID
     */
    private String conId;
    /**
     * 合同名
     */
    private String conName;

    /**
     * 编号
     */
    private String snCode;

    /**
     * 通知单编号
     */
    private String snNo;
    /**
     * 发文单位ID
     */
    private String snIssuingUnitId;
    /**
     * 发文单位
     */
    private String snIssuingUnitName;
    /**
     * 发文单位
     */
    private String snReceivingUnitName;
    /**
     * 收文单位ID
     */
    private String snReceivingUnitId;

    /**
     * 发文日期
     */
    private Date snIssueDate;
    /**
     * 是否需要收文单位回复
     */
    private Integer snNeedReply;
    /**
     * 限定回复日期
     */
    private Date snReply;
    /**
     * 文件标题
     */
    private String snReplyDeadline;
    /**
     * 文件内容
     */
    private String snContent;
    /**
     * 是否已回复
     */
    private Integer snReplyState;

    /**
     * 创建人ID
     */
    private String snCreateUid;



    public String getSnReceivingUnitName() {
        return snReceivingUnitName;
    }

    public void setSnReceivingUnitName(String snReceivingUnitName) {
        this.snReceivingUnitName = snReceivingUnitName;
    }

    public String getSnIssuingUnitName() {
        return snIssuingUnitName;
    }

    public void setSnIssuingUnitName(String snIssuingUnitName) {
        this.snIssuingUnitName = snIssuingUnitName;
    }

    /**
     * 创建人姓名
     */
    private String snCreateUname;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date snCreateTime;


    private Integer snState;


    private Integer snrState;

    private String snrId;

    public String getSnrId() {
        return snrId;
    }

    public void setSnrId(String snrId) {
        this.snrId = snrId;
    }

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date snStateTime;

    public Integer getSnrState() {
        return snrState;
    }

    public void setSnrState(Integer snrState) {
        this.snrState = snrState;
    }

    public String getSnId() {
        return SnId;
    }

    public void setSnId(String snId) {
        SnId = snId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }

    public String getSnIssuingUnitId() {
        return snIssuingUnitId;
    }

    public void setSnIssuingUnitId(String snIssuingUnitId) {
        this.snIssuingUnitId = snIssuingUnitId;
    }

    public String getSnReceivingUnitId() {
        return snReceivingUnitId;
    }

    public void setSnReceivingUnitId(String snReceivingUnitId) {
        this.snReceivingUnitId = snReceivingUnitId;
    }

    public Date getSnIssueDate() {
        return snIssueDate;
    }

    public void setSnIssueDate(Date snIssueDate) {
        this.snIssueDate = snIssueDate;
    }

    public Integer getSnNeedReply() {
        return snNeedReply;
    }

    public void setSnNeedReply(Integer snNeedReply) {
        this.snNeedReply = snNeedReply;
    }

    public Date getSnReply() {
        return snReply;
    }

    public void setSnReply(Date snReply) {
        this.snReply = snReply;
    }

    public String getSnReplyDeadline() {
        return snReplyDeadline;
    }

    public void setSnReplyDeadline(String snReplyDeadline) {
        this.snReplyDeadline = snReplyDeadline;
    }

    public String getSnContent() {
        return snContent;
    }

    public void setSnContent(String snContent) {
        this.snContent = snContent;
    }

    public Integer getSnReplyState() {
        return snReplyState;
    }

    public void setSnReplyState(Integer snReplyState) {
        this.snReplyState = snReplyState;
    }

    public String getSnCreateUid() {
        return snCreateUid;
    }

    public void setSnCreateUid(String snCreateUid) {
        this.snCreateUid = snCreateUid;
    }

    public String getSnCreateUname() {
        return snCreateUname;
    }

    public void setSnCreateUname(String snCreateUname) {
        this.snCreateUname = snCreateUname;
    }

    public Date getSnCreateTime() {
        return snCreateTime;
    }

    public void setSnCreateTime(Date snCreateTime) {
        this.snCreateTime = snCreateTime;
    }

    public Integer getSnState() {
        return snState;
    }

    public void setSnState(Integer snState) {
        this.snState = snState;
    }

    public Date getSnStateTime() {
        return snStateTime;
    }

    public void setSnStateTime(Date snStateTime) {
        this.snStateTime = snStateTime;
    }
}
