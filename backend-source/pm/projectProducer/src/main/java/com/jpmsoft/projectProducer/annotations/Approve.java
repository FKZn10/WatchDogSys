package com.jpmsoft.projectProducer.annotations;


import java.lang.annotation.*;

@Target({ElementType.METHOD}) // 作用在方法上
@Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Documented // 说明该注解将被包含在javadoc中
public @interface Approve {
    //主键
    String objectId() default "";

    //创建人
    String createId() default "";

    String createName() default "";

    String title() default "";

    String fileCode() default "";

    String status() default "0";
}
