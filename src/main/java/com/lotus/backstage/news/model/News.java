package com.lotus.backstage.news.model;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lotus.core.base.basemodel.BaseModel;

public class News extends BaseModel {
    
	private static final long serialVersionUID = 2936330516198937445L;

	private String newsTitle;

    private String newsImage;

    private String newsAuthor;

    private String newsFrom;

    private String newsTag;

    @JsonIgnore
    private byte[] newsContent;
    
    private String newsContent1;
    
    private List<News2Category> categorys;
    
    private String categoryNames;
    
    private Long categoryId;
    
    private String categoryType;
    
    private Integer status;
    
    private Integer seeSum;
    
    private Long integralTypeId;
    
    private Integer platForm;
    
    private Integer commentCount;
    
    private String headPortrait;
    
    private Long followId;
    
    private Integer fabulousSum;
    
    private Long fabulousNewsId;
    
	public Long getFabulousNewsId() {
		return fabulousNewsId;
	}

	public void setFabulousNewsId(Long fabulousNewsId) {
		this.fabulousNewsId = fabulousNewsId;
	}

	public Integer getFabulousSum() {
		return fabulousSum;
	}

	public void setFabulousSum(Integer fabulousSum) {
		this.fabulousSum = fabulousSum;
	}

	public Long getFollowId() {
		return followId;
	}

	public void setFollowId(Long followId) {
		this.followId = followId;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getPlatForm() {
		return platForm;
	}

	public void setPlatForm(Integer platForm) {
		this.platForm = platForm;
	}

	public Long getIntegralTypeId() {
		return integralTypeId;
	}

	public void setIntegralTypeId(Long integralTypeId) {
		this.integralTypeId = integralTypeId;
	}

	public Integer getSeeSum() {
		return seeSum;
	}

	public void setSeeSum(Integer seeSum) {
		this.seeSum = seeSum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getNewsContent1() {
    	if(null!=this.newsContent && this.newsContent.length!=0) {
    		return new String(this.newsContent,Charset.defaultCharset());
    	}
		return newsContent1;
	}

	public void setNewsContent1(String newsContent1) {
		if(null!=this.newsContent && this.newsContent.length!=0) {
			this.newsContent1 = new String(this.newsContent,Charset.defaultCharset());
		}else if(!StringUtils.isEmpty(newsContent1)) {
			this.newsContent1 = newsContent1;
		}
		
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(String categoryNames) {
		this.categoryNames = categoryNames;
	}

	public List<News2Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<News2Category> categorys) {
		this.categorys = categorys;
	}

	public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle == null ? null : newsTitle.trim();
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage == null ? null : newsImage.trim();
    }

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor == null ? null : newsAuthor.trim();
    }

    public String getNewsFrom() {
        return newsFrom;
    }

    public void setNewsFrom(String newsFrom) {
        this.newsFrom = newsFrom == null ? null : newsFrom.trim();
    }

    public String getNewsTag() {
        return newsTag;
    }

    public void setNewsTag(String newsTag) {
        this.newsTag = newsTag == null ? null : newsTag.trim();
    }
    
    public byte[] getNewsContent() {
    	if(!StringUtils.isEmpty(this.newsContent1)) {
    		return this.newsContent1.getBytes(Charset.defaultCharset());
    	}
        return newsContent;
    }

    public void setNewsContent(byte[] newsContent) {
    	if(!StringUtils.isEmpty(this.newsContent1)) {
    		this.newsContent = this.newsContent1.getBytes(Charset.defaultCharset());
    	}else if(!StringUtils.isEmpty(newsContent)) {
    		this.newsContent = newsContent;
    	}
    }

	@Override
	public String toString() {
		return "News [newsTitle=" + newsTitle + ", newsImage=" + newsImage + ", newsAuthor=" + newsAuthor
				+ ", newsFrom=" + newsFrom + ", newsTag=" + newsTag + ", newsContent=" + Arrays.toString(newsContent)
				+ ", newsContent1=" + newsContent1 + ", categorys=" + categorys + ", categoryNames=" + categoryNames
				+ ", categoryId=" + categoryId + ", categoryType=" + categoryType + ", status=" + status + ", seeSum="
				+ seeSum + ", integralTypeId=" + integralTypeId + ", platForm=" + platForm + ", commentCount="
				+ commentCount + ", headPortrait=" + headPortrait + "] EXTENDS "+super.toString();
	}
    
}