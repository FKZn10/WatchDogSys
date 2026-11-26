package com.jpmsoft.projectProducer.rabbitMQ;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCp;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCplTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Component
@RabbitListener(queuesToDeclare = {@Queue(value = "queueSyncProCplTemp")})
public class SyncNJCJProCplTemp {
    @Resource
    private IProjectCplTemplateService projectCplTemplateService;

    @RabbitHandler
    public void generateProjectCpRpr(String msg) {
        log.info("消息：{}", msg);
        BaseResponse response = null;
        if (msg != null) {
            Map<String, String> msgMap = JacksonUtils.parseObject(msg, new TypeReference<Map<String, String>>() {
            });
            response = projectCplTemplateService.generateProjectCpRpr(
                    msgMap.get("pId"),
                    JacksonUtils.parseObject(msgMap.get("projectCp"), ProjectCp.class),
                    JacksonUtils.parseObject(msgMap.get("cpTemp"), FileTemplateTree.class)
            );
        }
        log.info("响应结果：{}", response);
    }
}
