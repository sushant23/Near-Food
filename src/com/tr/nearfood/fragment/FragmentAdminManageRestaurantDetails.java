package com.tr.nearfood.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tr.nearfood.R;

public class FragmentAdminManageRestaurantDetails extends Fragment implements
		OnClickListener {
	TextView tvrestaurantStreetAddress, tvrestaurantCityAddress,
			tvrestaurantEmailAddress, tvrestaurantContactNumber;

	Button bEditRestaurantStreetAddress, bEditRestaurantCityAddress,
			bEditRestaurantEmailAddress, bEditRestaurantContactNumber;

	EditText etrestaurantStreetAddress, etrestaurantCityAddress,
			etrestaurantEmailAddress, etrestaurantContactNumber;

	Button bCommitRestaurantStreetAddress, bCommitRestaurantCityAddress,
			bCommitRestaurantEmailAddress, bCommitRestaurantContactNumber;

	LinearLayout lLayoutRestaurantStreetAddress, lLayoutRestaurantCityAddress,
			lLayoutRestaurantEmailAddress, lLayoutRestaurantContactNumber;

	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_restaurant_details_edit,
				container, false);
		initializeUIElements();

		bEditRestaurantCityAddress.setOnClickListener(this);
		bEditRestaurantContactNumber.setOnClickListener(this);
		bEditRestaurantEmailAddress.setOnClickListener(this);
		bEditRestaurantStreetAddress.setOnClickListener(this);

		bCommitRestaurantCityAddress.setOnClickListener(this);
		bCommitRestaurantContactNumber.setOnClickListener(this);
		bCommitRestaurantEmailAddress.setOnClickListener(this);
		bCommitRestaurantStreetAddress.setOnClickListener(this);

		return view;
	}

	private void initializeUIElements() {
		// TODO Auto-generated method stub
		tvrestaurantCityAddress = (TextView) view
				.findViewById(R.id.tvEditRestaurantCityAddress);
		tvrestaurantEmailAddress = (TextView) view
				.findViewById(R.id.tvEditRestaurantEmailAddress);
		tvrestaurantContactNumber = (TextView) view
				.findViewById(R.id.tvEditRestaurantContactNumber);
		tvrestaurantStreetAddress = (TextView) view
				.findViewById(R.id.tvEditRestaurantStreetAddress);

		etrestaurantStreetAddress = (EditText) view
				.findViewById(R.id.editTextchangedRestaurantStreetAddresss);
		etrestaurantCityAddress = (EditText) view
				.findViewById(R.id.editTextchangedRestaurantCityAddresss);
		etrestaurantEmailAddress = (EditText) view
				.findViewById(R.id.editTextchangedRestaurantEmailAddresss);
		etrestaurantContactNumber = (EditText) view
				.findViewById(R.id.editTextchangedRestaurantContactNumber);

		bEditRestaurantStreetAddress = (Button) view
				.findViewById(R.id.bRestaurantStreetAddressEdit);
		bEditRestaurantCityAddress = (Button) view
				.findViewById(R.id.bRestaurantCityAddressEdit);
		bEditRestaurantEmailAddress = (Button) view
				.findViewById(R.id.bRestaurantEmailAddressEdit);
		bEditRestaurantContactNumber = (Button) view
				.findViewById(R.id.bRestaurantContactNumberEdit);

		bCommitRestaurantStreetAddress = (Button) view
				.findViewById(R.id.bRestaurantStreetAddressCommit);
		bCommitRestaurantCityAddress = (Button) view
				.findViewById(R.id.bRestaurantCityAddressCommit);
		bCommitRestaurantEmailAddress = (Button) view
				.findViewById(R.id.bRestaurantEmailAddressCommit);
		bCommitRestaurantContactNumber = (Button) view
				.findViewById(R.id.bRestaurantContactNumberCommit);

		lLayoutRestaurantStreetAddress = (LinearLayout) view
				.findViewById(R.id.lineditLayoutRestaurantStreetAddressEditPanel);
		lLayoutRestaurantCityAddress = (LinearLayout) view
				.findViewById(R.id.lineditLayoutRestaurantCityAddressEditPanel);
		lLayoutRestaurantEmailAddress = (LinearLayout) view
				.findViewById(R.id.lineditLayoutRestaurantEmailAddressEditPanel);
		lLayoutRestaurantContactNumber = (LinearLayout) view
				.findViewById(R.id.lineditLayoutRestaurantContactNumberEditPanel);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bRestaurantStreetAddressEdit:
			lLayoutRestaurantStreetAddress.setVisibility(View.VISIBLE);

			break;
		case R.id.bRestaurantStreetAddressCommit:
			String restaurantStreetAddress = etrestaurantStreetAddress
					.getText().toString();
			tvrestaurantStreetAddress.setText(restaurantStreetAddress);
			lLayoutRestaurantStreetAddress.setVisibility(View.GONE);
			break;
		case R.id.bRestaurantCityAddressEdit:
			lLayoutRestaurantCityAddress.setVisibility(View.VISIBLE);
			break;
		case R.id.bRestaurantCityAddressCommit:
			String restaurantCityAddress = etrestaurantCityAddress.getText()
					.toString();
			tvrestaurantEmailAddress.setText(restaurantCityAddress);
			lLayoutRestaurantCityAddress.setVisibility(View.GONE);
			break;
		case R.id.bRestaurantEmailAddressEdit:
			lLayoutRestaurantEmailAddress.setVisibility(View.VISIBLE);
			break;
		case R.id.bRestaurantEmailAddressCommit:
			String restaurantEmailAddress = etrestaurantEmailAddress.getText()
					.toString();
			tvrestaurantEmailAddress.setText(restaurantEmailAddress);
			lLayoutRestaurantEmailAddress.setVisibility(View.GONE);
			break;
		case R.id.bRestaurantContactNumberEdit:
			lLayoutRestaurantContactNumber.setVisibility(View.VISIBLE);
			break;
		case R.id.bRestaurantContactNumberCommit:
			String restaurantContactNumber = etrestaurantContactNumber
					.getText().toString();
			tvrestaurantContactNumber.setText(restaurantContactNumber);
			lLayoutRestaurantContactNumber.setVisibility(View.GONE);
			break;
		default:
			break;
		}
	}

}
