package com.tr.nearfood.dbhelper;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.drive.internal.AddEventListenerRequest;
import com.tr.nearfood.model.Catagory;
import com.tr.nearfood.model.CustomerInfoDTO;
import com.tr.nearfood.model.CustomerOrdersDTO;
import com.tr.nearfood.model.ItemMenuDTO;
import com.tr.nearfood.model.OrderedItemDTO;
import com.tr.nearfood.model.ReservationDTO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 6;

	// Database Name
	private static final String DATABASE_NAME = "Menu_Items";

	// Table Names
	private static final String TABLE_ITEMS = "item";
	private static final String TABLE_CATAGORY = "catagorys";
	private static final String TABLE_ORDER = "adminorder";
	private static final String TABLE_CUSTOMER_DETAILS = "customer";
	private static final String TABLE_CUSTOMER_ORDERS = "customersorders";
	private static final String TABLE_RESTAURANT_LIST = "restautantlist";
	private static final String TABLE_RESERVE_TABLE = "reservetable";
	private static final String TABLE_NOTIFICATION_MESSAGE = "message";
	// Common column names
	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_CATAGORY_ID = "catagory_id";
	private static final String KEY_IS_ACTIVE = "is_active";
	private static final String KEY_UPDATED_AT = "updated_at";
	private static final String KEY_NOTI_MESSAGE = "noti_message";
	// ITEMS Table - column nmaes
	private static final String KEY_ITEM_ID = "item_id";
	private static final String KEY_ORDERED_ID = "order_id";
	private static final String KEY_RESTAURANT_ID = "restaurant_id";
	private static final String KEY_ITEM_NAME = "item_name";
	private static final String KEY_ITEM_PRICE = "item_price";

	// ORDERS Table-column names
	private static final String KEY_ITEMS_LIST = "item_list";
	private static final String KEY_STATUS = "status";
	private static final String KEY_RESTAURANT_NAME = "rest_name";
	private static final String KEY_RESTAURANT_Address = "rest_address";
	// CUSTOMER_DETAILS - column names
	private static final String KEY_CUSTOMER_ID = "customer_id";
	private static final String KEY_CUSTOMER_NAME = "name";
	private static final String KEY_CUSTOMER_DETAILS_JSON = "json";

	private static final String KEY_NO_OF_PEOPLE = "no_of_people";
	private static final String KEY_RESERVE_TABLE_MESSAGE = "message";
	private static final String KEY_CONTACT_NUMBER = "phone";
	private static final String KEY_EMAIL = "email";
	// Catagory Table - column names

	private static final String KEY_CATAGORY_NAME = "catagory_name";
	private static final String KEY_CATAGORY_DESCRIPTION = "catagory_description";

	// ITEM_CATAGORY Table - column names
	private static final String KEY_SQL_ITEM_ID = "sql_item_id";
	private static final String KEY_SQL_CATAGORY_ID = "sql_catagory_id";

	// Table Create Statements
	// ITEM table create statement
	private static final String CREATE_TABLE_ITEM = "CREATE TABLE "
			+ TABLE_ITEMS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_ITEM_ID + " INTEGER," + KEY_CATAGORY_ID + " INTEGER,"
			+ KEY_RESTAURANT_ID + " INTEGER," + KEY_IS_ACTIVE + " TEXT,"
			+ KEY_ITEM_NAME + " TEXT," + KEY_ITEM_PRICE + " INTEGER,"
			+ KEY_CREATED_AT + " TEXT," + KEY_UPDATED_AT + " TEXT" + ")";

	private static final String CREATE_TABLE_CUSTOMER = "CREATE TABLE "
			+ TABLE_CUSTOMER_DETAILS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_CUSTOMER_ID + " INTEGER," + KEY_CUSTOMER_NAME + " TEXT,"
			+ KEY_CUSTOMER_DETAILS_JSON + " TEXT" + ")";
	private static final String CREATE_TABLE_RESERVATION = "CREATE TABLE "
			+ TABLE_RESERVE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_CUSTOMER_ID + " INTEGER," + KEY_CUSTOMER_NAME + " TEXT,"
			+ KEY_CONTACT_NUMBER + " TEXT," + KEY_EMAIL + " TEXT,"
			+ KEY_RESERVE_TABLE_MESSAGE + " TEXT," + KEY_NO_OF_PEOPLE
			+ " INTEGER," + KEY_CUSTOMER_DETAILS_JSON + " TEXT" + ")";

	private static final String CREATE_TABLE_ORDERED_ITEM = "CREATE TABLE "
			+ TABLE_ORDER + "(" + KEY_ID + " INTEGER," + KEY_ITEM_ID
			+ " INTEGER," + KEY_ORDERED_ID + " INTEGER," + KEY_RESTAURANT_ID
			+ " INTEGER," + KEY_ITEM_NAME + " TEXT," + KEY_ITEM_PRICE
			+ " INTEGER," + KEY_CREATED_AT + " TEXT," + KEY_UPDATED_AT
			+ " TEXT" + ")";

	private static final String CREATE_TABLE_ORDER = "CREATE TABLE "
			+ TABLE_CUSTOMER_ORDERS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_RESTAURANT_ID + " INTEGER," + KEY_ITEMS_LIST + " TEXT,"
			+ KEY_STATUS + " TEXT" + ")";

	private static final String CREATE_TABLE_NOTIFICATION = "CREATE TABLE "
			+ TABLE_NOTIFICATION_MESSAGE + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY," + KEY_NOTI_MESSAGE + " TEXT" + ")";

	private static final String CREATE_TABLE_RESTAURANTS_LIST = "CREATE TABLE "
			+ TABLE_RESTAURANT_LIST + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_RESTAURANT_ID + " INTEGER," + KEY_RESTAURANT_NAME + " TEXT,"
			+ KEY_RESTAURANT_Address + " TEXT" + ")";
	// CATAGORY table create statement
	private static final String CREATE_TABLE_CATAGORY = "CREATE TABLE "
			+ TABLE_CATAGORY + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_CATAGORY_ID + " INTEGER," + KEY_IS_ACTIVE + " TEXT,"
			+ KEY_CATAGORY_NAME + " TEXT," + KEY_CATAGORY_DESCRIPTION
			+ " INTEGER," + KEY_CREATED_AT + " TEXT," + KEY_UPDATED_AT
			+ " TEXT" + ")";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_ITEM);
		db.execSQL(CREATE_TABLE_CATAGORY);
		db.execSQL(CREATE_TABLE_ORDERED_ITEM);
		db.execSQL(CREATE_TABLE_CUSTOMER);
		db.execSQL(CREATE_TABLE_ORDER);
		db.execSQL(CREATE_TABLE_RESTAURANTS_LIST);
		db.execSQL(CREATE_TABLE_RESERVATION);
		db.execSQL(CREATE_TABLE_NOTIFICATION);

		Log.d(LOG, "database created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATAGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER_DETAILS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER_ORDERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT_LIST);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION_MESSAGE);

		// create new tables
		onCreate(db);
	}

	// ------------------------ "items" table methods ----------------//

	/*
	 * Creating a item
	 */
	public void createItems(ItemMenuDTO item) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ITEM_ID, item.getId());
		values.put(KEY_ITEM_NAME, item.getItemName());
		values.put(KEY_ITEM_PRICE, item.getItemPrice());
		values.put(KEY_CATAGORY_ID, item.getItemCatagoryID());
		values.put(KEY_RESTAURANT_ID, item.getRestaurants_id());
		values.put(KEY_IS_ACTIVE, item.getIs_active());
		values.put(KEY_CREATED_AT, item.getUpdated_at());
		values.put(KEY_UPDATED_AT, item.getUpdated_at());

		// insert row
		db.insert(TABLE_ITEMS, null, values);
		Log.d(LOG, "Item table created");

	}

	public void createOrders(CustomerOrdersDTO order) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_RESTAURANT_ID, order.getRestaurant_id());
		values.put(KEY_ITEMS_LIST, order.getItems_list());
		values.put(KEY_STATUS, order.getStatus());

		db.insert(TABLE_CUSTOMER_ORDERS, null, values);
		Log.d(LOG, "Order table created");

	}

	public void createNotifiaction(String message) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NOTI_MESSAGE, message);
		db.insert(TABLE_NOTIFICATION_MESSAGE, null, values);
		Log.d(LOG, "NOtifiation table created");

	}

	public void createReservationDetail(ReservationDTO reservationDTO) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CUSTOMER_ID, reservationDTO.getId());
		values.put(KEY_CUSTOMER_NAME, reservationDTO.getName());
		values.put(KEY_RESERVE_TABLE_MESSAGE, reservationDTO.getMessage());
		values.put(KEY_EMAIL, reservationDTO.getEmail());
		values.put(KEY_CONTACT_NUMBER, reservationDTO.getPhone());
		values.put(KEY_CUSTOMER_DETAILS_JSON, reservationDTO.getJson());
		values.put(KEY_NO_OF_PEOPLE, reservationDTO.getNo_of_people());
		// insert row
		db.insert(TABLE_RESERVE_TABLE, null, values);
		Log.d(LOG, "Reservation table created");
	}

	public int getNoOfPeople(String headName, String message) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_RESERVE_TABLE
				+ " WHERE " + KEY_CUSTOMER_NAME + " =  \"" + headName + "\"";/*
				+ " AND " + KEY_RESERVE_TABLE_MESSAGE + " =  \"" + message
				+ "\"";*/

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null && c.moveToFirst()) {
			int no_of_people = c.getInt(c.getColumnIndex(KEY_NO_OF_PEOPLE));
			return no_of_people;
		}
		return 0;
	}

	public void createCustomer(CustomerInfoDTO customerInfoDTO) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CUSTOMER_ID, customerInfoDTO.getId());
		values.put(KEY_CUSTOMER_NAME, customerInfoDTO.getName());
		values.put(KEY_CUSTOMER_DETAILS_JSON, customerInfoDTO.getJson());
		// insert row
		db.insert(TABLE_CUSTOMER_DETAILS, null, values);
		Log.d(LOG, "Customer table created");

	}

	public void createRestaurantList(int id, String name, String address) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_RESTAURANT_ID, id);
		values.put(KEY_RESTAURANT_NAME, name);
		values.put(KEY_RESTAURANT_Address, address);
		db.insert(TABLE_RESTAURANT_LIST, null, values);

	}

	public String getRestaurantName(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT_LIST
				+ " WHERE " + KEY_RESTAURANT_ID + " = " + id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null && c.moveToFirst()) {
			String restaurant_name = c.getString(c
					.getColumnIndex(KEY_RESTAURANT_NAME));
			return restaurant_name;
		}
		return null;
	}

	public String getRestaurantAddresss(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_RESTAURANT_LIST
				+ " WHERE " + KEY_RESTAURANT_ID + " = " + id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null && c.moveToFirst()) {
			String restaurant_name = c.getString(c
					.getColumnIndex(KEY_RESTAURANT_Address));
			return restaurant_name;
		}
		return null;
	}

	/*
	 * Creating Catagory
	 */
	public void createCatagory(Catagory catagory) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CATAGORY_ID, catagory.getId());
		values.put(KEY_CATAGORY_NAME, catagory.getCatagoryName());
		values.put(KEY_CATAGORY_DESCRIPTION, catagory.getDescription());
		values.put(KEY_IS_ACTIVE, catagory.getIs_active());
		values.put(KEY_CREATED_AT, catagory.getCreated_at());
		values.put(KEY_UPDATED_AT, catagory.getUpdated_at());

		// insert row
		db.insert(TABLE_CATAGORY, null, values);
		Log.d(LOG, "catagory tablecreated");
	}

	public void createOrderItem(OrderedItemDTO order) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_RESTAURANT_ID, order.getRestaurant_id());
		values.put(KEY_ITEM_ID, order.getItem_id());
		values.put(KEY_ITEM_NAME, order.getItem());
		values.put(KEY_ITEM_PRICE, order.getPrice());

		// insert row
		db.insert(TABLE_ORDER, null, values);
		Log.d(LOG, "order tablecreated");
	}

	public String getCustomerJsonDetails(String customer_name, String table_name) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + table_name + " WHERE "
				+ KEY_CUSTOMER_NAME + " =  \"" + customer_name + "\"";

		Log.e(LOG, selectQuery);
		String json = "";
		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null && c.moveToFirst())
			json = c.getString(c.getColumnIndex(KEY_CUSTOMER_DETAILS_JSON));
		c.close();
		return json;
	}

	/*
	 * get single Catagory
	 */
	public Catagory getCatagory(int catagory_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_CATAGORY + " WHERE "
				+ KEY_CATAGORY_ID + " = " + catagory_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		Catagory td = new Catagory();
		td.setId(c.getInt(c.getColumnIndex(KEY_CATAGORY_ID)));
		td.setCatagoryName(c.getString(c.getColumnIndex(KEY_CATAGORY_NAME)));
		td.setDescription(c.getString(c
				.getColumnIndex(KEY_CATAGORY_DESCRIPTION)));
		td.setIs_active(c.getString(c.getColumnIndex(KEY_IS_ACTIVE)));
		td.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
		td.setUpdated_at(c.getString(c.getColumnIndex(KEY_UPDATED_AT)));
		c.close();
		return td;
	}

	public List<CustomerOrdersDTO> getallorders() {
		List<CustomerOrdersDTO> orderList = new ArrayList<CustomerOrdersDTO>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_CUSTOMER_ORDERS
				+ " WHERE " + KEY_STATUS + " =  \"" + "PENDING" + "\"";
		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null && c.moveToFirst()) {
			do {
				CustomerOrdersDTO order = new CustomerOrdersDTO();
				order.setRestaurant_id(c.getInt(c
						.getColumnIndex(KEY_RESTAURANT_ID)));
				order.setItems_list(c.getString(c
						.getColumnIndex(KEY_ITEMS_LIST)));
				order.setStatus(c.getString(c.getColumnIndex(KEY_STATUS)));

				orderList.add(order);
			} while (c.moveToNext());
		}

		c.close();
		return orderList;
	}

	public List<String> getallNotifications() {
		List<String> orderList = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_NOTIFICATION_MESSAGE;
		Cursor c = db.rawQuery(selectQuery, null);
		String message;
		if (c != null && c.moveToFirst()) {
			do {

				message = (c.getString(c.getColumnIndex(KEY_NOTI_MESSAGE)));

				orderList.add(message);
			} while (c.moveToNext());
		}

		c.close();
		return orderList;
	}

	// get all items of respectice catagory

	public List<ItemMenuDTO> getItems(int catagory_id, int restaurant_id) {
		List<ItemMenuDTO> itemList = new ArrayList<ItemMenuDTO>();
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE "
				+ KEY_CATAGORY_ID + "=" + catagory_id + " AND "
				+ KEY_RESTAURANT_ID + "=" + restaurant_id;
		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();
		do {
			ItemMenuDTO item = new ItemMenuDTO();
			item.setId(c.getInt(c.getColumnIndex(KEY_ITEM_ID)));
			item.setRestaurants_id(c.getInt(c.getColumnIndex(KEY_RESTAURANT_ID)));
			item.setItemName(c.getString(c.getColumnIndex(KEY_ITEM_NAME)));
			item.setItemPrice(c.getInt(c.getColumnIndex(KEY_ITEM_PRICE)));
			item.setIs_active(c.getString(c.getColumnIndex(KEY_IS_ACTIVE)));

			itemList.add(item);
		} while (c.moveToNext());
		c.close();

		return itemList;
	}

	public Boolean checkItemPresence(int restaurant_id, int item_id,
			String TABLE) {
		List<ItemMenuDTO> itemList = new ArrayList<ItemMenuDTO>();
		Boolean check = false;
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE + " WHERE " + KEY_ITEM_ID
				+ "=" + item_id + " AND " + KEY_RESTAURANT_ID + "="
				+ restaurant_id;
		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null) {
			if (c.moveToFirst()) {
				do {
					ItemMenuDTO item = new ItemMenuDTO();
					item.setId(c.getInt(c.getColumnIndex(KEY_ITEM_ID)));
					item.setRestaurants_id(c.getInt(c
							.getColumnIndex(KEY_RESTAURANT_ID)));
					item.setItemName(c.getString(c
							.getColumnIndex(KEY_ITEM_NAME)));
					item.setItemPrice(c.getInt(c.getColumnIndex(KEY_ITEM_PRICE)));

					itemList.add(item);
				} while (c.moveToNext());
			}

		}
		c.close();

		if (itemList.size() != 0)
			check = false;
		else
			check = true;
		return check;
	}

	public List<Integer> getItemIdList() {
		SQLiteDatabase db = this.getReadableDatabase();
		List<Integer> idList = new ArrayList<Integer>();
		String selectQuery = "SELECT " + KEY_ITEM_ID + " FROM " + TABLE_ITEMS;
		int listId;
		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			if (c.moveToFirst())
				do {
					listId = c.getInt(c.getColumnIndex(KEY_ITEM_ID));
					idList.add(listId);
				} while (c.moveToNext());
		c.close();

		return idList;
	}

	public int getItemId(String item_name, String TABLE) {
		SQLiteDatabase db = this.getReadableDatabase();
		int itemid = 0;
		String selectQuery = "SELECT " + KEY_ITEM_ID + " FROM " + TABLE
				+ " WHERE " + KEY_ITEM_NAME + " =  \"" + item_name + "\"";

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null) {
			if (c.moveToFirst())
				itemid = c.getInt(c.getColumnIndex(KEY_ITEM_ID));
		}
		c.close();

		return itemid;
	}

	public String getItemName(int item_id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String itemname = "";
		String selectQuery = "SELECT " + KEY_ITEM_NAME + " FROM " + TABLE_ITEMS
				+ " WHERE " + KEY_ITEM_ID + " = " + item_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null) {
			if (c.moveToFirst())
				itemname = c.getString(c.getColumnIndex(KEY_ITEM_NAME));
		}
		c.close();

		return itemname;
	}

	public int getRestaurantId(int item_id) {
		SQLiteDatabase db = this.getReadableDatabase();
		int restaurantId = 0;
		String selectQuery = "SELECT " + KEY_RESTAURANT_ID + " FROM "
				+ TABLE_ITEMS + " WHERE " + KEY_ITEM_ID + " = " + item_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null) {
			if (c.moveToFirst())
				restaurantId = c.getInt(c.getColumnIndex(KEY_RESTAURANT_ID));
		}
		c.close();

		return restaurantId;
	}

	public int getItemPrice(int item_id, String TABLE) {
		SQLiteDatabase db = this.getReadableDatabase();
		int itemprice = 0;
		String selectQuery = "SELECT " + KEY_ITEM_PRICE + " FROM " + TABLE
				+ " WHERE " + KEY_ITEM_ID + " = " + item_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null) {
			if (c.moveToFirst())
				itemprice = c.getInt(c.getColumnIndex(KEY_ITEM_PRICE));
		}
		c.close();

		return itemprice;
	}

	public void deleteItem(int itm_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ITEMS, KEY_ITEM_ID + " = ?",
				new String[] { String.valueOf(itm_id) });
	}

	public void dropTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		String sql = "DROP TABLE IF EXISTS " + TABLE_ITEMS;
		String sql1 = "DROP TABLE IF EXISTS " + TABLE_CATAGORY;
		String sql2 = "DROP TABLE IF EXISTS " + TABLE_ORDER;

		try {
			db.execSQL(sql);
			db.execSQL(sql1);
			db.execSQL(sql2);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER_DETAILS);
			// setTitle(sql);
			// create new tables
			onCreate(db);
		} catch (SQLException e) {
			// setTitle("exception");
		}

	}

	public List<Integer> getcatagorylist(int restaurant_id) {
		List<Integer> catagoryList = new ArrayList<Integer>();
		int cat;
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE "
				+ KEY_RESTAURANT_ID + "=" + restaurant_id;
		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null && c.moveToFirst()) {
			do {

				cat = (c.getInt(c.getColumnIndex(KEY_CATAGORY_ID)));

				catagoryList.add(cat);
			} while (c.moveToNext());
			c.close();
		}
		return catagoryList;
	}

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	public boolean checkItemPresence(String customer_name) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_CUSTOMER_DETAILS
				+ " WHERE " + KEY_CUSTOMER_NAME + "=" + customer_name;
		Cursor c = db.rawQuery(selectQuery, null);
		if (c == null) {
			c.close();
			return true;
		} else {
			c.close();
			return false;
		}

	}

}
