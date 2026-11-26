package com.jpmsoft.userProducer.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 动态代码用于获取Bean
 */
@Component
public class SpringContextUtil {
    private static ApplicationContext context;

    public SpringContextUtil(ApplicationContext context) {
        SpringContextUtil.context = context;
    }
    // 获取上下文对象（你需要的方法）
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

//    public static Object getBean(String beanName) {
//        return context.getBean(beanName);
//    }
}