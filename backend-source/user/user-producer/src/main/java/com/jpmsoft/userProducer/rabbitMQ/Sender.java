package com.jpmsoft.userProducer.rabbitMQ;

import com.jpmsoft.common.base.SysAuditLogDto;
import com.jpmsoft.common.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
public class Sender {

    /**
     * 第三方数据推送队列
     */
    private final static String queueName = "thirdDataPush";

    /**
     * 审计日志
     */
    private final static String queueAuditLog = "sysAuditLog";

    /**
     * 前端web审计日志
     */
    private final static String queueWebAuditLog = "webAuditLog";
    /**
     * 数据库表管理更行
     */
    private final static String queueTableSynchronization = "tableSynchronization";

    @Resource
    private AmqpTemplate amqpTemplate;

    public void send(String content) {
        log.info("Sender:" + content);
        try {
            this.amqpTemplate.convertAndSend(queueName, content);
        } catch (Exception ex) {
            log.error("rabbit写入数据失败！+ error message:" + ex.getMessage(), ex);
            ex.printStackTrace();
        }
    }

    public void sendAudit(String content, String uId, String uName, String id, String action) {
        SysAuditLogDto log = new SysAuditLogDto();
        log.setSalContent(content);
        log.setCreateUid(uId);
        log.setCreateTime(new Date());
        log.setSalFormId(id);
        log.setSalType(action);
        log.setCreateUname(uName);
        sendAudit(JacksonUtils.toJSONString(log));
    }

    public void sendWebAudit(String content, String uId, String uName, String id, String action) {
        SysAuditLogDto log = new SysAuditLogDto();
        log.setSalContent(content);
        log.setCreateUid(uId);
        log.setCreateTime(new Date());
        log.setSalFormId(id);
        log.setSalType(action);
        log.setCreateUname(uName);
        sendWebAudit(JacksonUtils.toJSONString(log));
    }

    public void sendAudit(String content) {
        log.info("Sender:" + content);
        this.amqpTemplate.convertAndSend(queueAuditLog, content);
    }

    public void sendWebAudit(String content) {
        log.info("Sender:" + content);
        this.amqpTemplate.convertAndSend(queueWebAuditLog, content);
    }

    public void sendTableSynchronization(String content) {
        log.info("sendTableSynchronization:" + content);
        this.amqpTemplate.convertAndSend(queueTableSynchronization, content);
    }
}
