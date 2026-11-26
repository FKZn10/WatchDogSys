package com.jpmsoft.projectConsumer.config;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/3/7 10:27
 * @Description:
 * @Version: 1.0
 */
public enum BaseResponseEnum {

    /**
     * success
     */
    SUCCESS("后台处理成功！", 200),

    /**
     * bad request
     */
    BAD_REQUEST("Bad Request!", 400),

    /**
     * error
     */
    INTERNAL_SERVER_ERROR("网络连接超时，请检查！", 500),

    ;

    private String msg;
    private Integer code;

    BaseResponseEnum(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public Integer getCode() {
        return this.code;
    }
}
