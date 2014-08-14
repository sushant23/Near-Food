package com.tr.nearfood.utills;

import java.util.ArrayList;
import java.util.List;

public class AppConstants {

	// GET request urls
	final static public String RESTAURANTS_LIST = "http://quofood.com/quofood/public/api/restaurant_by_type?id=";
	final static public String ITEMS_BY_RESTAURANTS = "http://quofood.com/quofood/public/api/items_by_restaurant?id=";

	// POST request URLs
	final static public String RESTAURANTS_REGISTRATION = "http://192.168.0.103:1337/admin/users/";
	final static public String RESTAURANTS_LOGIN = "http://192.168.0.103:1337/api/login";
}
