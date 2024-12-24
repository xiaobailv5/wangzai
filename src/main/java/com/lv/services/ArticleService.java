package com.lv.services;

import com.lv.pojo.Article;
import com.lv.pojo.PageBean;

public interface ArticleService {

    /**
     * 新增文章
     * @param article
     * @return void
     * @author gxjh2
     * @date 2024/12/24 10:56:00
    */
    void addArticle(Article article);

    /**
     * 查询文章列表
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @param state
     * @return com.lv.pojo.PageBean<com.lv.pojo.Article>
     * @author gxjh2
     * @date 2024/12/24 11:41:00
    */
    PageBean<Article> queryArticleList(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    /**
     * 获取文章详情
     * @param id
     * @return com.lv.pojo.Article
     * @author gxjh2
     * @date 2024/12/24 15:52:18
    */
    Article articleDetail(Integer id);

    /**
     * 修改文章
     * @param article
     * @return void
     * @author gxjh2
     * @date 2024/12/24 16:18:47
    */
    void updateArticle(Article article);

    /**
     * 删除文章
     * @param id
     * @return void
     * @author gxjh2
     * @date 2024/12/24 16:59:05
    */
    void deleteArticle(Integer id);
}
