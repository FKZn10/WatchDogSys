package com.jpmsoft.common.rabbit;

import com.jpmsoft.common.base.SysAuditLogDto;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Component
public class AuditSender {

    /**
     * 审计日志
     */
    private final static String queueAuditLog = "sysAuditLog";

    @Resource
    private AmqpTemplate rabbitmqTemplate;

    /**
     * 审计日志
     *
     * @param content 日志内容
     * @param user    操作人信息
     * @param id      业务表单ID
     * @param action  操作类型
     */
    public void sendAudit(String content, LogUserInfoVo user, String id, String action) {
        sendAudit(content, user.getId(), user.getName(), id, action);
    }

    public void sendAudit(String content, String currentUid, String currentUname, String id, String action) {
        SysAuditLogDto log = new SysAuditLogDto();
        log.setSalContent(content);
        log.setCreateUid(currentUid);
        log.setCreateTime(new Date());
        log.setSalFormId(id);
        log.setSalType(action);
        log.setCreateUname(currentUname);
        sendAudit(JacksonUtils.toJSONString(log));
    }

    public void sendAudit(String content) {
        log.info("Sender:" + content);
        this.rabbitmqTemplate.convertAndSend(queueAuditLog, content);
    }
}
