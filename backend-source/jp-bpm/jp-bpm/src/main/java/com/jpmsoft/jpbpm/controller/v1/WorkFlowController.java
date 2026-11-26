package com.jpmsoft.jpbpm.controller.v1;

import com.jpmsoft.jpbpm.scheduledTasks.Ipml.AutoCompletionTask;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@Api(tags = "用户代办", value = "用户代办")
@RequestMapping("/v1/userTask")
public class WorkFlowController {

    @Resource
    private AutoCompletionTask autoCompletionTask;

    @GetMapping("/test")
    public void AutoCompletionTask(){
        //执行定时任务
        log.info("---------------执行定时任务！----------------");
        autoCompletionTask.executionMethod();
        log.info("---------------自动完成待办定时任务触发！----------------");
        log.info("---------------定时任务执行完成！----------------");
    }

}
