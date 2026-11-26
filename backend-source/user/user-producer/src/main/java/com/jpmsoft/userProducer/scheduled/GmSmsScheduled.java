package com.jpmsoft.userProducer.scheduled;

import com.jpmsoft.userProducer.service.ISysShortMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class GmSmsScheduled {

    @Resource
    private ISysShortMessageService sysShortMessageService;

    /**
     * 光明自定义短信发送
     */
    @Scheduled(cron = "${platform.parameters.scheduling.GmSmsSend:-}")
    public void gmSendSmsByMessage() {
        log.info("定时发送短信任务");
        sysShortMessageService.gmSendSmsByMessage();
    }
}
