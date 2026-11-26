package com.jpmsoft.projectProducer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过token给入参赋值teId
 * 入参需继承于 BasePageEntity
 * 主要用于分页查询 根据租户ID过滤
 * create by:aaron
 * create time: 2022/06/26
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface TenantId {

}
