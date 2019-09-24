package com.lotus.backstage.news.mapper;

import java.util.List;

import com.lotus.backstage.news.model.News2Category;

public interface News2CategoryMapper {
    int deleteByPrimaryKey(Long id);

    int deleteByForeignKey(Long id);
    
    int insertSelective(News2Category record);

    List<News2Category> selectCategorys(News2Category record);

    int updateByPrimaryKeySelective(News2Category record);
}