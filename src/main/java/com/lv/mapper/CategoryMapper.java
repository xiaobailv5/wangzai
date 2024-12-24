package com.lv.mapper;

import com.lv.pojo.Category;

import java.util.List;

public interface CategoryMapper {

    void addCategory(Category category);

    List<Category> queryCategoryList(Integer userId);

    Category categoryDetail(Integer id);

    void updateCategory(Category category);

    void deleteCategory(Integer id);
}
