package com.lv.services;

import com.lv.pojo.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 新增文章分类
     * @param category
     * @return void
     * @author gxjh2
     * @date 2024/12/23 17:36:23
    */
    void addCategory(Category category);

    /**
     * 查询文章分类列表
     * @return java.util.List<com.lv.pojo.Category>
     * @author gxjh2
     * @date 2024/12/24 09:07:50
    */
    List<Category> queryCategoryList();

    /**
     * 获取文章分类详情
     * @param id
     * @return com.lv.pojo.Category
     * @author gxjh2
     * @date 2024/12/24 09:50:41
    */
    Category categoryDetail(Integer id);

    /**
     * 更新文章分类
     * @param category 
     * @return void
     * @author gxjh2
     * @date 2024/12/24 10:02:05
    */
    void updateCategory(Category category);

    /**
     * 删除文章分类
     * @param id
     * @return void
     * @author gxjh2
     * @date 2024/12/24 10:08:20
    */
    void deleteCategory(Integer id);
}
