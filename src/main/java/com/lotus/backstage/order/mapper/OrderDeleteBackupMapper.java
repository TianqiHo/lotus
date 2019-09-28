package com.lotus.backstage.order.mapper;

import com.lotus.backstage.order.model.OrderDeleteBackup;

public interface OrderDeleteBackupMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(OrderDeleteBackup record);

    OrderDeleteBackup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderDeleteBackup record);

}