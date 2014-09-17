package com.tr.nearfood.activity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.tr.nearfood.utills.ConnectionDetector;
import com.tr.nearfood.utills.AlertDialogManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.tr.nearfood.R;
import com.tr.nearfood.adapter.ExpandableListAdapterForCustomerOrder;
import com.tr.nearfood.pushnotification.MainActivity;
import com.tr.nearfood.utills.GPSTracker;
import com.tr.nearfood.utills.NearFoodTextView;

public class RestaurantCatagory extends Activity {

	public static String regid = null;
	ImageButton takeAway, table, delivery, suscribeIB;
	Button suscribe, restaurantListButton;
	int SELECT_TAKE_AWAY = 3, SELECT_DELIVERY = 1, SELECT_TABLE = 2,NEAR_BY_RESTAURANT=4;
	double latitude = 0, longitude = 0;
	Context ctx = this, context;
	GoogleCloudMessaging gcm;
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	SharedPreferences prefs;
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();

	/**
	 * Substitute you own sender ID here. This is the project number you got
	 * from the API Console, as described in "Getting Started."
	 */
	public static String SENDER_ID = "1049372403771";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.catagory);
		context = getApplicationContext();
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(RestaurantCatagory.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			// return;
		}
		NearFoodTextView.setDefaultFont(this, "DEFAULT", "Roboto-Regular.ttf");
		get_latlong();
		takeAway = (ImageButton) findViewById(R.id.ibTakeAway);
		table = (ImageButton) findViewById(R.id.ibTable);
		delivery = (ImageButton) findViewById(R.id.ibDelivery);
		suscribeIB = (ImageButton) findViewById(R.id.buttonSuscribe);
		restaurantListButton = (Button) findViewById(R.id.buttonRestaurantList);
		restaurantListButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent start = new Intent(getApplicationContext(),
						RestaurantList.class);
				start.putExtra("latitude", latitude);
				start.putExtra("longitude", longitude);
				start.putExtra("catagory_id", NEAR_BY_RESTAURANT);
				startActivity(start);
			}
		});
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
			regid = getRegistrationId(context);
			Log.d("registration id", regid);
			if (regid.isEmpty()) {
				registerInBackground();
			} else {

			}
		} else {
			Log.i("TAG", "No valid Google Play Services APK found.");
		}

		suscribeIB.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					suscribeIB.setColorFilter(Color.argb(150, 155, 155, 155));

					Intent start = new Intent(getApplicationContext(),
							RestaurantSubscribtion.class);
					startActivity(start);

					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					suscribeIB.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																				// null
					return true;
				}
				return false;
			}

		});
		takeAway.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					takeAway.setColorFilter(Color.argb(150, 155, 155, 155));

					Calendar cal = Calendar.getInstance();
					cal.setTimeZone(TimeZone.getTimeZone("GMT-1"));
					Date dt = null;
					Date dt1 = null;
					try {
						dt = new SimpleDateFormat("yyyy-MM-dd HH:mm")
								.parse("2014-09-02 9:30");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// dt1 = new
					// SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dtend);

					final Calendar beginTime = Calendar.getInstance();
					cal.setTime(dt);

					// beginTime.set(2013, 7, 25, 7, 30);
					beginTime.set(cal.get(Calendar.YEAR),
							cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
							cal.get(Calendar.HOUR_OF_DAY),
							cal.get(Calendar.MINUTE));

					// pushAppointmentsToCalender(RestaurantCatagory.this,
					// "test","this is test", "oatan", 0,
					// beginTime.getTimeInMillis(), true, false);

					Intent start = new Intent(getApplicationContext(),
							RestaurantList.class);
					start.putExtra("catagory_id", SELECT_TAKE_AWAY);
					start.putExtra("latitude", latitude);
					start.putExtra("longitude", longitude);
					startActivity(start);
					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					takeAway.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																			// null
					return true;
				}
				return false;
			}

		});
		table.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					table.setColorFilter(Color.argb(150, 155, 155, 155));

					Intent start = new Intent(getApplicationContext(),
							RestaurantList.class);
					start.putExtra("latitude", latitude);
					start.putExtra("longitude", longitude);
					start.putExtra("catagory_id", SELECT_TABLE);
					startActivity(start);
					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					table.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																		// null
					return true;
				}
				return false;
			}

		});
		delivery.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					delivery.setColorFilter(Color.argb(150, 155, 155, 155));
					Intent start = new Intent(getApplicationContext(),
							RestaurantList.class);
					start.putExtra("catagory_id", SELECT_DELIVERY);
					start.putExtra("latitude", latitude);
					start.putExtra("longitude", longitude);
					startActivity(start);
					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					delivery.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																			// null
					return true;
				}
				return false;
			}

		});
	}

	private void registerInBackground() {

		class Register extends AsyncTask<Void, Void, String> {

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

		}
		new Register().execute();
	}

	private void storeRegistrationId(Context context, String regId) {
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i("TAG", "Saving regId on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Log.i("TAG", "This device is not supported.");
				finish();
			}
			return false;
		}
		return true;
	}

	private String getRegistrationId(Context context) {
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			Log.i("TAG", "Registration not found.");
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION,
				Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Log.i("TAG", "App version changed.");
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

	public void get_latlong() {
		GPSTracker gps = new GPSTracker(this);

		// Check if GPS enabled
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			// Log.d("longitude_latfsdfsd",Double.toString(latitude)+"lonfsdfds="+Double.toString(longitude));
		} else {
			// Can't get location.
			// GPS or network is not enabled.
			// Ask user to enable GPS/network in settings.
			gps.showSettingsAlert();
		}

	}

	static String getEmail(Context context) {
		AccountManager accountManager = AccountManager.get(context);
		Account account = getAccount(accountManager);

		if (account == null) {
			return null;
		} else {
			return account.name;
		}
	}

	private static Account getAccount(AccountManager accountManager) {
		Account[] accounts = accountManager.getAccountsByType("com.google");
		Account account;
		if (accounts.length > 0) {
			account = accounts[0];
		} else {
			account = null;
		}
		return account;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back.
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
