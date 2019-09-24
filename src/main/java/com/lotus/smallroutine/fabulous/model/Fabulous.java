package com.lotus.smallroutine.fabulous.model;

import com.lotus.core.base.basemodel.BaseModel;

public class Fabulous extends BaseModel {

	private static final long serialVersionUID = 4552599988801950253L;

	private Long serviceId;

    private Integer type;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	@Override
	public String toString() {
		return "Fabulous [serviceId=" + serviceId + ", type=" + type + "] EXTENDS "+super.toString();
	}
    
}