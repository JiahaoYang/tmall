package com.yjh.service.impl;

import com.yjh.mapper.CategoryMapper;
import com.yjh.pojo.Category;
import com.yjh.pojo.CategoryExample;
import com.yjh.service.CategoryService;
import com.yjh.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;


/*    @Override
    public List<Category> list(Page page) {
        return categoryMapper.list(page);
    }

    @Override
    public int total() {
        return categoryMapper.total();
    }*/

    @Override
    public List<Category> list() {
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("id");
        return categoryMapper.selectByExample(example);
    }

    @Override
    public int add(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    public int delete(int id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Category category) {
        return categoryMapper.updateByPrimaryKeySelective(category);
    }
}
