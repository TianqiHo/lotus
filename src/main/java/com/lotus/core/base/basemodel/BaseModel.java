package com.lotus.core.base.basemodel;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(value = {"log","version"})
@JsonPropertyOrder(alphabetic=true)
public class BaseModel implements Serializable{

	private static final long serialVersionUID = -8817177052730721731L;

	private Long id;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	
	private Long createBy;
	
	private String createByName;
	
	private Long updateBy;
	
	private String updateByName;
	
	private String log;
	
	/**
	 * 1有效，2无效 
	 */
	private Integer valid;
	
	/**
	 * 从1递增
	 */
	
	private Integer version;
	
	
	/*************************************分页**************************************************/
	
    private Integer pageNumber;

    private Integer pageSize;

    /**
    private Long totalSize;

    private Integer totalPages;
    
    private List<T> contents; */   
    
    private String ids;
    
    public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getUpdateByName() {
		return updateByName;
	}

	public void setUpdateByName(String updateByName) {
		this.updateByName = updateByName;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    
    //@JsonFilter("usePagenation")
    private boolean usePagenation=true;

    
	public boolean isUsePagenation() {
		return usePagenation;
	}

	public void setUsePagenation(boolean usePagenation) {
		this.usePagenation = usePagenation;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void buildCreateDefaultValue() {
		this.setCreateTime(new Date());
		this.setLog("通过buildCreateDefaultValue()插入");
		this.setValid(1);
		this.setVersion(1);
	}
	
	public void buildUpdateDefaultValue() {
		this.setUpdateTime(new Date());
		//this.setUpdateBy(this.id);
		this.setLog("通过buildUpdateDefaultValue()插入");
		//this.setValid(1);
		//this.setVersion(1);
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "BaseModel [id=" + id + ", createBy=" + createBy + ", updateBy=" + updateBy + ", valid=" + valid
				+ ", pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", ids=" + ids + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + ", usePagenation=" + usePagenation + "]";
	}
}
