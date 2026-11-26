package com.jpmsoft.projectConsumer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 输出服务启停时间
 * @Author: 李文涛 aaron
 * @date: 2024/11/21 14:55
 */
@Slf4j
@Component
public class ApplicationLifecycleLogger implements CommandLineRunner, DisposableBean {

    private long startTime;

    @Override
    public void destroy() throws Exception {
        long endTime = System.currentTimeMillis();
        long uptime = endTime - this.startTime;
        log.info("服务停止->停止时间: {}, 在线时长->: {}ms", new java.util.Date(endTime), uptime);
    }

    @Override
    public void run(String... args) throws Exception {
        this.startTime = System.currentTimeMillis();
        log.info("服务启动成功->启动时间: {}", new java.util.Date(this.startTime));
    }
}
