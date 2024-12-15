package com.lv.controller;

import com.lv.pojo.Result;
import com.lv.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: wangzai
 * @ClassName ArticleController
 * @description: 文章控制类
 * @author: gxjh
 * @create: 2024-12-14 17:32
 * @Version 1.0
 **/
@RestController
@RequestMapping("/article")
public class ArticleController {


    @GetMapping("/queryArticleList")
    public Result queryArticleList() {

        return Result.success("所有的文章数据");
    }


}