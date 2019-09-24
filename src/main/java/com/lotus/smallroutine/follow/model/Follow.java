package com.lotus.smallroutine.follow.model;

import com.lotus.core.base.basemodel.BaseModel;

public class Follow extends BaseModel {
   
	private static final long serialVersionUID = 287918682055216346L;

	private Long serciceId;

    private Integer followType;
    
    private String categoryType;


    public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public Long getSerciceId() {
        return serciceId;
    }

    public void setSerciceId(Long serciceId) {
        this.serciceId = serciceId;
    }

    public Integer getFollowType() {
        return followType;
    }

    public void setFollowType(Integer followType) {
        this.followType = followType;
    }

	@Override
	public String toString() {
		return "Follow [serciceId=" + serciceId + ", followType=" + followType + ", categoryType=" + categoryType + "] EXTENDS "+super.toString();
	}
}