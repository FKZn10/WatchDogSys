package com.jpmsoft.jpbpm.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/6/25 14:13
 */
@Configuration
public class RabbitMQConfig {

    // 1.声明fanout广播模式的交换机
    @Bean
    public FanoutExchange getExchange(){
        /**
         * @params1 ：交换机名称
         * @params2 ：是否持久化
         * @params4 ：是否自动删除
         */
        return new FanoutExchange("fanout_status_exchange",true,false);
    }
}
