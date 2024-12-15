package com.lv.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * hello
 */
@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}