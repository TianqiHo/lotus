package com.lotus.backstage.comment.model;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lotus.core.base.basemodel.BaseModel;

public class Comment extends BaseModel {

	private static final long serialVersionUID = -1406689414673996374L;

	private Long articalId;

    private String categoryType;
    
    @JsonIgnore
    private byte[] sayWhat1;
    
    private String sayWhat;
    
    private Integer status;
    
    private Integer fabulousSum;
    
    private Long fabulousId;
    
    private Long examineBy;
    
    private String examineByName;
    
    private Date examineTime;
    

    private Long integralTypeId;
    
    private String headPortrait;
    
    
	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public Long getIntegralTypeId() {
		return integralTypeId;
	}

	public void setIntegralTypeId(Long integralTypeId) {
		this.integralTypeId = integralTypeId;
	}

	public String getExamineByName() {
		return examineByName;
	}

	public void setExamineByName(String examineByName) {
		this.examineByName = examineByName;
	}

	public Date getExamineTime() {
		return examineTime;
	}

	public void setExamineTime(Date examineTime) {
		this.examineTime = examineTime;
	}

	public Long getExamineBy() {
		return examineBy;
	}

	public void setExamineBy(Long examineBy) {
		this.examineBy = examineBy;
	}

	public Long getFabulousId() {
		return fabulousId;
	}

	public void setFabulousId(Long fabulousId) {
		this.fabulousId = fabulousId;
	}

	public Integer getFabulousSum() {
		return fabulousSum;
	}

	public void setFabulousSum(Integer fabulousSum) {
		this.fabulousSum = fabulousSum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getArticalId() {
        return articalId;
    }

    public void setArticalId(Long articalId) {
        this.articalId = articalId;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType == null ? null : categoryType.trim();
    }

	public byte[] getSayWhat1() {
		if(!StringUtils.isEmpty(this.sayWhat)) {
			return this.sayWhat.getBytes(Charset.defaultCharset());
		}
		return sayWhat1;
	}

	public void setSayWhat1(byte[] sayWhat1) {
		if(!StringUtils.isEmpty(this.sayWhat)) {
			this.sayWhat1 = this.sayWhat.getBytes(Charset.defaultCharset());
		}else if(!StringUtils.isEmpty(sayWhat1)) {
			this.sayWhat1 = sayWhat1;
		}
	}

	public String getSayWhat() {
		if(!StringUtils.isEmpty(this.sayWhat1)){
			return new String(this.sayWhat1, Charset.defaultCharset());
		}
		return sayWhat;
	}

	public void setSayWhat(String sayWhat) {
		if(!StringUtils.isEmpty(this.sayWhat1)){
			this.sayWhat =  new String(this.sayWhat1, Charset.defaultCharset());
		}else if(!StringUtils.isEmpty(sayWhat)) {
			this.sayWhat = sayWhat;
		}
	}

	@Override
	public String toString() {
		return "Comment [articalId=" + articalId + ", categoryType=" + categoryType + ", sayWhat1="
				+ Arrays.toString(sayWhat1) + ", sayWhat=" + sayWhat + ", status=" + status + ", fabulousSum="
				+ fabulousSum + ", fabulousId=" + fabulousId + ", examineBy=" + examineBy + ", examineByName="
				+ examineByName + ", examineTime=" + examineTime + ", integralTypeId=" + integralTypeId
				+ ", headPortrait=" + headPortrait + "] EXTENDS "+super.toString();
	}
}