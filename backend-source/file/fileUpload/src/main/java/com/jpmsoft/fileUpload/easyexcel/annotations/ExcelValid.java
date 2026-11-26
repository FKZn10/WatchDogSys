package com.jpmsoft.fileUpload.easyexcel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * easyexcel必填校验注解
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/2 10:53
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelValid {
    String message() default "";

    /**
     * 仅有空格的字符串 也认定为空
     * 仅用于字符串类型字段
     *
     * @return
     */
    boolean olnySpace() default true;

    /**
     * 校验值是否在某个字典内
     *
     * @return
     */
    String dirCheck() default "";
}
