package com.jpmsoft.common.base;

/**
 * 通用的返回信息，
 * 调用HTTP接口等统一的响应（返回）信息。
 */
public class BaseResponse {

    //响应状态码
    private Integer statusCode;
    //请求逻辑是否成功
    private Boolean success;
    //响应说明消息
    private String message;
    //服务内错误具体信息
    private ServerErrorMsg serverErrorMsg;
    //响应数据
    private Object data;
    //数据条数
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

    public ServerErrorMsg getServerErrorMsg() {
        return serverErrorMsg;
    }

    public void setServerErrorMsg(ServerErrorMsg serverErrorMsg) {
        this.serverErrorMsg = serverErrorMsg;
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

    public BaseResponse(Integer statusCode, Boolean success, String message) {
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }

    public BaseResponse(Integer statusCode, Boolean success, String message, ServerErrorMsg serverErrorMsg) {
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
        this.serverErrorMsg = serverErrorMsg;
    }

    public BaseResponse(String message) {
        this.statusCode = 200;
        this.success = false;
        this.message = message;
    }

    public BaseResponse(Boolean success, String message) {
        this.statusCode = 200;
        this.success = success;
        this.message = message;
    }

    public BaseResponse(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.success = false;
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

    public static BaseResponse ok() {
        return new BaseResponse(true, "操作成功！");
    }

    public static BaseResponse ok(Object obj) {
        return new BaseResponse(true, "操作成功！", obj);
    }

    public static BaseResponse ok(Object obj, Integer total) {
        return new BaseResponse("操作成功！", obj, total);
    }

    public static BaseResponse ok(Object obj, Long total) {
        return new BaseResponse("操作成功！", obj, Math.toIntExact(total));
    }

    public static BaseResponse ok(String msg, Object obj) {
        return new BaseResponse(true, msg, obj);
    }

    public static BaseResponse fail() {
        return new BaseResponse("操作失败！");
    }

    public static BaseResponse fail(String msg) {
        return new BaseResponse(msg);
    }

    public static BaseResponse fail(Integer statusCode, String msg) {
        return new BaseResponse(statusCode, msg);
    }

    public static BaseResponse fail(String msg, Object obj) {
        return new BaseResponse(false, msg, obj);
    }

    public boolean isSuccess() {
        return this.success && this.statusCode == 200;
    }
}