package com.jpmsoft.common.exception;

import com.jpmsoft.common.base.ServerErrorMsg;
import com.jpmsoft.common.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 异常消息处理类
 */
@Component
public class ExceptionMsgHandler {

    @Value("${spring.application.name:-}")
    private String server;

    @Value("${static.serverMsg:false}")
    private Boolean serverMsg;

    /**
     * 找到package为com.jpmsoft（自身代码）的异常消息
     *
     * @param ex
     * @return
     */
    public ServerErrorMsg outPrintln(Exception ex) {
        if (serverMsg) {
            ServerErrorMsg msg = new ServerErrorMsg();
            msg.setServer(server);
            msg.setMsg(ex.getMessage());
            if (ex.getStackTrace() != null) {
                StackTraceElement[] stackTraceElements = ex.getStackTrace();
                for (StackTraceElement stackTraceElement : stackTraceElements) {
                    if (ValidateUtil.isNotEmpty(stackTraceElement.getClassName())) {
                        if (stackTraceElement.getClassName().startsWith("com.jpmsoft")) {
                            msg.setFile(stackTraceElement.getFileName());
                            msg.setLine(stackTraceElement.getLineNumber());
                            msg.setMethod(stackTraceElement.getMethodName());
                            break;
                        }
                    }
                }
            }
            return msg;
        }
        return null;
    }
}
