package com.tr.nearfood.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tr.nearfood.R;
import com.tr.nearfood.adapter.CustomAdapterResturantLists;
import com.tr.nearfood.fragment.FragmentGoogleMapRestaurant;
import com.tr.nearfood.fragment.FragmentNearByRestaurantList;
import com.tr.nearfood.fragment.FragmentNearByRestaurantList.FragmentNearByResturantListCommunicator;
import com.tr.nearfood.fragment.FragmentGoogleMap;
import com.tr.nearfood.fragment.FragmentNotification;
import com.tr.nearfood.fragment.FragmentRestaurantMenu;
import com.tr.nearfood.fragment.FragmentRestaurantMenu.FragmentResturantMenuListCommunicator;
import com.tr.nearfood.fragment.FragmentResturantList;
import com.tr.nearfood.fragment.FragmentResturantList.FragmentResturantListCommunicator;
import com.tr.nearfood.fragment.FragmentResturantProfile;
import com.tr.nearfood.fragment.FragmentResturantProfile.FragmentResturantProfileCommunicator;
import com.tr.nearfood.fragment.FragmentShowCustomerOrder.FragmentShowCustomerOrderCommunicator;
import com.tr.nearfood.fragment.FragmentShowCustomerOrder;
import com.tr.nearfood.model.CalendarEvent;
import com.tr.nearfood.model.MigratingDatas;
import com.tr.nearfood.model.ResturantDTO;
import com.tr.nearfood.pushnotification.MainActivity;
import com.tr.nearfood.utills.AlertDialogManager;
import com.tr.nearfood.utills.BadgeView;
import com.tr.nearfood.utills.ConnectionDetector;
import com.tr.nearfood.utills.NearFoodTextView;

