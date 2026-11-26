package com.jpmsoft.jpbpm.config;

import com.jpmsoft.jpbpm.util.RedisUtils;
import com.jpmsoft.jpbpm.util.ReflectionUtil;
import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * 输出服务启停时间
 * @Author: 李文涛 aaron
 * @date: 2024/11/21 14:55
 */
@Slf4j
@Component
public class ApplicationLifecycleLogger implements CommandLineRunner, DisposableBean {

    private long startTime;
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public void destroy() throws Exception {
        long endTime = System.currentTimeMillis();
        long uptime = endTime - this.startTime;
        log.info("服务停止->停止时间: {}, 在线时长->: {}ms", new java.util.Date(endTime), uptime);
    }

    @Override
    public void run(String... args) throws Exception {
        redisUtils.delete("tableConfiguration20250521");
        this.startTime = System.currentTimeMillis();
        String  smsTemplate="工程管理平台提醒：{项目名称}的{合同名称}的结算业务已超办理期限，当前办理人{预警责任人}，已触发{任务名称}。";
        System.out.println(smsTemplate.contains("{预警责任人}"));
        log.info("服务启动成功->启动时间: {}", new java.util.Date(this.startTime));
        log.info("服务启动成功->:解析当前包所使用的实体信息 ");
        Set<Class<?>> classes = ReflectionUtil.getClassesInPackage("com.jpmsoft.jpbpm.pojo.entity");
        parseEntities(classes);

    }

    public static void parseEntities(Set<Class<?>> entityClasses) {
        for (Class<?> entityClass : entityClasses) {
            System.out.println("Parsing entity: " + entityClass.getSimpleName());
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                System.out.println("Field: " + field.getName());
            }
        }
    }
}
