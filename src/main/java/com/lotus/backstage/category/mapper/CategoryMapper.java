package com.lotus.backstage.category.mapper;

import java.util.List;

import com.lotus.backstage.category.model.Category;

public interface CategoryMapper{
    int deleteByPrimaryKey(Long id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

	List<Category> selectCategorys(Category categroy);
}