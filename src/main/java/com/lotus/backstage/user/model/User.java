package com.lotus.backstage.user.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lotus.core.base.basemodel.BaseModel;
import com.lotus.core.base.constants.Constants.LoginType;

public class User  extends BaseModel{
	private static final long serialVersionUID = 5067405672747417201L;

    private String userName;

    private String mobile;

    private String headPortrait;

    private String address;

    private String nickName;

    private String occupation;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    private Integer sex;

    private String briefIntroduction;

    private String geographicalPosition;

    private String passWord;

    private String idCard;
    
    private LoginType loginType = LoginType.LG_NAME_PASS;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
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

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", mobile=" + mobile + ", headPortrait=" + headPortrait + ", address="
				+ address + ", nickName=" + nickName + ", occupation=" + occupation + ", birthday=" + birthday
				+ ", sex=" + sex + ", briefIntroduction=" + briefIntroduction + ", geographicalPosition="
				+ geographicalPosition + ", passWord=" + passWord + ", idCard=" + idCard + ", loginType=" + loginType
				+ "] EXTENDS "+super.toString();
	}

	
}