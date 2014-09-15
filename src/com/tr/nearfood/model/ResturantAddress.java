package com.tr.nearfood.model;

public class ResturantAddress {
	private String resturantStreetAddress;
	private String returantCityName;
	private long resturantLatitude;
	private long resturantLongitude;
	private String resturantDistance;

	

	public String getResturantDistance() {
		return resturantDistance;
	}

	public void setResturantDistance(String resturantDistance) {
		this.resturantDistance = resturantDistance;
	}

	public String getResturantStreetAddress() {
		return resturantStreetAddress;
	}

	public void setResturantStreetAddress(String resturantStreetAddress) {
		this.resturantStreetAddress = resturantStreetAddress;
	}

	public String getReturantCityName() {
		return returantCityName;
	}

	public void setReturantCityName(String returantCityName) {
		this.returantCityName = returantCityName;
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
