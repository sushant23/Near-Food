package com.tr.nearfood.model;

public class ItemMenuDTO {
	private long itemID;
	private String itemName;
	private long itemPrice;
	private long itemCatagoryID;

	public long getItemID() {
		return itemID;
	}

	public void setItemID(long itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public long getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(long itemPrice) {
		this.itemPrice = itemPrice;
	}

	public long getItemCatagoryID() {
		return itemCatagoryID;
	}

	public void setItemCatagoryID(long itemCatagoryID) {
		this.itemCatagoryID = itemCatagoryID;
	}
}
