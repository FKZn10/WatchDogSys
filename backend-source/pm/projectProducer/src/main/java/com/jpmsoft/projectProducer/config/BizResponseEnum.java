package com.jpmsoft.projectProducer.config;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/3/7 10:27
 * @Description:
 * @Version: 1.0
 */
public enum BizResponseEnum {

    /**
     * 文件上传失败！
     */
    FILE_UPLOAD_FAILED("文件上传失败！", 500001),

    PUSH_MQ_MESSAGE_FAILED("MQ消息发送失败！", 500002),

    DATA_ID_IS_NULL("数据主键ID为null", 500003),

    FILE_MAX_UPLOAD_SIZE_EXCEEDED("文件大小超出限制！", 500004),

    ;


    private String errorMsg;
    private Integer errorCode;

    BizResponseEnum(String errorMsg, Integer errorCode) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
