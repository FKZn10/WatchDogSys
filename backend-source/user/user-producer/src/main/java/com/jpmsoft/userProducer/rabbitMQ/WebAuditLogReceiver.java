package com.jpmsoft.userProducer.rabbitMQ;


import com.jpmsoft.common.base.SysAuditLogDto;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.userProducer.pojo.entity.WebAuditLog;
import com.jpmsoft.userProducer.service.IWebAuditLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/25 22:00
 */
@Slf4j
@Component
@RabbitListener(queuesToDeclare = {@Queue(value = "webAuditLog", declare = "true")})
public class WebAuditLogReceiver {

    @Resource
    private IWebAuditLogService webAuditLogService;
//    @Resource
//    private IApiUtilsService apiUtilsService;
//    @Resource
//    private ISysConfigEntityService sysConfigEntityService;

    @RabbitHandler
    public void receiverAuditLog(String msg) {
        try {
            SysAuditLogDto audit = JacksonUtils.parseObject(msg, SysAuditLogDto.class);
            WebAuditLog log = new WebAuditLog();
            BeanUtils.copyProperties(audit, log);
            //项目数据完整性验证
//            InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
//            log.setSign(apiUtilsService.sign(ied, ied.getAuditSign(), log));
            webAuditLogService.save(log);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("审计日志MQ操作失败" + ex.getMessage(), ex);
        }
    }
}
