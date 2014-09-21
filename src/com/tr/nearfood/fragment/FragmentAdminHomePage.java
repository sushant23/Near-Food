package com.tr.nearfood.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.tr.nearfood.R;

public class FragmentAdminHomePage extends Fragment implements OnClickListener {
	public static String AUTH;
	View view;
	SharedPreferences loginDetails;
	Editor editor;
	FragmentResturantAdminHomePageCommunicator fragmentRestaurantAdminHomePageCommunicator;
	// orders
	Button manageOrders, manageAccepted, manageRejected,
			manageRestaurantsDetails;
	// reservation table
	Button pendingReservationTable, confirmedReservationTable,
			rejectedReservationTable, logout;

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
		loginDetails = getActivity().getSharedPreferences("LoginDetails",
				Context.MODE_PRIVATE);
		editor = loginDetails.edit();
		editor.putString("AUTH", AUTH);
		editor.commit();

		initializeUIELements();
		manageOrders.setOnClickListener(this);
		manageAccepted.setOnClickListener(this);
		manageRejected.setOnClickListener(this);
		manageRestaurantsDetails.setOnClickListener(this);

		pendingReservationTable.setOnClickListener(this);
		confirmedReservationTable.setOnClickListener(this);
		rejectedReservationTable.setOnClickListener(this);
		logout.setOnClickListener(this);
		return view;
	}

	private void initializeUIELements() {
		// TODO Auto-generated method stub
		manageOrders = (Button) view.findViewById(R.id.buttonManageOrders);
		manageAccepted = (Button) view.findViewById(R.id.buttonManageCompleted);
		manageRejected = (Button) view.findViewById(R.id.buttonManageRejected);
		manageRestaurantsDetails = (Button) view
				.findViewById(R.id.buttonManageRestaurantDetails);
		pendingReservationTable = (Button) view
				.findViewById(R.id.buttonManagePendingReservation);
		confirmedReservationTable = (Button) view
				.findViewById(R.id.buttonManageCompletedReservation);
		rejectedReservationTable = (Button) view
				.findViewById(R.id.buttonManageRejectedReservation);
		logout = (Button) view.findViewById(R.id.buttonManageRestaurantLogout);
	}

	public static interface FragmentResturantAdminHomePageCommunicator {
		public void setButtonManageOrder(String auth);

		public void setButtonMangeAccepted(String auth);

		public void setButtonMangeRejected(String auth);

		public void setButtonManageDetail(String auth);

		public void setButtonReserveTablePending(String auth);

		public void setButtonReserveTableConfirmed(String auth);

		public void setButtonReserveTableRejected(String auth);

		public void logout();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonManageOrders:
			fragmentRestaurantAdminHomePageCommunicator
					.setButtonManageOrder(AUTH);
			break;

		case R.id.buttonManageCompleted:
			fragmentRestaurantAdminHomePageCommunicator
					.setButtonMangeAccepted(AUTH);
			break;

		case R.id.buttonManageRejected:
			fragmentRestaurantAdminHomePageCommunicator
					.setButtonMangeRejected(AUTH);
			break;

		case R.id.buttonManageRestaurantDetails:
			fragmentRestaurantAdminHomePageCommunicator
					.setButtonManageDetail(AUTH);
			break;

		case R.id.buttonManagePendingReservation:
			fragmentRestaurantAdminHomePageCommunicator
					.setButtonReserveTablePending(AUTH);
			break;

		case R.id.buttonManageCompletedReservation:
			fragmentRestaurantAdminHomePageCommunicator
					.setButtonReserveTableConfirmed(AUTH);
			break;

		case R.id.buttonManageRejectedReservation:
			fragmentRestaurantAdminHomePageCommunicator
					.setButtonReserveTableRejected(AUTH);
			break;
		case R.id.buttonManageRestaurantLogout:
			editor = loginDetails.edit();
			editor.putString("AUTH", "");
			editor.commit();
			fragmentRestaurantAdminHomePageCommunicator.logout();

			break;
		default:
			break;
		}
	}
}
