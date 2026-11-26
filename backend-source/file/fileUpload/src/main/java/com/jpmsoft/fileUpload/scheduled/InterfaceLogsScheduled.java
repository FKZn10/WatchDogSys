package com.jpmsoft.fileUpload.scheduled;

import com.jpmsoft.fileUpload.service.ISysLoggerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InterfaceLogsScheduled {

    @Autowired
    private ISysLoggerInfoService sysLoggerInfoService;


    /**
     * 定时导出接口日志到excel并删除已导出记录
     * 严禁配置到高峰时段执行
     */
    @Scheduled(cron = "${platform.parameters.scheduling.exportAndDelLogs:-}")
    public void gmSendSmsByMessage() {
        log.info("定时导出并删除接口日志数据");
        try {
            sysLoggerInfoService.exportLoggerInfo2Excel();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("定时导出并删除接口日志数据失败！" + ex.getMessage(),ex);
        }
    }
}
