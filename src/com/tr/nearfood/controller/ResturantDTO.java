package com.tr.nearfood.controller;

public class ResturantDTO {
	private long resturantID;
	private String resturantName;
	private Address resturantAddress;
	private ContactInfo resturantContactInfo;
	private AvaliableServices resturantavaliableServices;
	private int resturantRating;
	private String resturantReview;

	public long getResturantID() {
		return resturantID;
	}

	public void setResturantID(long resturantID) {
		this.resturantID = resturantID;
	}

	public String getResturantName() {
		return resturantName;
	}

	public void setResturantName(String resturantName) {
		this.resturantName = resturantName;
	}

	public Address getResturantAddress() {
		return resturantAddress;
	}

	public void setResturantAddress(Address resturantAddress) {
		this.resturantAddress = resturantAddress;
	}

	public ContactInfo getResturantContactInfo() {
		return resturantContactInfo;
	}

	public void setResturantContactInfo(ContactInfo resturantContactInfo) {
		this.resturantContactInfo = resturantContactInfo;
	}

	public AvaliableServices getResturantavaliableServices() {
		return resturantavaliableServices;
	}

	public void setResturantavaliableServices(
			AvaliableServices resturantavaliableServices) {
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
}
