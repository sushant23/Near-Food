package com.tr.nearfood.fragment;

import com.tr.nearfood.R;
import com.tr.nearfood.fragment.FragmentAdminLogin.FragmentResturantAdminLoginCommunicator;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentAdminHomePage extends Fragment implements OnClickListener {
	View view;
	FragmentResturantAdminHomePageCommunicator fragmentRestaurantAdminHomePageCommunicator;
	Button manageOrders, manageMenu, manageRestaurantsDetails;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {

			fragmentRestaurantAdminHomePageCommunicator = (FragmentResturantAdminHomePageCommunicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					activity.toString()
							+ " must implement FragmentResturantAdminHomePAgeCommunicator");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_restaurant_manager_homepage,
				container, false);
		initializeUIELements();
		manageOrders.setOnClickListener(this);
		manageMenu.setOnClickListener(this);
		manageRestaurantsDetails.setOnClickListener(this);
		return view;
	}

	private void initializeUIELements() {
		// TODO Auto-generated method stub
		manageOrders = (Button) view.findViewById(R.id.buttonManageOrders);
		manageMenu = (Button) view.findViewById(R.id.buttonManageMenus);
		manageRestaurantsDetails = (Button) view
				.findViewById(R.id.buttonManageRestaurantDetails);
	}

	public static interface FragmentResturantAdminHomePageCommunicator {
		public void setButtonManageOrder();

		public void setButtonMangeMenu();
		public void setButtonManageDetail();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonManageOrders:
			fragmentRestaurantAdminHomePageCommunicator.setButtonManageOrder();

			break;
		case R.id.buttonManageMenus:
			fragmentRestaurantAdminHomePageCommunicator.setButtonMangeMenu();
			break;
		case R.id.buttonManageRestaurantDetails:
			fragmentRestaurantAdminHomePageCommunicator.setButtonManageDetail();
		default:
			break;
		}
	}
}
