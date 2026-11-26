package com.jpmsoft.projectProducer.scheduledtask;

import com.jpmsoft.projectProducer.service.qualitySafety.IQsPlanTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableScheduling
@Slf4j
public class QsPlanScheduleTask {
    @Resource
    private IQsPlanTaskService qsPlanTaskService;
    @Value("${task.cron.enableCron:false}")
    private String enableCron;
    @Scheduled(cron = "${task.cron.qsPlanTask}")
    public void regularlyTask() {
        log.info("生成短信任务{}",enableCron);
        if("true".equals(enableCron)){
            qsPlanTaskService.generateSms();
        }
    }
}
