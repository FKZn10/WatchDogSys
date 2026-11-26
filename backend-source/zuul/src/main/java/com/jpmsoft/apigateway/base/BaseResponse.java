package com.jpmsoft.apigateway.base;

/**
 * 通用的返回信息，
 * 调用HTTP接口等统一的响应（返回）信息。
 */
public class BaseResponse {

    private Integer statusCode;
    private Boolean success;
    private String message;
    private Object data;
    private Integer total;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public BaseResponse() {

    }


    public BaseResponse(String message) {
        this.statusCode = 200;
        this.success = false;
        this.message = message;
    }

    public BaseResponse(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public BaseResponse(Integer statusCode, Boolean success, String message) {
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }

    public BaseResponse(Boolean success, String message) {
        this.statusCode = 200;
        this.success = success;
        this.message = message;
    }

    public BaseResponse(Integer statusCode, Boolean success, String message, Object data, Integer total) {
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
        this.data = data;
        this.total = total;
    }


    public BaseResponse(String message, Object data, Integer total) {
        this.statusCode = 200;
        this.success = true;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    public BaseResponse(Boolean success, String message, Object data) {
        this.statusCode = 200;
        this.success = success;
        this.message = message;
        this.data = data;
        this.total = 0;
    }

    public static BaseResponse fail(Integer statusCode, String msg) {
        return new BaseResponse(statusCode, msg);
    }
}