package com.lotus.backstage.news.service;

import com.lotus.backstage.news.model.News;
import com.lotus.core.base.returnmessage.Message;

public interface INewsService {
	
	Message saveOrUpdateNews(News news)throws Exception;
	
	Message selectNews(News news)throws Exception;
	
	Message selectNewss(News news)throws Exception;
	
	Message deleteByPrimaryKey(News news)throws Exception;

	Message deleteNewss(News news)throws Exception;
	
	Message seeSum(News news)throws Exception;

	Message shareNews(News news)throws Exception;
	
	Message selectClientNewss(News news)throws Exception;

	Message selectNewsAndIsFollow(News news) throws Exception;

	Message fabulousNews(News news)throws Exception;
}
