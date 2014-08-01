package com.tr.nearfood.activity;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer.InitiateMatchResult;
import com.tr.nearfood.R;
import com.tr.nearfood.adapter.PlaceAutoCompleteAdapter;
import com.tr.nearfood.fragment.FragmentAdminHomePage;
import com.tr.nearfood.fragment.FragmentAdminHomePage.FragmentResturantAdminHomePageCommunicator;
import com.tr.nearfood.fragment.FragmentAdminLogin;
import com.tr.nearfood.fragment.FragmentAdminLogin.FragmentResturantAdminLoginCommunicator;
import com.tr.nearfood.fragment.FragmentAdminManageMenu;
import com.tr.nearfood.fragment.FragmentAdminManageRestaurantDetails;
import com.tr.nearfood.fragment.FragmentGoogleMap;
import com.tr.nearfood.fragment.FragmentGoogleMap.FragmentGoogleMapListener;
import com.tr.nearfood.fragment.FragmentRestaturantSubscribtion;
import com.tr.nearfood.fragment.FragmentRestaurantMenu;
import com.tr.nearfood.fragment.FragmentRestaturantSubscribtion.FragmentResturantSubscribtionCommunicator;
import com.tr.nearfood.fragment.FragmentResturantList;
import com.tr.nearfood.fragment.FragmentResturantProfile.FragmentResturantProfileCommunicator;
import com.tr.nearfood.fragment.FragmentsRestaurantAdminManageOrder;
import com.tr.nearfood.utills.BadgeView;
import com.tr.nearfood.utills.NearFoodTextView;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class RestaurantSubscribtion extends ActionBarActivity implements
		FragmentResturantSubscribtionCommunicator,
		FragmentResturantAdminLoginCommunicator,
		FragmentResturantAdminHomePageCommunicator, FragmentGoogleMapListener {
	FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	AdView adView;
	ImageButton notification, homeButton, calender;
	Button subscribe;
	BadgeView badge, badge1;
	Button signIn;
	AutoCompleteTextView searchplace;
	LinearLayout searchContainer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		InitilizeUIElements();

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		NearFoodTextView.setDefaultFont(this, "DEFAULT", "Roboto-Regular.ttf");
		try {
			ActionBar myActionBar = getSupportActionBar();
			myActionBar.hide();
		} catch (Exception e) {
			e.printStackTrace();
		}
		AdRequest adRequest = new AdRequest.Builder().build();

		adView.loadAd(adRequest);
		addResturantSubscribtionFragment();

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

	private void InitilizeUIElements() {
		// TODO Auto-generated method stub
		adView = (AdView) findViewById(R.id.adView);
		signIn = (Button) findViewById(R.id.buttonRestaurantAdminLogin);
		subscribe = (Button) findViewById(R.id.buttonSuscribe);
		homeButton = (ImageButton) findViewById(R.id.imageButtonHomePage);
		searchplace = (AutoCompleteTextView) findViewById(R.id.editTextSearchResturantLists);
		searchContainer=(LinearLayout) findViewById(R.id.linsearchcontainer);
	}

	private void addResturantSubscribtionFragment() {
		// TODO Auto-generated method stub
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		FragmentRestaturantSubscribtion fragmentResturantSubscribtion = new FragmentRestaturantSubscribtion();

		fragmentTransaction.add(R.id.linLayoutFragmentContainer,
				fragmentResturantSubscribtion, "Resturant ");
		fragmentTransaction.commit();
	}

	@Override
	public void setButtonAdminLogin() {
		// TODO Auto-generated method stub
		Fragment restaurantAdminLogin = new FragmentAdminLogin();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				restaurantAdminLogin);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

	}

	@Override
	public void setButtonSignin() {
		// TODO Auto-generated method stub
		Fragment restaurantAdminhomepage = new FragmentAdminHomePage();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				restaurantAdminhomepage);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

	}

	@Override
	public void setButtonManageOrder() {
		// TODO Auto-generated method stub
		Fragment restaurantAdminmanageOrder = new FragmentsRestaurantAdminManageOrder();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				restaurantAdminmanageOrder);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	@Override
	public void setButtonMangeMenu() {
		// TODO Auto-generated method stub
		Fragment restaurantAdminmanageMenu = new FragmentAdminManageMenu();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				restaurantAdminmanageMenu);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	@Override
	public void setButtonManageDetail() {
		// TODO Auto-generated method stub
		Fragment restaurantAdminmanageRestaurantDetails = new FragmentAdminManageRestaurantDetails();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				restaurantAdminmanageRestaurantDetails);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	@Override
	public void setGetCoordinateFromMap() {
		// TODO Auto-generated method stub
		Fragment fragmentGoogleMap = new FragmentGoogleMap();
		searchContainer.setVisibility(View.GONE);
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				fragmentGoogleMap);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	@Override
	public void onLocationSetClicked(double longitude, double latitude) {
		// TODO Auto-generated method stub
		Fragment restaurantSubscribtion = new FragmentRestaturantSubscribtion();
		FragmentRestaturantSubscribtion.longitude = longitude;
		FragmentRestaturantSubscribtion.latitude = latitude;
		searchContainer.setVisibility(View.VISIBLE);
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				restaurantSubscribtion);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

}
