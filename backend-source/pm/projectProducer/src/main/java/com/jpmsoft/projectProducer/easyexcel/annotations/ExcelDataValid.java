package com.jpmsoft.projectProducer.easyexcel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * easyexcel必填校验注解
 *
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelDataValid {

    String message() default "";

    /**
     * 仅有空格的字符串 也认定为空
     * 仅用于字符串类型字段
     *
     * @return
     */
    boolean olnySpace() default true;


    /**
     * 校验数据类型必须是规定类型。默认不校验
     * @return
     */
    String dataType() default "";

    /**
     * 校验类型提示信息
     * @return
     */
    String dataTypeMsg() default "";

    /**
     * 校验值是否在某个字典内
     *
     * @return
     */
    String dirCheck() default "";

}
