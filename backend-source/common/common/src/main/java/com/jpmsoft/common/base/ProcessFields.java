package com.jpmsoft.common.base;

import com.jpmsoft.common.enums.ProcessFieldEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注流程字段
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/3 9:43
 */
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ProcessFields {

    ProcessFieldEnum type();
}
