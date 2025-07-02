package com.sky.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageResult getCategoryList(String name, Integer type, Integer page, Integer pageSize){
        PageHelper.startPage(page,pageSize);
        List<Category> list = categoryMapper.getCategoryList(name, type);
        PageInfo<Category> pageInfo = new PageInfo<>(list);
        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getList());
        return pageResult;
    }
    @Override
    public void addCategory(CategoryDTO category) {
        Category c = new Category();
        BeanUtils.copyProperties(category,c);
        c.setCreateTime(LocalDateTime.now());
        c.setUpdateTime(LocalDateTime.now());
        c.setStatus(1);
        Long userId = BaseContext.getCurrentId();
        c.setCreateUser(userId);
        c.setUpdateUser(userId);
        categoryMapper.addCategory(c);
    }

    @Override
    public void deleteCategory(Long id) {
        // TODO 菜品写好以后，记得在此处添加菜品关联的判断
        categoryMapper.deleteCategory(id);
    }

    @Override
    public void updateCategoryStatus(Integer status, Long id) {
        Category category = categoryMapper.getById(id);
        category.setStatus(status);
        categoryMapper.updateCategory(category);
    }

    @Override
    public void updateCategory(CategoryDTO category) {
        Category c = new Category();
        BeanUtils.copyProperties(category,c);
        c.setUpdateTime(LocalDateTime.now());
        Long userId = BaseContext.getCurrentId();
        c.setUpdateUser(userId);
        categoryMapper.updateCategory(c);
    }
}
