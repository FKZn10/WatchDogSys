package com.jpmsoft.common.model.response.ThirdParty;

/**
 * 第三方响应报文
 * 北京工行
 * 第三方登录
 */
public class ThirdBaseResponse {

    private String status;
    private String errMsg;
    private String msgId;
    private String busiContent;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getBusiContent() {
        return busiContent;
    }

    public void setBusiContent(String busiContent) {
        this.busiContent = busiContent;
    }

    public ThirdBaseResponse(String msgId, String errMsg) {
        this.status = "201";
        this.msgId = msgId;
        this.errMsg = errMsg;
    }

    public ThirdBaseResponse(String msgId, String errMsg, String busiContent) {
        this.status = "0";
        this.msgId = msgId;
        this.errMsg = errMsg;
        this.busiContent = busiContent;
    }

}
