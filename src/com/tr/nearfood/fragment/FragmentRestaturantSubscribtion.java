package com.tr.nearfood.fragment;

import com.tr.nearfood.R;
import com.tr.nearfood.fragment.FragmentResturantProfile.FragmentResturantProfileCommunicator;
import com.tr.nearfood.utills.ActivityLayoutAdjuster;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FragmentRestaturantSubscribtion extends Fragment implements
		OnClickListener, OnCheckedChangeListener {

	View view;
	FragmentResturantSubscribtionCommunicator fragmentRestaurantSubscribtionCommunicator;
	Button adminLogin;
	RadioGroup restaurantLocationRadioGroup;
	RadioButton currentLocation, locationByCoordinate, locationFromGoogleMap;
	public static double longitude,latitude;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {

			fragmentRestaurantSubscribtionCommunicator = (FragmentResturantSubscribtionCommunicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					activity.toString()
							+ " must implement FragmentResturantSubscribtionCommunicator");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ActivityLayoutAdjuster.assistActivity(getActivity());
		view = inflater.inflate(R.layout.fragment_restaurant_subscribtion,
				container, false);
		initializeUIElsments();

		if(longitude!=0&&latitude!=0)
			Toast.makeText(getActivity(), "Longitude="+longitude+"Latitude="+latitude, Toast.LENGTH_SHORT).show();
		restaurantLocationRadioGroup.setOnCheckedChangeListener(this);

		adminLogin.setOnClickListener(this);
		return view;
	}

	private void initializeUIElsments() {
		// TODO Auto-generated method stub
		adminLogin = (Button) view
				.findViewById(R.id.buttonRestaurantAdminLogin);
		restaurantLocationRadioGroup = (RadioGroup) view
				.findViewById(R.id.radioGroupRestaurantLocationChooser);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonRestaurantAdminLogin:
			fragmentRestaurantSubscribtionCommunicator.setButtonAdminLogin();
			Log.d("Subscribtion", "BUTTOn IS CLICKED");
			break;

		default:
			break;
		}
	}

	public static interface FragmentResturantSubscribtionCommunicator {
		public void setButtonAdminLogin();

		public void setGetCoordinateFromMap();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.radioButtonUseMap:
			fragmentRestaurantSubscribtionCommunicator
					.setGetCoordinateFromMap();
			break;

		default:
			break;
		}

	}
}
