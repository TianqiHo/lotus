package com.lotus.backstage.news.model;

import java.io.Serializable;

public class News2Category implements Serializable{
  
	private static final long serialVersionUID = 2031286191489595696L;

	private Long id;

    private Long newsId;

    private Long categoryId;
    
    private String categoryName;

    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}