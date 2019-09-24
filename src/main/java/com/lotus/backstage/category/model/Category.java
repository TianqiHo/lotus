package com.lotus.backstage.category.model;

import com.lotus.core.base.basemodel.BaseModel;

public class Category extends BaseModel{
   
	private static final long serialVersionUID = -1073731976825812629L;

	private String categoryName;

    private String categoryType;
    
    private String categoryImg;
    
    private Integer newsCount;

	public Integer getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(Integer newsCount) {
		this.newsCount = newsCount;
	}

	public String getCategoryImg() {
		return categoryImg;
	}

	public void setCategoryImg(String categoryImg) {
		this.categoryImg = categoryImg;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	@Override
	public String toString() {
		return "Category [categoryName=" + categoryName + ", categoryType=" + categoryType + "] EXTENDS "+super.toString();
	}
}