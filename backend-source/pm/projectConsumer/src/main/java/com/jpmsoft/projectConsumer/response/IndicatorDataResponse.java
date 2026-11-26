package com.jpmsoft.projectConsumer.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class IndicatorDataResponse implements Serializable {

    private static final long serialVersionUID = 6522819975978900570L;


    //请求是否成功
    private Boolean success;

    //响应状态码
    private Integer code;

    //响应信息
    private String message;

    //响应数据
    private Object data;

    public IndicatorDataResponse(){}

    public IndicatorDataResponse(String message) {
        this.code = 200;
        this.success = false;
        this.message = message;
    }

    public IndicatorDataResponse(Integer code,String message) {
        this.code = code;
        this.success = false;
        this.message = message;
    }

    public IndicatorDataResponse(Boolean success,String message) {
        this.code = 200;
        this.success = success;
        this.message = message;
    }

    public IndicatorDataResponse(Boolean success,String message,Object data) {
        this.code = 200;
        this.success = success;
        this.message = message;
        this.data=data;
    }


    public static IndicatorDataResponse ok() {
        return new IndicatorDataResponse(true, "操作成功！");
    }

    public static IndicatorDataResponse ok(Object obj) {
        return new IndicatorDataResponse(true, "操作成功！", obj);
    }

    public static IndicatorDataResponse fail() {
        return new IndicatorDataResponse("操作失败！");
    }

    public static IndicatorDataResponse fail(String msg) {
        return new IndicatorDataResponse(msg);
    }

    public static IndicatorDataResponse fail(Integer code, String msg) {
        return new IndicatorDataResponse(code, msg);
    }

    public static IndicatorDataResponse fail(String msg, Object obj) {
        return new IndicatorDataResponse(false, msg, obj);
    }

}
