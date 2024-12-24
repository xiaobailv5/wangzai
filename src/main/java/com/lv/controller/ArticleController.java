package com.lv.controller;

import com.lv.pojo.Article;
import com.lv.pojo.PageBean;
import com.lv.pojo.Result;
import com.lv.services.ArticleService;
import com.lv.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ArticleService articleService;

    /**
     * 新增文章
     * @param article
     * @return com.lv.pojo.Result
     * @author gxjh2
     * @date 2024/12/24 10:55:39
    */
    @PostMapping("/addArticle")
    public Result addArticle(@RequestBody @Validated Article article) {

        articleService.addArticle(article);

        return Result.success("添加文章成功");
    }

    /**
     * 查询文章列表
     * @param pageNum 当前页码
     * @param pageSize 每页条数
     * @param categoryId 文章分类ID
     * @param state 发布状态 已发布 || 草稿
     * @return com.lv.pojo.Result
     * @author gxjh2
     * @date 2024/12/24 11:37:11
    */
    @GetMapping("/queryArticleList")
    public Result<PageBean<Article>> queryArticleList(Integer pageNum, Integer pageSize,
                                                      @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String state) {

        PageBean<Article> articlePageBean = articleService.queryArticleList(pageNum, pageSize, categoryId, state);

        return Result.success(articlePageBean);
    }

    /**
     * 获取文章详情
     * @param id
     * @return com.lv.pojo.Result<com.lv.pojo.Article>
     * @author gxjh2
     * @date 2024/12/24 15:52:02
    */
    @GetMapping("/articleDetail")
    public Result<Article> articleDetail(@NotNull(message = "文章id不能为空") Integer id) {

        Article article = articleService.articleDetail(id);

        return Result.success(article);
    }

    /**
     * 更新文章
     * @param article
     * @return com.lv.pojo.Result
     * @author gxjh2
     * @date 2024/12/24 16:16:06
    */
    @PutMapping("/updateArticle")
    public Result updateArticle(@RequestBody @Validated Article article) {

        articleService.updateArticle(article);

        return Result.success("更新文章成功");
    }

    /**
     * 删除文章
     * @param id
     * @return com.lv.pojo.Result
     * @author gxjh2
     * @date 2024/12/24 16:58:54
    */
    @DeleteMapping("/deleteArticle")
    public Result deleteArticle(@NotNull(message = "文章id不能为空") Integer id) {

        articleService.deleteArticle(id);

        return Result.success("删除文章成功");
    }
}