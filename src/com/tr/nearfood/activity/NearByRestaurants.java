package com.tr.nearfood.activity;

import com.google.android.gms.ads.AdView;
import com.tr.nearfood.R;
import com.tr.nearfood.fragment.FragmentNearByRestaurantList;
import com.tr.nearfood.fragment.FragmentNearByRestaurantList.FragmentNearByResturantListCommunicator;
import com.tr.nearfood.fragment.FragmentResturantList;
import com.tr.nearfood.fragment.FragmentResturantProfile;
import com.tr.nearfood.fragment.FragmentResturantList.FragmentResturantListCommunicator;
import com.tr.nearfood.model.ResturantDTO;
import com.tr.nearfood.utills.AlertDialogManager;
import com.tr.nearfood.utills.ConnectionDetector;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class NearByRestaurants extends ActionBarActivity implements
		FragmentNearByResturantListCommunicator {
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	ImageButton notification, homeButton, calender;
	Button subscribe;
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	double latitude, longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		initializrUIElements();

		updateUIElements();
		checkConnection();
		Bundle dataFromRestaurantCategory = getIntent().getExtras();
		latitude = dataFromRestaurantCategory.getDouble("latitude");
		longitude = dataFromRestaurantCategory.getDouble("longitude");
		addResturantListFragment(latitude, longitude);

	}

	private void checkConnection() {
		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(NearByRestaurants.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
	}

	private void initializrUIElements() {
		// TODO Auto-generated method stub
		// adView = (AdView) findViewById(R.id.adView);
		notification = (ImageButton) findViewById(R.id.imageButtonNotification);
		subscribe = (Button) findViewById(R.id.buttonSuscribe);
		homeButton = (ImageButton) findViewById(R.id.imageButtonHomePage);
		calender = (ImageButton) findViewById(R.id.imageButtonCalendar);

		/*
		 * restaurantFilter = (EditText)
		 * findViewById(R.id.editTextSearchResturantLists); searchContainer =
		 * (LinearLayout) findViewById(R.id.linsearchcontainer);
		 */
	}

	private void updateUIElements() {
		// TODO Auto-generated method stub
		notification.setVisibility(View.GONE);
		subscribe.setVisibility(View.GONE);
		calender.setVisibility(View.GONE);
		homeButton.setVisibility(View.GONE);
	}

	public void addResturantListFragment(double latitude, double longitude) {
		
	}

	@Override
	public void setClickedNearByList(ResturantDTO resturantDTO) {
		// searchContainer.setVisibility(View.GONE);
		FragmentResturantProfile resturantProfileFragment = new FragmentResturantProfile();
		FragmentResturantProfile.SELECTED_RESTURANT_DTO = resturantDTO;
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				resturantProfileFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

		Log.i(this.getClass().toString(), "inside clicked data");

	}
}
