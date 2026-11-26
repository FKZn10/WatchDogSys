package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.model.BaseFileRequest;

import java.util.Date;

public class ProImageProgressReplyModel extends BaseFileRequest {
    private String id;
    private String ipcId;
    private String content;
    private String iprId;
    private String replyUname;
    private String replyUid;
    private String createUid;
    private String createUname;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIpcId() {
        return ipcId;
    }

    public void setIpcId(String ipcId) {
        this.ipcId = ipcId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIprId() {
        return iprId;
    }

    public void setIprId(String iprId) {
        this.iprId = iprId;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public String getCreateUname() {
        return createUname;
    }

    public void setCreateUname(String createUname) {
        this.createUname = createUname;
    }

    public String getReplyUname() {
        return replyUname;
    }

    public void setReplyUname(String replyUname) {
        this.replyUname = replyUname;
    }

    public String getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(String replyUid) {
        this.replyUid = replyUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
