package com.tr.nearfood.controller;

public class AvaliableServices {
	private boolean isTakeAwayAvaliable;
	private boolean isDeliveryAvaliable;
	private boolean isReservationAvaliable;
	public boolean isTakeAwayAvaliable() {
		return isTakeAwayAvaliable;
	}
	public void setTakeAwayAvaliable(boolean isTakeAwayAvaliable) {
		this.isTakeAwayAvaliable = isTakeAwayAvaliable;
	}
	public boolean isDeliveryAvaliable() {
		return isDeliveryAvaliable;
	}
	public void setDeliveryAvaliable(boolean isDeliveryAvaliable) {
		this.isDeliveryAvaliable = isDeliveryAvaliable;
	}
	public boolean isReservationAvaliable() {
		return isReservationAvaliable;
	}
	public void setReservationAvaliable(boolean isReservationAvaliable) {
		this.isReservationAvaliable = isReservationAvaliable;
	}
	
	
}
