package com.jpmsoft.common.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注文件字段
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/3 9:43
 */
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface FileFields {
    /**
     * 序号
     *
     * @return
     */
    int index();

    /**
     * fileRelationId默认值为表单ID
     * 其他标注修改时传入的文件ID
     *
     * @return
     */
    String type() default "fileRelationId";
}
