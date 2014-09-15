package com.tr.nearfood.utills;

import java.util.ArrayList;
import java.util.List;

public class AppConstants {

	// GET request urls
	final static public String RESTAURANTS_LIST = "http://192.168.0.104/techroadians/laravel/public/api/restaurant_by_type?id=";
	final static public String ITEMS_BY_RESTAURANTS = "http://192.168.0.104/techroadians/laravel/public/api/items_by_restaurant?id=";

	// POST request URLs
	final static public String RESTAURANTS_REGISTRATION = "http://192.168.0.104/techroadians/laravel/public/admin/users";
	//final static public String RESTAURANTS_LOGIN = "http://192.168.0.111:1337/api/login";
	final static public String RESTAURANTS_LOGIN = "http://192.168.0.104/techroadians/laravel/public/api/login";
	final static public String CUSTOMER_ORDER="http://192.168.0.104/techroadians/laravel/public/api/order";
	final static public String RESTAURANTS_ORDERS_LIST="http://192.168.0.104/techroadians/laravel/public/api/adminorder";
	final static public String RESTAURANTS_ORDER_CONFIRMED="http://192.168.0.104/techroadians/laravel/public/api/check/CONFIRMED";
	final static public String RESTAURANTS_ORDER_REJECTED="http://192.168.0.104/techroadians/laravel/public/api/check/REJECTED";
	final static public String RESTAURANTS_CONFIRM_REJECTED_ORDER="http://192.168.0.104/techroadians/laravel/public/api/edit-order"; 
	final static public String RESTAURANTS_ORDER_CONFIRMING="http://192.168.0.104/techroadians/laravel/public/api/changestatus/CONFIRMED";
	final static public String RESTAURANTS_ORDER_REJECTING="http://192.168.0.104/techroadians/laravel/public/api/changestatus/REJECTED";
	
	final static public String RESTAURANTS_DETAILS_EDIT="http://192.168.0.104/techroadians/laravel/public/admin/edit-personal-details";
	final static public String SERVER_URL = "http://192.168.0.104/techroadians/laravel/public/gcm/notification"; 

	final static public String RESTAURANTS_RESERVE_TABLE="http://192.168.0.104/techroadians/laravel/public/api/reservation";
	final static public String RESTAURANT_VIEW_RESERVATION="http://192.168.0.104/techroadians/laravel/public/api/adminreservations/";
	final static public String RESTAURANT_CHANGE_RESERVATION_STAUTS="http://192.168.0.104/techroadians/laravel/public/api/change-reservation-status/";
	
	final static public String API="c2VjcmV0";
}
