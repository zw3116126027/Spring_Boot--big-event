package org.example.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validation.StateValidation;

import java.lang.annotation.*;

@Documented  //元注解
@Target({ElementType.FIELD})  //应用于字段
@Retention(RetentionPolicy.RUNTIME)  //表示注解信息会被VM保留，可以在运行时通过反射读取注解信息
@Constraint(validatedBy = {StateValidation.class})  //指定提供校验规则的类
public @interface State {
    //提供校验失败后的提示信息
    String message() default "state参数的值只能是已发布或草稿";
    //指定分组
    Class<?>[] groups() default {};
    //负载  获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
