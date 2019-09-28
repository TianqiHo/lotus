package com.lotus.backstage.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lotus.backstage.product.model.Product;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    /**
     * 查询客户端商品
     * @param product
     * @return
     */
	List<Product> selectClientProducts(Product product);
	
	int downProductRepertoryNum(@Param("id")Long id);

}