package com.jpmsoft.projectProducer.scheduledtask;

import com.jpmsoft.projectProducer.service.project.IPreProjectSolutionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableScheduling
@Slf4j
public class PreProjectSolutionScheduleTask {
    @Resource
    private IPreProjectSolutionService preProjectSolutionService;
    @Value("${task.cron.enablePreProjectSolutionCron:true}")
    private String enableCron;
    @Scheduled(cron = "${task.cron.preProjectSolutionTask}")
    public void refreshInfo() {
        log.info("生成指标任务：{}",enableCron);
        if("true".equals(enableCron)){
            preProjectSolutionService.refreshInfo();
        }
    }
}
