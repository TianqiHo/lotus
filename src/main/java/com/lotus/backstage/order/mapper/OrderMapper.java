package com.lotus.backstage.order.mapper;

import java.util.List;

import com.lotus.backstage.order.model.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

	List<Order> selectClientOrders(Order order);

}