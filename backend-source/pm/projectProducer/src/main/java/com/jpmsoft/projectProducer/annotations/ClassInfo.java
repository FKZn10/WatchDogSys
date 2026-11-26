package com.jpmsoft.projectProducer.annotations;

import java.lang.annotation.*;

@Target({ElementType.FIELD}) // 作用在方法上
@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented 
public @interface ClassInfo {
     /**
     * title
     */
    String title() default "";
    /**
     * type
     */
    String type() default "";
    /**
     * value
     */
    String value() default "";
    /**
     * dictionary
     */
    String dictionary() default "";
}
