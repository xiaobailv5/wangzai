package com.lv.common;

import com.lv.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: wangzai
 * @ClassName GlobalExceptionHandler
 * @description: 全局异常处理类
 * @author: gxjh
 * @create: 2024-12-14 16:26
 * @Version 1.0
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage() : "操作失败");
    }

}