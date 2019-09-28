package com.lotus.backstage.logistics.mapper;

import com.lotus.backstage.logistics.model.LogisticsDeleteBackup;

public interface LogisticsDeleteBackupMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(LogisticsDeleteBackup record);

    LogisticsDeleteBackup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LogisticsDeleteBackup record);

}