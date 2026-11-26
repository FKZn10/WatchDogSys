package com.jpmsoft.projectProducer.scheduledtask;

import com.jpmsoft.projectProducer.service.blxw.IBlxwInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableScheduling
@Slf4j
public class BlxwScheduleTask {
    @Resource
    private IBlxwInfoService blxwInfoService;
    @Value("${task.cron.enableBlxwCron:true}")
    private String enableCron;
    @Scheduled(cron = "${task.cron.blxwTask}")
    public void generateBlxwNoticeInfo() {
        log.info("自动生成不良行为通知数据任务:{}",enableCron);
        if("true".equals(enableCron)){
            blxwInfoService.generateNoticeInfo();
        }
    }
}