package com.jpmsoft.videoaccess.api;

import com.jpmsoft.common.base.BaseResponse;

/** Company common response contract, with only video-specific error/mode metadata added. */
public final class ApiResponse<T> extends BaseResponse {
    public final String errorCode;
    public final String mode;

    private ApiResponse(int statusCode, String message, T data, int total, String errorCode, String mode) {
        super(statusCode, statusCode < 400, message, data, total);
        this.errorCode = errorCode;
        this.mode = mode;
    }

    public static <T> ApiResponse<T> ok(T data, int total, String mode) {
        return new ApiResponse<>(200, "操作成功！", data, total, null, mode);
    }

    public static ApiResponse<Void> error(int status, String code, String message, String mode) {
        return new ApiResponse<>(status, message, null, 0, code, mode);
    }
    public static <T> ApiResponse<T> accepted(T data, String mode) {
        return new ApiResponse<>(200, "请求已受理，不代表设备已执行", data, 0, null, mode);
    }
}
