package com.lv.controller;

import com.lv.pojo.Category;
import com.lv.pojo.Result;
import com.lv.services.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: wangzai
 * @package: com.lv.controller
 * @className: CategoryController
 * @author: dus
 * @description: 文章分类控制类
 * @date: 2024/12/23 17:09
 * @version: 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增文章分类
     * @param category
     * @return com.lv.pojo.Result
     * @author gxjh2
     * @date 2024/12/23 17:14:03
    */
    @PostMapping("/addCategory")
    public Result addCategory(@Validated(Category.Add.class) @RequestBody Category category) {

        categoryService.addCategory(category);

        return Result.success();
    }

    /**
     * 查询文章分类列表
     * @return com.lv.pojo.Result<java.util.List<com.lv.pojo.Category>>
     * @author gxjh2
     * @date 2024/12/24 09:06:36
    */
    @GetMapping("/queryCategoryList")
    public Result<List<Category>> queryCategoryList() {

        List<Category> categoryList = categoryService.queryCategoryList();

        return Result.success(categoryList);
    }

    /**
     * 获取文章分类详情
     * @param id
     * @return com.lv.pojo.Result<com.lv.pojo.Category>
     * @author gxjh2
     * @date 2024/12/24 09:50:19
    */
    @GetMapping("/categoryDetail")
    public Result<Category> categoryDetail(@NotNull(message = "分类id不能为空") Integer id){

        Category category = categoryService.categoryDetail(id);

        return Result.success(category);
    }

    /**
     * 修改文章分类
     * @param category
     * @return com.lv.pojo.Result
     * @author gxjh2
     * @date 2024/12/24 09:50:19
    */
    @PutMapping("/updateCategory")
    public Result updateCategory(@Validated(Category.Update.class) @RequestBody Category category) {

        categoryService.updateCategory(category);

        return Result.success();
    }

    /**
     * 删除文章分类
     * @param id
     * @return com.lv.pojo.Result
     * @author gxjh2
     * @date 2024/12/24 10:08:02
    */
    @DeleteMapping("/deleteCategory")
    public Result deleteCategory(@NotNull(message = "分类id不能为空") Integer id) {

        categoryService.deleteCategory(id);

        return Result.success();
    }
}
