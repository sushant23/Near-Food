package com.tr.nearfood.model;

public class CustomerOrdersDTO {

	int restaurant_id;
	String items_list;
	String status;
	public int getRestaurant_id() {
		return restaurant_id;
	}
	public void setRestaurant_id(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	public String getItems_list() {
		return items_list;
	}
	public void setItems_list(String items_list) {
		this.items_list = items_list;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
