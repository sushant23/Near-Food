package com.tr.nearfood.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.tr.nearfood.R;
import com.tr.nearfood.fragment.FragmentRestaurantMenu;
import com.tr.nearfood.fragment.FragmentResturantList;
import com.tr.nearfood.fragment.FragmentResturantList.FragmentResturantListCommunicator;
import com.tr.nearfood.fragment.FragmentResturantProfile.FragmentResturantProfileCommunicator;
import com.tr.nearfood.fragment.FragmentResturantProfile;
import com.tr.nearfood.model.ResturantDTO;

public class RestaurantList extends ActionBarActivity implements
		FragmentResturantListCommunicator,FragmentResturantProfileCommunicator {
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		try {
			ActionBar myActionBar = getSupportActionBar();
			myActionBar.hide();
		} catch (Exception e) {
			e.printStackTrace();
		}

		addResturantListFragment();

	}

	@Override
	public void setClickedData(ResturantDTO resturantDTO) {
		Fragment resturantProfileFragment = new FragmentResturantProfile();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				resturantProfileFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
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
		Fragment restaurantMenuFragment= new FragmentRestaurantMenu();
		fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.linLayoutFragmentContainer,
				restaurantMenuFragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

}
