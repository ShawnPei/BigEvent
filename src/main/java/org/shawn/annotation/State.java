package org.shawn.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import org.shawn.validation.StateValidation;

import java.lang.annotation.*;

/**
 * @author peixy
 */
@Documented //元注解，这个注解可被抽取到帮助文档中
@Target(ElementType.FIELD) //声明注解可以被作用到什么地方，该注解只作用在属性字段上
@Retention(RetentionPolicy.RUNTIME)//在哪个阶段被保留，运行时也要用这个注解，所以要在运行时也保留着
@Constraint(
        validatedBy = { StateValidation.class} //指定提供校验规则的类
)
public @interface State {

    //用来提供校验失败之后的信息
    String message() default "状态字段只能是已发布或者草稿";
    //指定分组
    Class<?>[] groups() default {};
    //负载 获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};

}
