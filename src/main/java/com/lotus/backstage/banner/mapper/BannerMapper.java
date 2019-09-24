package com.lotus.backstage.banner.mapper;

import java.util.List;

import com.lotus.backstage.banner.model.Banner;

public interface BannerMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Banner record);

    Banner selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Banner record);
    
    List<Banner> selectBanners(Banner record);
}