package com.lotus.backstage.news.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lotus.backstage.news.model.News;
import com.lotus.smallroutine.follow.model.Follow;

public interface NewsMapper {
	
    int deleteByPrimaryKey(Long id);

    Long insertSelective(News record);

    News selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(News record);

	List<News> selectNwess(News news);

	int seeSum(News news);
	
	List<News> selectClientNewss(News news);
	
	List<News> selectMyFollows(Follow follow);
	
	News selectNewsAndIsFollow(@Param("createBy") Long createBy,@Param("id") Long id);
}