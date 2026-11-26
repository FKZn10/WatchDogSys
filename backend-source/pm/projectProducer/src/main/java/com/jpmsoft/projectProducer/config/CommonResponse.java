package com.jpmsoft.projectProducer.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> implements Serializable {

    private static final long serialVersionUID = -7362432471302878753L;

    private int statusCode;
    private String message;
    private T data;
    private boolean success;

    public static CommonResponse<Void> ok() {
        CommonResponse<Void> commonResponse = new CommonResponse<>();
        commonResponse.setStatusCode(BaseResponseEnum.SUCCESS.getCode());
        commonResponse.setMessage(BaseResponseEnum.SUCCESS.getMsg());
        commonResponse.setSuccess(true);
        return commonResponse;
    }

    public static <T> CommonResponse<T> ok(T data) {
        CommonResponse<T> commonResponse = new CommonResponse<>();
        commonResponse.setStatusCode(BaseResponseEnum.SUCCESS.getCode());
        commonResponse.setMessage(BaseResponseEnum.SUCCESS.getMsg());
        commonResponse.setData(data);
        commonResponse.setSuccess(true);
        return commonResponse;
    }

    public static CommonResponse<Void> error(int statusCode, String message) {
        CommonResponse<Void> commonResponse = new CommonResponse<>();
        commonResponse.setStatusCode(statusCode);
        commonResponse.setMessage(message);
        return commonResponse;
    }

    public static CommonResponse<Void> error(BaseResponseEnum baseResponseEnum) {
        CommonResponse<Void> commonResponse = new CommonResponse<>();
        commonResponse.setStatusCode(baseResponseEnum.getCode());
        commonResponse.setMessage(baseResponseEnum.getMsg());
        return commonResponse;
    }

    public static CommonResponse<Void> error(BizResponseEnum bizResponseEnum) {
        CommonResponse<Void> commonResponse = new CommonResponse<>();
        commonResponse.setStatusCode(bizResponseEnum.getErrorCode());
        commonResponse.setMessage(bizResponseEnum.getErrorMsg());
        return commonResponse;
    }

    public static CommonResponse<Void> error() {
        CommonResponse<Void> commonResponse = new CommonResponse<>();
        commonResponse.setStatusCode(BaseResponseEnum.INTERNAL_SERVER_ERROR.getCode());
        commonResponse.setMessage(BaseResponseEnum.INTERNAL_SERVER_ERROR.getMsg());
        return commonResponse;
    }
}