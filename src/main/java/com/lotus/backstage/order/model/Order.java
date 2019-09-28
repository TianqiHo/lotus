package com.lotus.backstage.order.model;

import com.lotus.core.base.basemodel.BaseModel;

public class Order extends BaseModel{

	private static final long serialVersionUID = 6134048073703620447L;

	private Long productId;

    private String orderCode;

    private Long currentStateId;

    private Long addressId;
    
    private Integer transactionPrice;
    
    private String statusName;
    
    private Integer status;
    
    private String logisticsCode;
    
    private String productName;
    
    private String productIntegralPrice;
    
    private String productImg;
    
    public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductIntegralPrice() {
		return productIntegralPrice;
	}

	public void setProductIntegralPrice(String productIntegralPrice) {
		this.productIntegralPrice = productIntegralPrice;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public String getLogisticsCode() {
		return logisticsCode;
	}

	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getTransactionPrice() {
		return transactionPrice;
	}

	public void setTransactionPrice(Integer transactionPrice) {
		this.transactionPrice = transactionPrice;
	}

	public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public Long getCurrentStateId() {
        return currentStateId;
    }

    public void setCurrentStateId(Long currentStateId) {
        this.currentStateId = currentStateId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}