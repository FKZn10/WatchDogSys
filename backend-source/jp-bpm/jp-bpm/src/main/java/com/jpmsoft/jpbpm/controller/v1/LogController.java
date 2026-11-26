package com.jpmsoft.jpbpm.controller.v1;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.LogLastNLine;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 11:55
 */
@RestController
@RequestMapping("/v1/log")
public class LogController {

    @GetMapping(value = "/logView")
    public BaseResponse getLogs(@RequestParam(required = false) Integer size) {
        if (size == null) size = 200;
        // 获取LoggerContext
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        // 获取root logger
        Logger rootLogger = loggerContext.getLogger("ROOT");
        String LOG_FILE_PATH = null;
        // 遍历root logger的所有appender
        for (Iterator<Appender<ILoggingEvent>> it = rootLogger.iteratorForAppenders(); it.hasNext(); ) {
            Appender<ILoggingEvent> appender = it.next();
            if (appender instanceof FileAppender) {
                // 如果找到FileAppender，获取其文件路径
                FileAppender<ILoggingEvent> fileAppender = (FileAppender<ILoggingEvent>) appender;
                LOG_FILE_PATH = fileAppender.getFile();
                break; // 假设只需要找到第一个FileAppender
            }
        }
        return BaseResponse.ok(LogLastNLine.getLastNLines(LOG_FILE_PATH, size));
    }

}
