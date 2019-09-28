package com.lotus.core.security.systemmenu.mapper;

import java.util.List;

import com.lotus.core.security.systemmenu.model.SystemMenu;

public interface SystemMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SystemMenu record);

    SystemMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemMenu record);

	List<SystemMenu> selectSystemMenus(SystemMenu systemMenu);
}