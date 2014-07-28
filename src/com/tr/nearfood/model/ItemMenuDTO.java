package com.tr.nearfood.model;

public class ItemMenuDTO {
	private int id;
	private String itemName;
	private int itemPrice;
	private int itemCatagoryID;
	private int restaurants_id;
	private String is_active;
	private String created_at;
	private String updated_at;
	private Catagory itemCatagory;

	public ItemMenuDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}

	public int getItemCatagoryID() {
		return itemCatagoryID;
	}

	public void setItemCatagoryID(int itemCatagoryID) {
		this.itemCatagoryID = itemCatagoryID;
	}

	public int getRestaurants_id() {
		return restaurants_id;
	}

	public void setRestaurants_id(int restaurants_id) {
		this.restaurants_id = restaurants_id;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public Catagory getItemCatagory() {
		return itemCatagory;
	}

	public void setItemCatagory(Catagory itemCatagory) {
		this.itemCatagory = itemCatagory;
	}

}
