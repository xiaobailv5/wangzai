package com.lv.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义 AOP 注解，用于修改 List<Map<String, Object>> 中的某个字段值
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModifyField {

    String fieldName();  // 要修改的字段名
    String newValue();   // 新的字段值


}
