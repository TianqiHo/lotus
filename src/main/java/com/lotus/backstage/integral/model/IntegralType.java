package com.lotus.backstage.integral.model;


import com.lotus.core.base.basemodel.BaseModel;

public class IntegralType extends BaseModel{

	private static final long serialVersionUID = -2534627953306097654L;

	private Integer integralNum;

    private Long integralType;

    private String integralName;
    
    private Integer integralDailyCeilingNum;
    
    private String integrayTypeName;

    public String getIntegrayTypeName() {
		return integrayTypeName;
	}

	public void setIntegrayTypeName(String integrayTypeName) {
		this.integrayTypeName = integrayTypeName;
	}

	public Integer getIntegralDailyCeilingNum() {
		return integralDailyCeilingNum;
	}

	public void setIntegralDailyCeilingNum(Integer integralDailyCeilingNum) {
		this.integralDailyCeilingNum = integralDailyCeilingNum;
	}

	public Integer getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(Integer integralNum) {
        this.integralNum = integralNum;
    }

    public Long getIntegralType() {
        return integralType;
    }

    public void setIntegralType(Long integralType) {
        this.integralType = integralType;
    }

    public String getIntegralName() {
        return integralName;
    }

    public void setIntegralName(String integralName) {
        this.integralName = integralName == null ? null : integralName.trim();
    }

	@Override
	public String toString() {
		return "IntegralType [integralNum=" + integralNum + ", integralType=" + integralType + ", integralName="
				+ integralName + ", integralDailyCeilingNum=" + integralDailyCeilingNum + ", integrayTypeName="
				+ integrayTypeName + "] EXTENDS "+super.toString();
	}
    
    
}