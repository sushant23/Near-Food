package com.tr.nearfood.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.tr.nearfood.R;

public class FragmentAdminLogin extends Fragment implements OnClickListener {
	View view;

	FragmentResturantAdminLoginCommunicator fragmentResturantAdminLoginCommunicator;
	Button signin;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {

			fragmentResturantAdminLoginCommunicator = (FragmentResturantAdminLoginCommunicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentResturantAdminLOginCommunicator");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_restaurant_admin_login,
				container, false);
		initializeUIElements();
		signin.setOnClickListener(this);
		return view;
	}

	private void initializeUIElements() {
		// TODO Auto-generated method stub
		signin = (Button) view.findViewById(R.id.buttonRestaurantManagerLogin);
	}

	public static interface FragmentResturantAdminLoginCommunicator {
		public void setButtonSignin();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonRestaurantManagerLogin:
			fragmentResturantAdminLoginCommunicator.setButtonSignin();
			break;

		default:
			break;
		}
	}
}