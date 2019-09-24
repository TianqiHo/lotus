package com.lotus.smallroutine.customer.model;

import java.nio.charset.Charset;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lotus.core.base.basemodel.BaseModel;
import com.lotus.core.base.constants.Constants.LoginType;

public class Customer  extends BaseModel{
	private static final long serialVersionUID = 5067405672747417201L;

    private String userName;

    private String mobile;

    private String headPortrait;

    private String address;

    private String nickName;
    
    private byte[] nickName1;

    private String occupation;

	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    private Integer sex;

    private String briefIntroduction;

    private String geographicalPosition;

    private String passWord;

    private String idCard;
    
    private LoginType loginType;
    
    
    private String wxMiniProOpenId;
    
    private String wxMiniProAvatarUrl;
    
    private String wxMiniProUnionId;

	private String wxMiniProCode;
	
	//private String wxMiniProRawData;//用户非敏感信息
    private String wxMiniProsignature;//签名
    private String wxMiniProEncrypteData;//用户敏感信息
    private String wxMiniProIv;//解密算法的向量

    //积分类型ID
    private Long integralTypeId;

    
	public byte[] getNickName1() {
		if(!StringUtils.isEmpty(nickName)) {
		   return nickName.getBytes(Charset.defaultCharset());
		}else {
			return nickName1;
		}
		
	}

	public void setNickName1(byte[] nickName1) {
		if(!StringUtils.isEmpty(nickName)) {
			this.nickName1 = nickName.getBytes(Charset.defaultCharset());
		}else {
			this.nickName1 = nickName1;
		}
		
	}

   public String getNickName() {
		if(!StringUtils.isEmpty(nickName1)) {
			 return new String(nickName1,Charset.defaultCharset());
		}else {
			return nickName;
		}
        
    }

    public void setNickName(String nickName) {
    	if(!StringUtils.isEmpty(nickName1)) {
    		this.nickName = new String(nickName1,Charset.defaultCharset());
    	}else {
    		 this.nickName = nickName == null ? null : nickName.trim();
    	}
       
    }
	public Long getIntegralTypeId() {
		return integralTypeId;
	}

	public void setIntegralTypeId(Long integralTypeId) {
		this.integralTypeId = integralTypeId;
	}

	public String getWxMiniProsignature() {
		return wxMiniProsignature;
	}

	public String getWxMiniProAvatarUrl() {
		return wxMiniProAvatarUrl;
	}

	public void setWxMiniProAvatarUrl(String wxMiniProAvatarUrl) {
		this.wxMiniProAvatarUrl = wxMiniProAvatarUrl;
	}

	public void setWxMiniProsignature(String wxMiniProsignature) {
		this.wxMiniProsignature = wxMiniProsignature;
	}

	public String getWxMiniProEncrypteData() {
		return wxMiniProEncrypteData;
	}

	public void setWxMiniProEncrypteData(String wxMiniProEncrypteData) {
		this.wxMiniProEncrypteData = wxMiniProEncrypteData;
	}

	public String getWxMiniProIv() {
		return wxMiniProIv;
	}

	public void setWxMiniProIv(String wxMiniProIv) {
		this.wxMiniProIv = wxMiniProIv;
	}

	public String getWxMiniProOpenId() {
		return wxMiniProOpenId;
	}

	public void setWxMiniProOpenId(String wxMiniProOpenId) {
		this.wxMiniProOpenId = wxMiniProOpenId;
	}

	public String getWxMiniProUnionId() {
		return wxMiniProUnionId;
	}

	public void setWxMiniProUnionId(String wxMiniProUnionId) {
		this.wxMiniProUnionId = wxMiniProUnionId;
	}

	public String getWxMiniProCode() {
		return wxMiniProCode;
	}

	public void setWxMiniProCode(String wxMiniProCode) {
		this.wxMiniProCode = wxMiniProCode;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait == null ? null : headPortrait.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation == null ? null : occupation.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction == null ? null : briefIntroduction.trim();
    }

    public String getGeographicalPosition() {
        return geographicalPosition;
    }

    public void setGeographicalPosition(String geographicalPosition) {
        this.geographicalPosition = geographicalPosition == null ? null : geographicalPosition.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

	@Override
	public String toString() {
		return "Customer [userName=" + userName + ", mobile=" + mobile + ", headPortrait=" + headPortrait + ", address="
				+ address + ", nickName=" + nickName + ", occupation=" + occupation + ", birthday=" + birthday
				+ ", sex=" + sex + ", briefIntroduction=" + briefIntroduction + ", geographicalPosition="
				+ geographicalPosition + ", passWord=" + passWord + ", idCard=" + idCard + ", loginType=" + loginType
				+ ", wxMiniProOpenId=" + wxMiniProOpenId + ", wxMiniProAvatarUrl=" + wxMiniProAvatarUrl
				+ ", wxMiniProUnionId=" + wxMiniProUnionId + ", wxMiniProCode=" + wxMiniProCode
				+ ", wxMiniProsignature=" + wxMiniProsignature + ", wxMiniProEncrypteData=" + wxMiniProEncrypteData
				+ ", wxMiniProIv=" + wxMiniProIv + ", integralTypeId=" + integralTypeId + "] EXTENDS "+super.toString();
	}
}