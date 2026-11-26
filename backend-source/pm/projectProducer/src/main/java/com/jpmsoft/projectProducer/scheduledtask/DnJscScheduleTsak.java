package com.jpmsoft.projectProducer.scheduledtask;

import com.jpmsoft.projectProducer.service.scheduledtask.impl.DnJscScheduleTsakServiceImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableScheduling
public class DnJscScheduleTsak {

    @Resource
    private DnJscScheduleTsakServiceImpl dnJscScheduleTsakService;

    /**
     * 东大驾驶舱实时统计数据
     *
     */
    @Scheduled(fixedDelay = 1000)
    public void task() {
        dnJscScheduleTsakService.startTask();
    }
}
