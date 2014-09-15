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
import android.widget.Toast;

public class FragmentAdminHomePage extends Fragment implements OnClickListener {
	public static String AUTH;
	View view;
	FragmentResturantAdminHomePageCommunicator fragmentRestaurantAdminHomePageCommunicator;
//orders
	Button manageOrders, manageAccepted,manageRejected, manageRestaurantsDetails;
//reservation table
	Button pendingReservationTable,confirmedReservationTable,rejectedReservationTable;
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
		manageAccepted.setOnClickListener(this);
		manageRejected.setOnClickListener(this);
		manageRestaurantsDetails.setOnClickListener(this);
		
		pendingReservationTable.setOnClickListener(this);
		confirmedReservationTable.setOnClickListener(this);
		rejectedReservationTable.setOnClickListener(this);
		return view;
	}

	private void initializeUIELements() {
		// TODO Auto-generated method stub
		manageOrders = (Button) view.findViewById(R.id.buttonManageOrders);
		manageAccepted = (Button) view.findViewById(R.id.buttonManageCompleted);
		manageRejected = (Button) view.findViewById(R.id.buttonManageRejected);
		manageRestaurantsDetails = (Button) view
				.findViewById(R.id.buttonManageRestaurantDetails);
		pendingReservationTable=(Button) view.findViewById(R.id.buttonManagePendingReservation);
		confirmedReservationTable=(Button) view.findViewById(R.id.buttonManageCompletedReservation);
		rejectedReservationTable=(Button) view.findViewById(R.id.buttonManageRejectedReservation);
	}

	public static interface FragmentResturantAdminHomePageCommunicator {
		public void setButtonManageOrder(String auth);

		public void setButtonMangeAccepted(String auth);
		public void setButtonMangeRejected(String auth);
		public void setButtonManageDetail(String auth);
		
		public void setButtonReserveTablePending(String auth);
		public void setButtonReserveTableConfirmed(String auth);
		public void setButtonReserveTableRejected(String auth);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonManageOrders:
			fragmentRestaurantAdminHomePageCommunicator.setButtonManageOrder(AUTH);
			break;

		case R.id.buttonManageCompleted:
			fragmentRestaurantAdminHomePageCommunicator.setButtonMangeAccepted(AUTH);
			break;
		
		case R.id.buttonManageRejected:
			fragmentRestaurantAdminHomePageCommunicator.setButtonMangeRejected(AUTH);
			break;
		
		case R.id.buttonManageRestaurantDetails:
			fragmentRestaurantAdminHomePageCommunicator.setButtonManageDetail(AUTH);
			break;
		
		case R.id.buttonManagePendingReservation:
				fragmentRestaurantAdminHomePageCommunicator.setButtonReserveTablePending(AUTH);
			break;
		
		case R.id.buttonManageCompletedReservation:
				fragmentRestaurantAdminHomePageCommunicator.setButtonReserveTableConfirmed(AUTH);
			break;
		
		case R.id.buttonManageRejectedReservation:
				fragmentRestaurantAdminHomePageCommunicator.setButtonReserveTableRejected(AUTH);
			break;
			
		default:
			break;
		}
	}
}
