package com.tr.nearfood.utills;

import java.util.ArrayList;
import java.util.List;

public class AppConstants {

	// GET request urls
	final static public String RESTAURANTS_LIST = "http://www.quofood.com/quofood/public/api/restaurant_by_type?id=";
	final static public String ITEMS_BY_RESTAURANTS = "http://www.quofood.com/quofood/public/api/items_by_restaurant?id=";

	// POST request URLs
	final static public String RESTAURANTS_REGISTRATION = "http://www.quofood.com/quofood/public/admin/users";
	//final static public String RESTAURANTS_LOGIN = "http://192.168.0.111:1337/api/login";
	final static public String RESTAURANTS_LOGIN = "http://www.quofood.com/quofood/public/api/login";
	final static public String CUSTOMER_ORDER="http://www.quofood.com/quofood/public/api/order";
	final static public String RESTAURANTS_ORDERS_LIST="http://www.quofood.com/quofood/public/api/adminorder";
	final static public String RESTAURANTS_ORDER_CONFIRMED="http://www.quofood.com/quofood/public/api/check/CONFIRMED";
	final static public String RESTAURANTS_ORDER_REJECTED="http://www.quofood.com/quofood/public/api/check/REJECTED";
	final static public String RESTAURANTS_CONFIRM_REJECTED_ORDER="http://www.quofood.com/quofood/public/api/edit-order"; 
	final static public String RESTAURANTS_ORDER_CONFIRMING="http://www.quofood.com/quofood/public/api/changestatus/CONFIRMED";
	final static public String RESTAURANTS_ORDER_REJECTING="http://www.quofood.com/quofood/public/api/changestatus/REJECTED";
	
	final static public String RESTAURANTS_DETAILS_EDIT="http://www.quofood.com/quofood/public/admin/edit-personal-details";
	final static public String SERVER_URL = "http://www.quofood.com/quofood/public/gcm/notification"; 

	final static public String RESTAURANTS_RESERVE_TABLE="http://www.quofood.com/quofood/public/api/reservation";
	final static public String RESTAURANT_VIEW_RESERVATION="http://www.quofood.com/quofood/public/api/adminreservations/";
	final static public String RESTAURANT_CHANGE_RESERVATION_STAUTS="http://www.quofood.com/quofood/public/api/change-reservation-status/";
	
	final static public String API="c2VjcmV0";
}