public class RestaurantList extends ActionBarActivity implements
		FragmentResturantListCommunicator,
		FragmentResturantProfileCommunicator,
		FragmentResturantMenuListCommunicator,
		FragmentShowCustomerOrderCommunicator,FragmentNearByResturantListCommunicator {
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	AdView adView;
	ImageButton notification, homeButton, calender;
	int SELECTED_CATEGORY = 0;
	BadgeView badge, badge1;
	Button subscribe;
	double latitude, longitude;
	Context ctx = this;
	String baseUri;
	EditText restaurantFilter;
	LinearLayout searchContainer;
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	public static int badgeValue = 0;
	SharedPreferences prefs;
	Fragment fragmentGoogleMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(RestaurantList.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		Bundle dataFromRestaurantCategory = getIntent().getExtras();
		SELECTED_CATEGORY = dataFromRestaurantCategory.getInt("catagory_id");
		latitude = dataFromRestaurantCategory.getDouble("latitude");
		longitude = dataFromRestaurantCategory.getDouble("longitude");

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		NearFoodTextView.setDefaultFont(this, "DEFAULT", "Roboto-Regular.ttf");

		try {
			ActionBar myActionBar = getSupportActionBar();
			myActionBar.hide();
		} catch (Exception e) {
			e.printStackTrace();
		}

		initializeUIElements();
		showNotification();

		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);

		addResturantListFragment(SELECTED_CATEGORY, latitude, longitude);
		Log.d("value of category", Integer.toString(SELECTED_CATEGORY));

		homeButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					homeButton.setColorFilter(Color.argb(150, 155, 155, 155));
					Intent start = new Intent(getApplicationContext(),
							RestaurantCatagory.class);
					startActivity(start);
					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					homeButton.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																				// null
					return true;
				}
				return false;
			}

		});

		notification.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					notification.setColorFilter(Color.argb(150, 155, 155, 155));

					try {
						badge.hide();
						badgeValue = 0;
						SharedPreferences.Editor editor = prefs.edit();
						editor.putBoolean("badge", false);
						editor.commit();
					} catch (NullPointerException e) {
						Log.e("NOtification", e.toString());
					}
					Fragment restaurantNotification = new FragmentNotification();
					fragmentTransaction = getSupportFragmentManager()
							.beginTransaction();
					fragmentTransaction.replace(
							R.id.linLayoutFragmentContainer,
							restaurantNotification);
					fragmentTransaction.addToBackStack(null);
					fragmentTransaction.commit();
					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					notification.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																				// null
					return true;
				}
				return false;
			}

		});
		calender.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					calender.setColorFilter(Color.argb(150, 155, 155, 155));

					long startMillis = System.currentTimeMillis();
					Uri.Builder builder = CalendarContract.CONTENT_URI
							.buildUpon();
					builder.appendPath("time");
					ContentUris.appendId(builder, startMillis);
					Intent intent = new Intent(Intent.ACTION_VIEW)
							.setData(builder.build());
					startActivity(intent);

					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					calender.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																			// null
					return true;
				}
				return false;
			}

		});
		subscribe.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent subscribeActivity = new Intent(getApplicationContext(),
						com.tr.nearfood.pushnotification.MainActivity.class);
				startActivity(subscribeActivity);
			}
		});

	}

	void showNotification() {
		try {
			if (prefs.getBoolean("badge", false)) {

				int value = prefs.getInt("badgeValue", 0) + 1;
				badge = new BadgeView(this, notification);
				badge.setText(Integer.toString(value));
				badge.setBadgePosition(BadgeView.POSITION_BOTTOM_LEFT);
				if (value == 0)
					badge.hide();
				else
					badge.show();
			}
		} catch (NullPointerException e) {
			Log.e("NOTIFICATION ETTER", "NULL pont exception");
		}
	}

	@Override
	public void setClickedData(ResturantDTO resturantDTO) {
		// searchContainer.setVisibility(View.GONE);
		FragmentResturantProfile resturantProfileFragment = new FragmentResturantProfile();
		FragmentResturantProfile.SELECTED_RESTURANT_DTO = resturantDTO;
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				resturantProfileFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

		Log.i(this.getClass().toString(), "inside clicked data");
		Log.i(this.getClass().toString(),
				FragmentResturantProfile.SELECTED_RESTURANT_DTO
						.getResturantName());
	}

	

	public void addResturantListFragment(int SELECTED_CATAGORY,
			double latitude, double longitude) {
		if (SELECTED_CATAGORY == 4) {
			updateUIElements();
			fragmentManager = getSupportFragmentManager();
			fragmentTransaction = fragmentManager.beginTransaction();
			FragmentNearByRestaurantList fragmentResturantList = new FragmentNearByRestaurantList();
			FragmentNearByRestaurantList.latitude = latitude;
			FragmentNearByRestaurantList.longitude = longitude;
			fragmentTransaction.add(R.id.linLayoutFragmentContainer,
					fragmentResturantList, "Resturant List");
			fragmentTransaction.commit();
		} else {
			searchContainer.setVisibility(View.GONE);
			fragmentManager = getSupportFragmentManager();
			fragmentTransaction = fragmentManager.beginTransaction();
			FragmentResturantList fragmentResturantList = new FragmentResturantList();
			FragmentResturantList.selected_catagory = SELECTED_CATAGORY;
			FragmentResturantList.latitude = latitude;
			FragmentResturantList.longitude = longitude;
			fragmentTransaction.add(R.id.linLayoutFragmentContainer,
					fragmentResturantList, "Resturant List");
			fragmentTransaction.commit();
		}
	}

	@Override
	public void setButtonClicked(int restaurantID) {
		// TODO Auto-generated method stub
		searchContainer.setVisibility(View.GONE);
		Fragment restaurantMenuFragment = new FragmentRestaurantMenu();
		FragmentRestaurantMenu.SELECTED_RESTAURANTID = restaurantID;
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				restaurantMenuFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	public void initializeUIElements() {
		adView = (AdView) findViewById(R.id.adView);
		notification = (ImageButton) findViewById(R.id.imageButtonNotification);
		subscribe = (Button) findViewById(R.id.buttonSuscribe);
		homeButton = (ImageButton) findViewById(R.id.imageButtonHomePage);
		calender = (ImageButton) findViewById(R.id.imageButtonCalendar);
		// setContext(RestaurantList.this);

		restaurantFilter = (EditText) findViewById(R.id.editTextSearchResturantLists);
		searchContainer = (LinearLayout) findViewById(R.id.linsearchcontainer);
	}

	@Override
	public void setMenuButtonClicked(List<Integer> selected_Menu_Item_List) {
		// TODO Auto-generated method stub
		searchContainer.setVisibility(View.GONE);
		Fragment fragmentShowCustomerOrder = new FragmentShowCustomerOrder();
		FragmentShowCustomerOrder.SELECTED_MENU_ITEM_LIST = selected_Menu_Item_List;
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				fragmentShowCustomerOrder);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	@Override
	public void setConfirmButtonClicked(MigratingDatas migratingDtos) {
		// TODO Auto-generated method stub
		searchContainer.setVisibility(View.GONE);
		Fragment restaurantMenuFragment = new FragmentRestaurantMenu();
		FragmentRestaurantMenu.migratingdata = migratingDtos;
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				restaurantMenuFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString("message");

			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			 * */
			showNotification();
			// Showing received message

			Toast.makeText(getApplicationContext(),
					"New Message: " + newMessage, Toast.LENGTH_LONG).show();

		}
	};

	@Override
	public void setClickedNearByList(ResturantDTO resturantDTO) {
		// TODO Auto-generated method stub
		showUiElements(resturantDTO);
		FragmentResturantProfile resturantProfileFragment = new FragmentResturantProfile();
		FragmentResturantProfile.SELECTED_RESTURANT_DTO = resturantDTO;
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				resturantProfileFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	private void showUiElements(ResturantDTO resturantDTO) {
		// TODO Auto-generated method stub
		if(resturantDTO.getRegisrered())
		{
		notification.setVisibility(View.VISIBLE);
		subscribe.setVisibility(View.VISIBLE);
		calender.setVisibility(View.VISIBLE);
		homeButton.setVisibility(View.VISIBLE);
		}else{
			updateUIElements();
		}
		
	}
	private void updateUIElements() {
		// TODO Auto-generated method stub
		notification.setVisibility(View.GONE);
		subscribe.setVisibility(View.GONE);
		calender.setVisibility(View.GONE);
		homeButton.setVisibility(View.GONE);
	}

	@Override
	public void setGmapImageButtonClicked(double longitude, double latitude) {
		// TODO Auto-generated method stub
		fragmentGoogleMap = new FragmentGoogleMapRestaurant();
		FragmentGoogleMapRestaurant.LATITUDE=latitude;
		FragmentGoogleMapRestaurant.LONGITUDE=longitude;
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				fragmentGoogleMap);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

}
