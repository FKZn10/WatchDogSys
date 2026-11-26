package com.jpmsoft.projectProducer.scheduledtask;

import com.jpmsoft.projectProducer.config.XmlConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ConfigRefreshScheduled {

    @Resource
    private XmlConfig xmlConfig;

    @Scheduled(cron = "0 0/30 * * * ? ")
    public void gmSendSmsByMessage() {
        log.info("刷新基础配置projectCompanyHistoryNameSearch内存缓存");
        xmlConfig.historySearchEnabled();
    }
}
