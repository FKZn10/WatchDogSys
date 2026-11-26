package com.jpmsoft.common.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 密改参与字段注解
 * 被注解字段会用于密改生成字符串
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SecretField {
    String format() default "%s"; // 字段格式化方式
}
