package com.tr.nearfood.model;

import java.util.List;

public class MigratingDatas {

	private List<Integer> confirmedOrderList;
	private int restaurant_id;

	public int getRestaurant_id() {
		return restaurant_id;
	}

	public void setRestaurant_id(int restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public List<Integer> getConfirmedOrderList() {
		return confirmedOrderList;
	}

	public void setConfirmedOrderList(List<Integer> confirmedOrderList) {
		this.confirmedOrderList = confirmedOrderList;
	}

}
