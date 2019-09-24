package com.lotus.smallroutine.follow.mapper;

import java.util.List;

import com.lotus.smallroutine.follow.model.Follow;

public interface FollowMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Follow record);

    Follow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Follow record);
    
    List<Follow> selectFollows(Follow record);
}