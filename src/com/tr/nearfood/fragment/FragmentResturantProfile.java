package com.tr.nearfood.fragment;

import com.tr.nearfood.R;
import com.tr.nearfood.activity.RestaurantList;
import com.tr.nearfood.model.ResturantDTO;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentResturantProfile extends Fragment {
	View view;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_resturant_profile, container,
				false);
		initializeUIElements();

		RestaurantList restaurantList = (RestaurantList) getActivity();
		ResturantDTO selectedResturantDTO = restaurantList
				.getSelectedResturantDTO();

		

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	private void initializeUIElements() {
		
	}

}
