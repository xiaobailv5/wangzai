package com.lv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: wangzai
 * @ClassName WzApplication
 * @description:
 * @author: gxjh
 * @create: 2024-12-14 15:54
 * @Version 1.0
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.lv.mapper")
public class WzApplication {

    public static void main(String[] args) {
        SpringApplication.run(WzApplication.class,args);
    }
}