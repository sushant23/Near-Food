package com.tr.nearfood.model;

public class Catagory {

	private int id;
	private String CatagoryName;
	private String is_active;
	private String description;
	private String created_at;
	private String updated_at;

	public Catagory() {
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

	public String getCatagoryName() {
		return CatagoryName;
	}

	public void setCatagoryName(String catagoryName) {
		CatagoryName = catagoryName;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
