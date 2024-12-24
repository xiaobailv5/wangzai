package com.lv.mapper;

import com.lv.pojo.Article;

import java.util.List;

public interface ArticleMapper {


    void addArticle(Article article);

    List<Article> queryArticleList(Integer categoryId, String state, Integer userId);

    Article articleDetail(Integer id);

    void updateArticle(Article article);

    void deleteArticle(Integer id);
}
