package com.jpmsoft.apigateway.base;

import java.util.Objects;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/25 21:45
 */
public enum HttpResMsgEnum {

    SC_NOT_FOUND("未找到请求资源!", 404),
    SC_REQUEST_TIMEOUT("请求超时!", 408);

    private String msg;
    private Integer status;

    HttpResMsgEnum(String msg, Integer status) {
        this.msg = msg;
        this.status = status;
    }

    public static String getMsg(Integer status) {
        for (HttpResMsgEnum c : HttpResMsgEnum.values()) {
            if (status != null && Objects.equals(c.getStatus(), status)) {
                return c.msg;
            }
        }
        return "服务响应失败!";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
