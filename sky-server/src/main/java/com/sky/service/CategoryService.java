package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.result.PageResult;


public interface CategoryService {
    PageResult getCategoryList( String name, Integer type, Integer page, Integer pageSize);
    void addCategory (CategoryDTO category);

    void deleteCategory(Long id);
    void updateCategoryStatus(Integer status, Long id);
    void updateCategory(CategoryDTO category);
}
