package com.jpmsoft.videoaccess.api;

import com.jpmsoft.videoaccess.config.VideoProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);
    private final VideoProperties properties;
    public ApiExceptionHandler(VideoProperties properties) { this.properties = properties; }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> business(ApiException exception) {
        return error(exception.getStatus(), exception.getCode(), exception.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> validation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage()).sorted().findFirst().orElse("参数不合法");
        return error(400, "INVALID_ARGUMENT", message);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> json(HttpMessageNotReadableException exception) {
        return error(400, "INVALID_JSON", "请求格式不正确，请检查字段、枚举和带时区的时间");
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> method(HttpRequestMethodNotSupportedException exception) {
        String[] methods = exception.getSupportedMethods();
        return ResponseEntity.status(405).header("Allow", methods == null ? "" : String.join(", ", methods))
                .body(ApiResponse.error(405, "METHOD_NOT_ALLOWED", "请求方法不支持", properties.getMode()));
    }
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> mediaType(HttpMediaTypeNotSupportedException exception) {
        return error(415, "UNSUPPORTED_MEDIA_TYPE", "请求 Content-Type 必须为 application/json");
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> unexpected(Exception exception) {
        // Do not dump request bodies, credentials, or third-party URLs in this fallback.
        log.error("Unhandled video request failure ({})", exception.getClass().getSimpleName());
        return error(500, "INTERNAL_ERROR", "服务内部错误");
    }
    private ResponseEntity<ApiResponse<Void>> error(int status, String code, String message) {
        return ResponseEntity.status(status).body(ApiResponse.error(status, code, message, properties.getMode()));
    }
}
