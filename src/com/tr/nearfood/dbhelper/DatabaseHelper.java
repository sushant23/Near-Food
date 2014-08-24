package com.tr.nearfood.dbhelper;

import java.util.ArrayList;
import java.util.List;

import com.tr.nearfood.model.Catagory;
import com.tr.nearfood.model.CustomerInfoDTO;
import com.tr.nearfood.model.ItemMenuDTO;
import com.tr.nearfood.model.OrderedItemDTO;

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
	private static final int DATABASE_VERSION = 3;

	// Database Name
	private static final String DATABASE_NAME = "Menu_Items";

	// Table Names
	private static final String TABLE_ITEMS = "item";
	private static final String TABLE_CATAGORY = "catagorys";
	private static final String TABLE_ORDER = "adminorder";
	private static final String TABLE_CUSTOMER_DETAILS = "customer";
	// Common column names
	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_CATAGORY_ID = "catagory_id";
	private static final String KEY_IS_ACTIVE = "is_active";
	private static final String KEY_UPDATED_AT = "updated_at";

	// ITEMS Table - column nmaes
	private static final String KEY_ITEM_ID = "item_id";
	private static final String KEY_ORDERED_ID = "order_id";
	private static final String KEY_RESTAURANT_ID = "restaurant_id";
	private static final String KEY_ITEM_NAME = "item_name";
	private static final String KEY_ITEM_PRICE = "item_price";

	// CUSTOMER_DETAILS - column names
	private static final String KEY_CUSTOMER_ID = "customer_id";
	private static final String KEY_CUSTOMER_NAME = "name";
	private static final String KEY_CUSTOMER_DETAILS_JSON = "json";

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

	private static final String CREATE_TABLE_ORDERED_ITEM = "CREATE TABLE "
			+ TABLE_ORDER + "(" + KEY_ID + " INTEGER," + KEY_ITEM_ID
			+ " INTEGER," + KEY_ORDERED_ID + " INTEGER," + KEY_RESTAURANT_ID
			+ " INTEGER," + KEY_ITEM_NAME + " TEXT," + KEY_ITEM_PRICE
			+ " INTEGER," + KEY_CREATED_AT + " TEXT," + KEY_UPDATED_AT
			+ " TEXT" + ")";

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
		Log.d(LOG, "database created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATAGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER_DETAILS);
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

	public String getCustomerJsonDetails(String customer_name) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_CUSTOMER_DETAILS
				+ " WHERE " + KEY_CUSTOMER_NAME + " =  \"" + customer_name
				+ "\"";

		Log.e(LOG, selectQuery);
		Cursor c = db.rawQuery(selectQuery, null);
		if (c != null)
			c.moveToFirst();
		String json = c.getString(c.getColumnIndex(KEY_CUSTOMER_DETAILS_JSON));
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
		String sql2 = "DROP TABLE IF EXITS " + TABLE_ORDER;

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

		if (c != null)
			c.moveToFirst();
		do {

			cat = (c.getInt(c.getColumnIndex(KEY_CATAGORY_ID)));

			catagoryList.add(cat);
		} while (c.moveToNext());
		c.close();

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
