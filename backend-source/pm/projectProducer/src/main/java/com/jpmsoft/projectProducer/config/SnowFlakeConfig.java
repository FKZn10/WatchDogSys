package com.jpmsoft.projectProducer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program:
 * @description:
 * @author: zhangl
 * @create: 2021-05-05 21:43
 **/
@Configuration
public class SnowFlakeConfig {
    @Value("${snowflake.workerId}")
    private long workerId;
    @Value("${snowflake.datacenterId}")
    private long datacenterId;

    @Bean("mySnowFlake")
    public SnowflakeNew getSnowflake() {
        return new SnowflakeNew(workerId, datacenterId);
    }
}
