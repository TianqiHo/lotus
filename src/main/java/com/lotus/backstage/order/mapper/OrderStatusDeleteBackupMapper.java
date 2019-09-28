package com.lotus.backstage.order.mapper;

import com.lotus.backstage.order.model.OrderStatusDeleteBackup;

public interface OrderStatusDeleteBackupMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(OrderStatusDeleteBackup record);

    OrderStatusDeleteBackup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderStatusDeleteBackup record);

}