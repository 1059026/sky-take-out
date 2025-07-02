package com.sky.controller.category;

import com.sky.dto.CategoryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j

public class CategoryController {
    @Autowired
    private com.sky.service.CategoryService categoryService;
    @GetMapping("/admin/category/page")
    @ApiOperation(value="查询分类列表")
    public Result getCategoryList(String name, Integer type, Integer page, Integer pageSize){
        PageResult list = categoryService.getCategoryList(name,type,page,pageSize);
        return Result.success(list);
    }
    @PostMapping("/admin/category")
    @ApiOperation(value="新增菜品分类")
    public Result<String> addCategory(@RequestBody CategoryDTO category){
        categoryService.addCategory(category);
        return Result.success("操作成功");
    }

    @DeleteMapping("/admin/category")
    @ApiOperation(value="删除菜品分类")
    public Result<String> deleteCategory(@RequestParam Long id){
        categoryService.deleteCategory(id);
        return Result.success("操作成功");
    }

    @PostMapping("/admin/category/status/{status}")
    @ApiOperation(value="启用/禁用菜品分类")
    public Result<String> updateCategoryStatus(@PathVariable Integer status,@RequestParam Long id){
        categoryService.updateCategoryStatus(status,id);
        return Result.success("操作成功");
    }

    @PutMapping("/admin/category")
    @ApiOperation(value="修改菜品分类")
    public Result<String> updateCategory(@RequestBody CategoryDTO category){
        categoryService.updateCategory(category);
        return Result.success("操作成功");
    }
}
