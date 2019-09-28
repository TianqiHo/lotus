package com.lotus.backstage.order.model;

import com.lotus.core.base.basemodel.BaseModel;

public class OrderStatusDeleteBackup extends BaseModel{

	private static final long serialVersionUID = -7695705548006689643L;

	private Long orderId;

    private Integer status;

    public OrderStatusDeleteBackup() {}
    
    public OrderStatusDeleteBackup(OrderStatus os) {
    	this.status = os.getStatus();
    }
    
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}