package com.lotus.backstage.integral.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lotus.backstage.integral.model.Integral;

public interface IntegralMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Integral record);

    List<Integral> selectIntegral(Integral integral);

    int updateByPrimaryKeySelective(Integral record);
    
    int todayShareNewsCountByCategory(@Param("beginTime") Date beginTime,
    		@Param("createBy")Long createBy,
    		@Param("categoryType")String categoryType);

	List<Integral> selectIntegrals(Integral integral);
	
	Map<String,Object> selectIntegralDetail(@Param("createBy") Long createBy);
	
	Integer selectIntegralSum(@Param("createBy") Long createBy);
}