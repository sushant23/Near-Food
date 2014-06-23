package com.tr.nearfood.model;

public class Address {
	private String resturantAddressInfo;
	private long resturantLatitude;
	private long resturantLongitude;

	public String getResturantAddressInfo() {
		return resturantAddressInfo;
	}

	public void setResturantAddressInfo(String resturantAddressInfo) {
		this.resturantAddressInfo = resturantAddressInfo;
	}

	public long getResturantLatitude() {
		return resturantLatitude;
	}

	public void setResturantLatitude(long resturantLatitude) {
		this.resturantLatitude = resturantLatitude;
	}

	public long getResturantLongitude() {
		return resturantLongitude;
	}

	public void setResturantLongitude(long resturantLongitude) {
		this.resturantLongitude = resturantLongitude;
	}
}
