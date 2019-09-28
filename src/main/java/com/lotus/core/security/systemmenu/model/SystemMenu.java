package com.lotus.core.security.systemmenu.model;

import java.util.List;

import com.lotus.core.base.basemodel.BaseModel;

public class SystemMenu extends BaseModel{

	private static final long serialVersionUID = 3995339172134017259L;

	private Long pId;

    private String name;

    private String actionUrl;

    private String resourceUrl;

    private List<SystemMenu> childSystemMenus;
    
    
    public List<SystemMenu> getChildSystemMenus() {
		return childSystemMenus;
	}

	public void setChildSystemMenus(List<SystemMenu> childSystemMenus) {
		this.childSystemMenus = childSystemMenus;
	}

	public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl == null ? null : actionUrl.trim();
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl == null ? null : resourceUrl.trim();
    }
}