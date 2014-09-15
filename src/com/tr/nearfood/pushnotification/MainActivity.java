package com.tr.nearfood.pushnotification;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tr.nearfood.R;
import com.tr.nearfood.adapter.ExpandableListAdapterForCustomerOrder;
import com.tr.nearfood.dbhelper.DatabaseHelper;
import com.tr.nearfood.fragment.FragmentResturantProfile;
import com.tr.nearfood.model.CustomerOrdersDTO;
import com.tr.nearfood.utills.AppConstants;
import com.tr.nearfood.utills.SetEventToCalandar;

public class MainActivity extends Activity {
	// label to display gcm messages

	public static final String EXTRA_MESSAGE = "message";
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	/**
	 * Substitute you own sender ID here. This is the project number you got
	 * from the API Console, as described in "Getting Started."
	 */
	public static String SENDER_ID = "1049372403771";

	/**
	 * Tag used on log messages.
	 */
	static final String TAG = "GCMDemo";
	ExpandableListAdapterForCustomerOrder listAdapter;
	ExpandableListView expListView;
	TextView mDisplay;
	GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();
	SharedPreferences prefs;
	Context context;
	String json_string;
	String regid = "";
	JSONObject orders;
	int restaurant_id;
	String items_list, status;
	DatabaseHelper db;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;
	SetEventToCalandar writeEvent = new SetEventToCalandar();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_main);
		mDisplay = (TextView) findViewById(R.id.lblMessage);
		db = new DatabaseHelper(this);
		context = getApplicationContext();
		expListView = (ExpandableListView) findViewById(R.id.expandableListMenuCollasapable);

		Bundle b = getIntent().getExtras();
		if (b != null) {
			json_string = b.getString("json_string");
			items_list = b.getString("items");
			try {
				status = "PENDING";
				orders = new JSONObject(json_string);
				restaurant_id = orders.getInt("restaurant_id");
				CustomerOrdersDTO order = new CustomerOrdersDTO();
				order.setRestaurant_id(restaurant_id);
				order.setItems_list(items_list);
				order.setStatus(status);
				db.createOrders(order);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Check device for Play Services APK. If check succeeds, proceed with
		// GCM registration.
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(context);
			Log.d("registration id", regid);
			if (regid.isEmpty()) {
				registerInBackground();
			} else {
				prepareOrderList();
				sendRegistrationIdToBackend();
				listAdapter = new ExpandableListAdapterForCustomerOrder(this,
						listDataHeader, listDataChild);

				// setting list adapter
				expListView.setAdapter(listAdapter);
			}
		} else {
			Log.i(TAG, "No valid Google Play Services APK found.");
		}

	}

	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 * 
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i(TAG, "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i(TAG, "App version changed.");
			return "";
		}
		return registrationId;
	}

	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
		// This sample app persists the registration ID in shared preferences,
		// but
		// how you store the regID in your app is up to you.
		return getSharedPreferences(MainActivity.class.getSimpleName(),
				Context.MODE_PRIVATE);
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	// You need to do the Play Services APK check here too.
	@Override
	protected void onResume() {
		super.onResume();
		checkPlayServices();
	}

	public void prepareOrderList() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		int restaurant_id;
		String items_list;
		List<CustomerOrdersDTO> ordersSQL = new ArrayList<CustomerOrdersDTO>();
		ordersSQL = db.getallorders();
		Log.d("SQLLITE", ordersSQL.toString());
		if (ordersSQL.size() != 0) {
			for (int i = 0; i < ordersSQL.size(); i++) {
				CustomerOrdersDTO single = new CustomerOrdersDTO();
				single = ordersSQL.get(i);
				restaurant_id = single.getRestaurant_id();
				items_list = single.getItems_list();
				/*
				 * Log.d("fsdfsd",
				 * Integer.toString(restaurant_id)+"list="+items_list);
				 */
				String restaurant_name = db.getRestaurantName(restaurant_id);
				listDataHeader.add(restaurant_name);
				String[] items = items_list.split(",");
				List<String> orderList = new ArrayList<String>();
				for (String item : items) {
					String item_name = db.getItemName(Integer.parseInt(item));
					orderList.add(item_name);
					Log.d("itemsss", "item = " + item + "restaurant="
							+ restaurant_name + "itemNme=" + item_name);
				}
				listDataChild.put(restaurant_name, orderList);

			}
		}
	}

	/**
	 * Registers the application with GCM servers asynchronously.m_name
	 * <p>
	 * Stores the registration ID and app versionCode in the application's
	 * shared preferences.
	 */
	private void registerInBackground() {

		class Register extends AsyncTask<Void, Void, String> {
			ProgressDialog pd = null;

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				if (pd == null) {
					pd = new ProgressDialog(MainActivity.this);
					pd.setCancelable(true);
					pd.setTitle("Please wait");
					pd.setMessage("Loading...");
					pd.show();
				}
			}

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(context);
					}
					regid = gcm.register(SENDER_ID);
					Log.d("registration id", regid);

					msg = "Device registered, registration ID=" + regid;

					// You should send the registration ID to your server over
					// HTTP,
					// so it can use GCM/HTTP or CCS to send messages to your
					// app.
					// The request to your server should be authenticated if
					// your app
					// is using accounts.
					sendRegistrationIdToBackend();

					// For this demo: we don't need to send it because the
					// device
					// will send upstream messages to a server that echo back
					// the
					// message using the 'from' address in the message.

					// Persist the regID - no need to register again.
					storeRegistrationId(context, regid);
				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
					// If there is an error, don't just keep trying to register.
					// Require the user to click a button again, or perform
					// exponential back-off.
				}
				return msg;

			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				if (pd.isShowing()) {
					pd.dismiss();
					pd = null;
				}
				prepareOrderList();
				mDisplay.append(result + "\n");
				listAdapter = new ExpandableListAdapterForCustomerOrder(
						context, listDataHeader, listDataChild);
				// setting list adapter
				expListView.setAdapter(listAdapter);

			}
		}
		new Register().execute();
	}

	long addEventToCalanadar() {
		String restaurant_name = db.getRestaurantName(restaurant_id);
		String restaurant_address = db.getRestaurantAddresss(restaurant_id);
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT-1"));
		Date dt = null;
		try {
			dt = new SimpleDateFormat("yyyy-MM-dd HH:mm")
					.parse(FragmentResturantProfile.datetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final Calendar beginTime = Calendar.getInstance();
		cal.setTime(dt);

		// beginTime.set(2013, 7, 25, 7, 30);
		beginTime.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE));
		
		String eventItems="";
		String[] items = items_list.split(",");
		List<String> orderList = new ArrayList<String>();
		for (String item : items) {
			String item_name = db.getItemName(Integer.parseInt(item));
			eventItems=eventItems.concat(item_name+",");
			orderList.add(item_name);
		}
		
		Log.d("calalnder event list",eventItems);
		long eventID=writeEvent.pushAppointmentsToCalender(MainActivity.this,
				restaurant_name,eventItems , restaurant_address, 0,
				beginTime.getTimeInMillis(), true, false);
		return eventID;
	}

	/**
	 * Sends the registration ID to your server over HTTP, so it can use
	 * GCM/HTTP or CCS to send messages to your app. Not needed for this demo
	 * since the device sends upstream messages to a server that echoes back the
	 * message using the 'from' address in the message.
	 */
	private void sendRegistrationIdToBackend() {
		// Your implementation here.
		class SendRegistrationID extends AsyncTask<Void, Void, String> {

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				long event_id=addEventToCalanadar();

				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(AppConstants.CUSTOMER_ORDER);
				post.addHeader("api", AppConstants.API);
				try {
					List<NameValuePair> param = new ArrayList<NameValuePair>();
					param.add(new BasicNameValuePair("json_string", json_string));
					param.add(new BasicNameValuePair("reg_Id", regid));
					param.add(new BasicNameValuePair("event_id", Long.toString(event_id)));
					post.setEntity(new UrlEncodedFormEntity(param));
					HttpResponse response = client.execute(post);
					HttpEntity entity = response.getEntity();
					Log.d("order", param.toString());
					// Toast.makeText(getActivity(), result,
					// Toast.LENGTH_SHORT).show();

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}
		new SendRegistrationID().execute();
	}

	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 * 
	 * @param context
	 *            application's context.
	 * @param regId
	 *            registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	/**
	 * Check the device to make sure it has the Google Play Services APK. If it
	 * doesn't, display a dialog that allows users to download the APK from the
	 * Google Play Store or enable it in the device's system settings.
	 */
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i(TAG, "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

}