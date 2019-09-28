package com.lotus.backstage.order.mapper;

import java.util.List;

import com.lotus.backstage.order.model.OrderStatus;

public interface OrderStatusMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(OrderStatus record);

    OrderStatus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderStatus record);
    
    int setValid2(OrderStatus record);
    
    List<OrderStatus> selectOrderStatus(OrderStatus orderStatus);

}