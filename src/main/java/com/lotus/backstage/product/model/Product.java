package com.lotus.backstage.product.model;

import com.lotus.core.base.basemodel.BaseModel;

public class Product extends BaseModel {

	private static final long serialVersionUID = 3792391339044472854L;

	private String productName;

    private Long productCategoryId;

    private String productImg;

    private Integer productIntegralPrice;

    private String productIntroduction;

    private String describeImg;

    private Integer productRepertoryNum;
    
    private String categoryName;

    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg == null ? null : productImg.trim();
    }

    public Integer getProductIntegralPrice() {
        return productIntegralPrice;
    }

    public void setProductIntegralPrice(Integer productIntegralPrice) {
        this.productIntegralPrice = productIntegralPrice;
    }

    public String getProductIntroduction() {
        return productIntroduction;
    }

    public void setProductIntroduction(String productIntroduction) {
        this.productIntroduction = productIntroduction == null ? null : productIntroduction.trim();
    }

    public String getDescribeImg() {
        return describeImg;
    }

    public void setDescribeImg(String describeImg) {
        this.describeImg = describeImg == null ? null : describeImg.trim();
    }

    public Integer getProductRepertoryNum() {
        return productRepertoryNum;
    }

    public void setProductRepertoryNum(Integer productRepertoryNum) {
        this.productRepertoryNum = productRepertoryNum;
    }
}