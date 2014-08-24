package com.tr.nearfood.activity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tr.nearfood.R;
import com.tr.nearfood.fragment.FragmentNotification;
import com.tr.nearfood.fragment.FragmentRestaurantMenu;
import com.tr.nearfood.fragment.FragmentRestaurantMenu.FragmentResturantMenuListCommunicator;
import com.tr.nearfood.fragment.FragmentResturantList;
import com.tr.nearfood.fragment.FragmentResturantList.FragmentResturantListCommunicator;
import com.tr.nearfood.fragment.FragmentResturantProfile;
import com.tr.nearfood.fragment.FragmentResturantProfile.FragmentResturantProfileCommunicator;
import com.tr.nearfood.fragment.FragmentShowCustomerOrder.FragmentShowCustomerOrderCommunicator;
import com.tr.nearfood.fragment.FragmentShowCustomerOrder;
import com.tr.nearfood.model.MigratingDatas;
import com.tr.nearfood.model.ResturantDTO;
import com.tr.nearfood.utills.BadgeView;
import com.tr.nearfood.utills.NearFoodTextView;

public class RestaurantList extends ActionBarActivity implements
		FragmentResturantListCommunicator,
		FragmentResturantProfileCommunicator,
		FragmentResturantMenuListCommunicator,
		FragmentShowCustomerOrderCommunicator {
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	AdView adView;
	ImageButton notification, homeButton, calender;
	int SELECTED_CATEGORY = 0;
	BadgeView badge, badge1;
	Button subscribe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		Bundle dataFromRestaurantCategory = getIntent().getExtras();
		SELECTED_CATEGORY = dataFromRestaurantCategory.getInt("catagory_id");

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
		addResturantListFragment(SELECTED_CATEGORY);
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
				badge.hide();
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					notification.setColorFilter(Color.argb(150, 155, 155, 155));
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

					Intent openCalenderActivity = new Intent(
							getApplicationContext(), CustumCalenderEvents.class);
					startActivity(openCalenderActivity);
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
						RestaurantSubscribtion.class);
				startActivity(subscribeActivity);
			}
		});

	}

	void showNotification() {
		badge = new BadgeView(this, notification);
		badge.setText("5");
		badge.setBadgePosition(BadgeView.POSITION_BOTTOM_LEFT);
		badge.show();

	}

	@Override
	public void setClickedData(ResturantDTO resturantDTO) {
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

	public void addResturantListFragment(int SELECTED_CATAGORY) {
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		FragmentResturantList fragmentResturantList = new FragmentResturantList();
		FragmentResturantList.selected_catagory = SELECTED_CATAGORY;
		fragmentTransaction.add(R.id.linLayoutFragmentContainer,
				fragmentResturantList, "Resturant List");
		fragmentTransaction.commit();
	}

	@Override
	public void setButtonClicked(int restaurantID, String setDateTime) {
		// TODO Auto-generated method stub
		Fragment restaurantMenuFragment = new FragmentRestaurantMenu();
		FragmentRestaurantMenu.SELECTED_RESTAURANTID = restaurantID;
		FragmentRestaurantMenu.SETDATETIME = setDateTime;
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
	}

	@Override
	public void setMenuButtonClicked(List<Integer> selected_Menu_Item_List) {
		// TODO Auto-generated method stub

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
		Fragment restaurantMenuFragment = new FragmentRestaurantMenu();
		FragmentRestaurantMenu.migratingdata = migratingDtos;
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				restaurantMenuFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

}
