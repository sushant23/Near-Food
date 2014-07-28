package com.tr.nearfood.model;

public class OrdersDto {
	int restaurant_id;
	String customer_name;
	String customer_address;
	String customer_mobile_number;
	String customer_email;
	OrderedItemDTO orders_items;

	public int getRestaurant_id() {
		return restaurant_id;
	}

	public void setRestaurant_id(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public String getCustomer_mobile_number() {
		return customer_mobile_number;
	}

	public void setCustomer_mobile_number(String customer_mobile_number) {
		this.customer_mobile_number = customer_mobile_number;
	}

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	public OrderedItemDTO getOrders_items() {
		return orders_items;
	}

	public void setOrders_items(OrderedItemDTO orders_items) {
		this.orders_items = orders_items;
	}

}
