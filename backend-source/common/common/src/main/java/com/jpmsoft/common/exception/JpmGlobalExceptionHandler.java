package com.jpmsoft.common.exception;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ErrorCodeEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.net.ConnectException;
import java.sql.SQLException;

/**
 * 全局异常捕获
 */
@Order(5)
@Slf4j
@RestControllerAdvice
public class JpmGlobalExceptionHandler {

    @Autowired
    private ExceptionMsgHandler exceptionMsgHandler;

    @Value("${spring.servlet.multipart.max-file-size:-}")
    private String maxFile;

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public BaseResponse maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        e.printStackTrace();
        log.error("MaxUploadSizeExceededException", e);
        String msg = ErrorCodeEnum.MaxUploadSizeExceededException.getMsg();
        if (ValidateUtil.isNotEmpty(maxFile) && !maxFile.equals("-"))
            msg = msg + "文件大小超出" + maxFile;
        return BaseResponse.fail(ErrorCodeEnum.MaxUploadSizeExceededException.getCode(), msg);
        // return   ErrorCodeEnum.MaxUploadSizeExceededException.errorReturn(exceptionMsgHandler.outPrintln(e));
    }


    @ExceptionHandler(value = SQLException.class)
    public BaseResponse sqlExceptionHandler(SQLException e) {
        e.printStackTrace();
        log.error("sqlExceptionHandler", e);
        String state = ValidateUtil.isEmpty(e.getSQLState()) ? "99999999" : e.getSQLState();
        switch (state) {
            //缺少表或者字段
            case "22000":
                return ErrorCodeEnum.NotFoundFieldOrTable.errorReturn(exceptionMsgHandler.outPrintln(e));
            //违反引用约束
            case "23000":
                return ErrorCodeEnum.IntegrityConstraint.errorReturn(exceptionMsgHandler.outPrintln(e));
            default:
                return ErrorCodeEnum.SQLException.errorReturn(exceptionMsgHandler.outPrintln(e));
        }
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse paramsValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        log.error("paramsValidException", e);
        return BaseResponse.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(BindException.class)
    public BaseResponse bindException(BindException e) {
        e.printStackTrace();
        log.error("BindException", e);
        return BaseResponse.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 请求参数转换异常
     * 便于前端开发排查
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public BaseResponse httpMessageNotReadableHandler(HttpMessageNotReadableException e) {
        e.printStackTrace();
        log.error("httpMessageNotReadableHandler", e);
        return ErrorCodeEnum.HttpMessageNotReadable.errorReturn(exceptionMsgHandler.outPrintln(e));
    }

    @ExceptionHandler(value = ConnectException.class)
    public BaseResponse connectExceptionHandler(ConnectException e) {
        e.printStackTrace();
        log.error("connectExceptionHandler", e);
        return ErrorCodeEnum.ConnectException.errorReturn(exceptionMsgHandler.outPrintln(e));
    }

    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = JPMException.class)
    public BaseResponse JPMException(JPMException e) {
        e.printStackTrace();
        log.error("JPMException", e);
        return BaseResponse.fail(e.getCode(), e.getMsg());
    }

}
