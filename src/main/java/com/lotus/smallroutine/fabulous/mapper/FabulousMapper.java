package com.lotus.smallroutine.fabulous.mapper;

import java.util.List;

import com.lotus.smallroutine.fabulous.model.Fabulous;

public interface FabulousMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Fabulous record);

    Fabulous selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Fabulous record);

	List<Fabulous> selectFabulouss(Fabulous fabulous);
}