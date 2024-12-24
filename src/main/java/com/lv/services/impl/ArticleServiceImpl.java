package com.lv.services.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lv.mapper.ArticleMapper;
import com.lv.pojo.Article;
import com.lv.pojo.PageBean;
import com.lv.services.ArticleService;
import com.lv.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @projectName: wangzai
 * @package: com.lv.services.impl
 * @className: ArticleServiceImpl
 * @author: dus
 * @description:
 * @date: 2024/12/24 10:44
 * @version: 1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void addArticle(Article article) {

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("userId");
        article.setCreateUser(userId);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.addArticle(article);

    }

    @Override
    public PageBean<Article> queryArticleList(Integer pageNum, Integer pageSize, Integer categoryId, String state) {

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("userId");


        PageBean<Article> articlePageBean = new PageBean<>();

        PageHelper.startPage(pageNum, pageSize);

        List<Article> articleList =articleMapper.queryArticleList(categoryId, state, userId);
        Page<Article> page = (Page<Article>) articleList;

        articlePageBean.setTotal(page.getTotal());
        articlePageBean.setItems(page.getResult());
        return articlePageBean;
    }

    @Override
    public Article articleDetail(Integer id) {

        Article article = articleMapper.articleDetail(id);
        return article;
    }

    @Override
    public void updateArticle(Article article) {

        article.setUpdateTime(LocalDateTime.now());
        articleMapper.updateArticle(article);
    }

    @Override
    public void deleteArticle(Integer id) {

        articleMapper.deleteArticle(id);
    }


}
