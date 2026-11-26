package com.jpmsoft.common.model.request.Oa;


import com.jpmsoft.common.model.BaseModeRequest;

/**
 * 通知公告阅读记录
 */
public class OaNoticeReadRequest extends BaseModeRequest {
    private String nrId;
    private String nId;
    private String nrUid;
    private String nrUname;
    private String isRead;

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getNrId() {
        return nrId;
    }

    public void setNrId(String nrId) {
        this.nrId = nrId;
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getNrUid() {
        return nrUid;
    }

    public void setNrUid(String nrUid) {
        this.nrUid = nrUid;
    }

    public String getNrUname() {
        return nrUname;
    }

    public void setNrUname(String nrUname) {
        this.nrUname = nrUname;
    }
}
