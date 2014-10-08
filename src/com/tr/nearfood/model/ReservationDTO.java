package com.tr.nearfood.model;

public class ReservationDTO {
	int id;
	String name;
	String email;
	String message;
	String phone;
	String json;
	int no_of_people;
	public int getNo_of_people() {
		return no_of_people;
	}

	public void setNo_of_people(int no_of_people) {
		this.no_of_people = no_of_people;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
