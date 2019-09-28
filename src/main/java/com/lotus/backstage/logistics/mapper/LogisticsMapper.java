package com.lotus.backstage.logistics.mapper;

import java.util.List;

import com.lotus.backstage.logistics.model.Logistics;

public interface LogisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Logistics record);

    Logistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Logistics record);

	List<Logistics> selectLogisticses(Logistics logistics);

}