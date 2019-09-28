package com.lotus.backstage.order.model;

import com.lotus.core.base.basemodel.BaseModel;

public class OrderStatus extends BaseModel {

	private static final long serialVersionUID = 6160209715860897265L;

	private Long orderId;

    private Integer status;

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