package com.jpmsoft.apigateway.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Sender {

    private static String queueName = "interfaceLog";
    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    @Value("${rabbit.sendMessage.enable}")
    private String sendMsg;

    public void send(String content) {
        if(sendMsg.equals("true")) {
            try {
                log.info("Sender:" + content);
                this.rabbitmqTemplate.convertAndSend(queueName, content);
            }catch (Exception ex)
            {
                log.error("rabbit写入数据失败！+ error message:"+ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
