package com.jpmsoft.jpbpm.scheduledTasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.jpmsoft.jpbpm.scheduledTasks.Ipml.AutoCompletionTask;

import javax.annotation.Resource;

@Slf4j
@Component
public class WorkFlow {


    @Resource
    private AutoCompletionTask autoCompletionTask;

    @Scheduled(cron = "${bpm.scheduling.autoCompTask:-}")
    public void AutoCompletionTask(){
        //执行定时任务
        log.info("---------------执行定时任务！----------------");
        try {
            autoCompletionTask.executionMethod();
        }catch (Exception ex){
            log.error(ex.getMessage(),ex);
            ex.printStackTrace();
        }
        log.info("---------------定时任务执行完成！----------------");
    }

}
