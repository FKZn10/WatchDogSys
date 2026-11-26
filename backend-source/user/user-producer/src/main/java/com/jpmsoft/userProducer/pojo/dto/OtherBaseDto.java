package com.jpmsoft.userProducer.pojo.dto;

import java.io.Serializable;

/**
 * 第三方请求报文
 * 北京工行
 * 第三方登录
 */
public class OtherBaseDto implements Serializable {

    private static final long serialVersionUID = 7304441613351242573L;
    private String appId;
    private String msgId;
    private String format;
    private String charset;
    private String encryptType;
    private String signType;
    private String sign;
    private String timestamp;
    private String busiContent;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getEncryptType() {
        return encryptType;
    }

    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBusiContent() {
        return busiContent;
    }

    public void setBusiContent(String busiContent) {
        this.busiContent = busiContent;
    }
}
