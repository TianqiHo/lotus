package com.lotus.backstage.banner.model;

import com.lotus.core.base.basemodel.BaseModel;

public class Banner extends BaseModel{
	
	private static final long serialVersionUID = -4564717824354452262L;

    private String bannerName;

    private String bannerImg;

    private Integer bannerOrder;

    private String targetUrl;

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName == null ? null : bannerName.trim();
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg == null ? null : bannerImg.trim();
    }

    public Integer getBannerOrder() {
        return bannerOrder;
    }

    public void setBannerOrder(Integer bannerOrder) {
        this.bannerOrder = bannerOrder;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl == null ? null : targetUrl.trim();
    }

	@Override
	public String toString() {
		return "Banner [bannerName=" + bannerName + ", bannerImg=" + bannerImg + ", bannerOrder=" + bannerOrder
				+ ", targetUrl=" + targetUrl + "] EXTENDS "+super.toString();
	}
}