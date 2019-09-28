package com.lotus.backstage.logistics.model;

import com.lotus.core.base.basemodel.BaseModel;

public class Logistics extends BaseModel {

	private static final long serialVersionUID = 1090267431003610848L;

	private Long orderId;

    private String expressCompanyLogo;

    private String logisticsCode;

    private String expressCompanyName;

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