package com.jpmsoft.userProducer.rabbitMQ;


import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.service.ITableManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
@RabbitListener(queuesToDeclare = {@Queue(value = "tableSynchronization", declare = "true")})
public class TableSynchronization {
    @Resource
    private ITableManagementService tableManagementService;

    @RabbitHandler
    public void receiverTableSynchronization(String msg) {
        try {
            String owner = msg.split("//")[0];
            if (ValidateUtil.isNotNull(owner)) {
                log.info("开始执行数据表同步" + (new Date()).getTime());
                tableManagementService.synchronizationpBind(owner);
                log.info("数据表同步执行完成" + (new Date()).getTime());
            } else {
                log.error("数据表更新同步MQ操作失败:模式名为空");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("数据表更新同步MQ操作失败" + ex.getMessage(), ex);
        }
    }
}
