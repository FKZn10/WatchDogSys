package com.jpmsoft.jputils.dto;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/19 11:02
 */
public class HwIotSmsResult {
    private String resultCode;
    private List<SmsResult> resultList;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public List<SmsResult> getResultList() {
        return resultList;
    }

    public void setResultList(List<SmsResult> resultList) {
        this.resultList = resultList;
    }

    public static class SmsResult{
        private String mobile;
        private String resultCode;
        private String resultDesc;
        private String messageId;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getResultDesc() {
            return resultDesc;
        }

        public void setResultDesc(String resultDesc) {
            this.resultDesc = resultDesc;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }
    }
}

