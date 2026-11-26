package com.jpmsoft.apigateway.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 开启一个队列 且持久化
 *
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue interfaceLogQueue() {
        return new Queue("interfaceLog", true);
    }
}
