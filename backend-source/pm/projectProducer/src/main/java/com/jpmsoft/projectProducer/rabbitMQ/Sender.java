package com.jpmsoft.projectProducer.rabbitMQ;

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
     * 第三方推送
     */
    private final static String queueName = "thirdDataPush";

    /**
     * 审计日志
     */
    private final static String queueAuditLog = "sysAuditLog";

    /**
     * 城建将项目信息上的用户信息绑定到对应部门对应岗位
     * 数据库事务限制，无法在projectApproved2ImpTempDepartment方法中调用
     * syncProjectPositionPUser方法
     * 故此用rabbitmq队列缓冲处理
     */
    private final static String queueSyncDep = "queueSyncNJCJDep";
    /**
     * 城建进度计划变更
     * 同步更新建设程序模板以及详情
     */
    private final static String queueSyncPlan2Cp = "queueSyncPlan2Cp";

    /**
     * 城建同步项目到成果办理
     */
    private final static String queueSyncProCplTemp = "queueSyncProCplTemp";

    @Resource
    private AmqpTemplate rabbitmqTemplate;

    public void sendSyncPlan2Cp(String content) {
        this.rabbitmqTemplate.convertAndSend(queueSyncPlan2Cp, content);
    }


    public void sendSyncNJCJDep(String content) {
        log.info("Sender:" + content);
        this.rabbitmqTemplate.convertAndSend(queueSyncDep, content);
    }

    public void sendSyncProCplTemp(String content) {
        log.info("Sender:" + content);
        this.rabbitmqTemplate.convertAndSend(queueSyncProCplTemp, content);
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

    public void send(String content) {
        log.info("Sender:" + content);
        this.rabbitmqTemplate.convertAndSend(queueName, content);
    }

    public void sendAudit(String content) {
        log.info("Sender:" + content);
        this.rabbitmqTemplate.convertAndSend(queueAuditLog, content);
    }
}
