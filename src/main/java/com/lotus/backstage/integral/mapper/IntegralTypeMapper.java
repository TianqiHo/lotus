package com.lotus.backstage.integral.mapper;

import java.util.List;

import com.lotus.backstage.integral.model.IntegralType;

public interface IntegralTypeMapper {
    int deleteByPrimaryKey(Long id);


    int insertSelective(IntegralType record);

    IntegralType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IntegralType record);
    
	List<IntegralType> selectIntegralTypes(IntegralType integralType);
}