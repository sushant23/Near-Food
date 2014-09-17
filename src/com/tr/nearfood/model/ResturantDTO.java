package com.tr.nearfood.model;

public class ResturantDTO {
	private int resturantID;
	private String resturantName;
	private ResturantAddress resturantAddress;
	private ResturantContactInfo resturantContactInfo;
	private ResturantAvaliableServices resturantavaliableServices;
	private int resturantRating;
	private String resturantReview;
	private String dataTime;
	private Boolean regisrered;
	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public int getResturantID() {
		return resturantID;
	}

	public void setResturantID(int resturantID) {
		this.resturantID = resturantID;
	}

	public String getResturantName() {
		return resturantName;
	}

	public void setResturantName(String resturantName) {
		this.resturantName = resturantName;
	}

	public ResturantAddress getResturantAddress() {
		return resturantAddress;
	}

	public void setResturantAddress(ResturantAddress resturantAddress) {
		this.resturantAddress = resturantAddress;
	}

	public ResturantContactInfo getResturantContactInfo() {
		return resturantContactInfo;
	}

	public void setResturantContactInfo(
			ResturantContactInfo resturantContactInfo) {
		this.resturantContactInfo = resturantContactInfo;
	}

	public ResturantAvaliableServices getResturantavaliableServices() {
		return resturantavaliableServices;
	}

	public void setResturantavaliableServices(
			ResturantAvaliableServices resturantavaliableServices) {
		this.resturantavaliableServices = resturantavaliableServices;
	}

	public int getResturantRating() {
		return resturantRating;
	}

	public void setResturantRating(int resturantRating) {
		this.resturantRating = resturantRating;
	}

	public String getResturantReview() {
		return resturantReview;
	}

	public void setResturantReview(String resturantReview) {
		this.resturantReview = resturantReview;
	}

	public Boolean getRegisrered() {
		return regisrered;
	}

	public void setRegisrered(Boolean regisrered) {
		this.regisrered = regisrered;
	}
}
