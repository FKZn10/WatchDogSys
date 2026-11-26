package com.jpmsoft.projectProducer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配合aop
 * 用于create表单数据时给入参生成编码
 * create by:aaron
 * create time: 2022/05/24
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CodeGeneration {

    /**
     * 模型内主键字段
     * 判断新增还是修改
     */
    String key();

    /**
     * 模型内code编码字段
     *
     * @return
     */
    String codeParam();

    /**
     * 编码规则
     *
     * @return
     */
    String codeRule();

    /**
     * 模型的赋值方法名
     *
     * @return
     */
    String method();

    /**
     * 请求model class
     *
     * @return
     */
    Class<?> beanClass();
}
