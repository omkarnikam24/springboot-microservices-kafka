package com.amdocs.media.assignement.dto;

import java.io.Serializable;

/**
 * @author omkar.nikam24@gmail.com
 * @apiNote A Data Transfer Object for Profile
 */
public class ProfileDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;

	private String userName;

	private String address;

	private long phoneNumber;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ProfileDTO [type=" + type + ", userName=" + userName + ", address=" + address + ", phoneNumber="
				+ phoneNumber + "]";
	}
}
