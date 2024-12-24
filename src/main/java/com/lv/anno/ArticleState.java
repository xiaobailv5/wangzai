package com.lv.anno;


import com.lv.validated.ArticleStateValidated;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented//元注解
//注解来定义一个新的验证注解
@Constraint(validatedBy = {ArticleStateValidated.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArticleState {

    String message() default "发布状态 只能是已发布或草稿";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
