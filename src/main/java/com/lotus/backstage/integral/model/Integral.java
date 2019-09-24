package com.lotus.backstage.integral.model;

import com.lotus.core.base.basemodel.BaseModel;

public class Integral extends BaseModel{
  
	private static final long serialVersionUID = 4998781794063961263L;

	private Integer integral;

    private Long integralTypeId;
    
    private Long serviceId;
    
    private String integralName;
    
    
	public String getIntegralName() {
		return integralName;
	}

	public void setIntegralName(String integralName) {
		this.integralName = integralName;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

	public Long getIntegralTypeId() {
		return integralTypeId;
	}

	public void setIntegralTypeId(Long integralTypeId) {
		this.integralTypeId = integralTypeId;
	}

}