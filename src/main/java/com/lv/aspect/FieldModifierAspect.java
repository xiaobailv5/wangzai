package com.lv.aspect;

/**
 * @projectName: wangzai
 * @package: com.lv.aspect
 * @className: FieldModifierAspect
 * @author: dus
 * @description:
 * @date: 2025/1/8 15:59
 * @version: 1.0
 */

import com.lv.aop.ModifyField;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * AOP 切面类，用于修改 List<Map<String, Object>> 中的某个字段值
 */
@Aspect
@Component
public class FieldModifierAspect {


    /**
     * 拦截带有 @ModifyField 注解的方法
     *
     * @param joinPoint 连接点
     * @param modifyField 注解
     * @return 修改后的返回值
     * @throws Throwable
     */
    @Around("@annotation(modifyField)")
    public Object modifyField(ProceedingJoinPoint joinPoint, ModifyField modifyField) throws Throwable {
        // 执行目标方法并获取返回值
        Object result = joinPoint.proceed();

        // 检查返回值是否为 List<Map<String, Object>>
        if (result instanceof List) {
            List<?> resultList = (List<?>) result;

            // 遍历列表中的每个 Map，并修改指定字段的值
            for (Object item : resultList) {
                if (item instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) item;
                    // 修改指定字段的值
                    map.put(modifyField.fieldName(), modifyField.newValue());
                }
            }
        }

        // 返回修改后的结果
        return result;
    }


}
