package com.lv.services.impl;

import com.lv.mapper.CategoryMapper;
import com.lv.pojo.Category;
import com.lv.services.CategoryService;
import com.lv.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @projectName: wangzai
 * @package: com.lv.services.impl
 * @className: CategoryServiceImpl
 * @author: dus
 * @description:
 * @date: 2024/12/23 17:32
 * @version: 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public void addCategory(Category category) {

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("userId");
        category.setCreateUser(userId);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.addCategory(category);
    }

    @Override
    public List<Category> queryCategoryList() {

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("userId");
        List<Category> categoryList = categoryMapper.queryCategoryList(userId);
        return categoryList;
    }

    @Override
    public Category categoryDetail(Integer id) {
        Category category = categoryMapper.categoryDetail(id);
        return category;
    }

    @Override
    public void updateCategory(Category category) {

        categoryMapper.updateCategory(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryMapper.deleteCategory(id);
    }


}
