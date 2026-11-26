package com.jpmsoft.common.exception;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获
 * 捕获GlobalExceptionHandler中未声明异常
 */
@Order(6)
@Slf4j
@RestControllerAdvice
public class JpmAllExceptionHandler {

    @Autowired
    private ExceptionMsgHandler exceptionMsgHandler;

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResponse allExceptionHandler(Exception e) {
        e.printStackTrace();
        log.error("allExceptionHandler", e);
        return ErrorCodeEnum.AllException.errorReturn(exceptionMsgHandler.outPrintln(e));
    }
}
