package com.lotus.backstage.logistics.model;

import com.lotus.core.base.basemodel.BaseModel;

public class LogisticsDeleteBackup extends BaseModel{

	private static final long serialVersionUID = -664609479428622597L;

	private Long orderId;

    private String expressCompanyLogo;

    private String logisticsCode;

    private String expressCompanyName;

    public LogisticsDeleteBackup() {
		
	}
    
    public LogisticsDeleteBackup(Logistics logistics) {
		this.expressCompanyLogo = logistics.getExpressCompanyLogo();
		this.expressCompanyName = logistics.getExpressCompanyName();
		this.logisticsCode = logistics.getLogisticsCode();
   	}

	public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getExpressCompanyLogo() {
        return expressCompanyLogo;
    }

    public void setExpressCompanyLogo(String expressCompanyLogo) {
        this.expressCompanyLogo = expressCompanyLogo == null ? null : expressCompanyLogo.trim();
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode == null ? null : logisticsCode.trim();
    }

    public String getExpressCompanyName() {
        return expressCompanyName;
    }

    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName == null ? null : expressCompanyName.trim();
    }
}