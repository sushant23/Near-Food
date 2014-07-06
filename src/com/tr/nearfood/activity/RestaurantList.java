package com.tr.nearfood.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.tr.nearfood.R;
import com.tr.nearfood.fragment.FragmentRestaurantMenu;
import com.tr.nearfood.fragment.FragmentRestaurantMenu.FragmentResturantMenuListCommunicator;
import com.tr.nearfood.fragment.FragmentResturantList;
import com.tr.nearfood.fragment.FragmentResturantList.FragmentResturantListCommunicator;
import com.tr.nearfood.fragment.FragmentResturantProfile;
import com.tr.nearfood.fragment.FragmentResturantProfile.FragmentResturantProfileCommunicator;
import com.tr.nearfood.fragment.FragmentShowCustomerOrder;
import com.tr.nearfood.model.ResturantDTO;
import com.tr.nearfood.utills.BadgeView;
import com.tr.nearfood.utills.NearFoodTextView;

public class RestaurantList extends ActionBarActivity implements
		FragmentResturantListCommunicator,
		FragmentResturantProfileCommunicator,
		FragmentResturantMenuListCommunicator {
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	AdView adView;
	ImageButton notification;
	BadgeView badge, badge1;
	Button subscribe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

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
		addResturantListFragment();

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

	public void addResturantListFragment() {
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		FragmentResturantList fragmentResturantList = new FragmentResturantList();

		fragmentTransaction.add(R.id.linLayoutFragmentContainer,
				fragmentResturantList, "Resturant List");
		fragmentTransaction.commit();
	}

	@Override
	public void setButtonClicked() {
		// TODO Auto-generated method stub
		Fragment restaurantMenuFragment = new FragmentRestaurantMenu();
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
	}

	@Override
	public void setMenuButtonClicked() {
		// TODO Auto-generated method stub

		Fragment fragmentShowCustomerOrder = new FragmentShowCustomerOrder();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				fragmentShowCustomerOrder);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

}
