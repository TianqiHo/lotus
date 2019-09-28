package com.lotus.backstage.order.model;

import com.lotus.core.base.basemodel.BaseModel;

public class OrderDeleteBackup extends BaseModel{

	private static final long serialVersionUID = 5959691672819842696L;

	private Long productId;

    private String orderCode;

    private Long currentStateId;

    private Long addressId;

    public OrderDeleteBackup() {}
    
    public OrderDeleteBackup(Order order) {
    	this.productId = order.getProductId();
    	this.orderCode = order.getOrderCode();
    	//this.currentStateId = order.getCurrentStateId();
    	this.addressId = order.getAddressId();
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