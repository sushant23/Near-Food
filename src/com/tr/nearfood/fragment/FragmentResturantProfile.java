package com.tr.nearfood.fragment;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tr.nearfood.R;
import com.tr.nearfood.dbhelper.DatabaseHelper;
import com.tr.nearfood.model.ResturantDTO;
import com.tr.nearfood.utills.CustomDateTimePicker;

public class FragmentResturantProfile extends Fragment implements
		OnClickListener {
	View view;

	TextView restaurantName, restaurantStreetAdd, restaurantCityName,
			restaurantDistance, restaurantPhoneNumber;
	Button sendMessage, chooseMenu, setDateAndTime, reserveTable;
	EditText senderName, senderEmail, senderPhone, senderMessage;
	FragmentResturantProfileCommunicator fragmentResturantProfileCommunicator;
	// WebView tripadvisor;
	public static ResturantDTO SELECTED_RESTURANT_DTO;
	String datetime=null;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {

			fragmentResturantProfileCommunicator = (FragmentResturantProfileCommunicator) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentResturantProfileCommunicator");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_resturant_profile, container,
				false);

		initializeUIElements();
		Log.i(getActivity().getClass().toString(),
				"oncreate view fragment resturant profile");
		setSelectedData(SELECTED_RESTURANT_DTO);
		chooseMenu.setOnClickListener(this);
		sendMessage.setOnClickListener(this);
		setDateAndTime.setOnClickListener(this);
		reserveTable.setOnClickListener(this);

		restaurantPhoneNumber.setOnClickListener(this);
		// tripadvisor.getSettings().setJavaScriptEnabled(true);
		// tripadvisor.loadUrl("http://192.168.0.101/hello/tripadvisor.html");
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	}

	private void initializeUIElements() {

		sendMessage = (Button) view.findViewById(R.id.buttonSendMessage);
		chooseMenu = (Button) view.findViewById(R.id.buttonChooseMenu);
		setDateAndTime = (Button) view.findViewById(R.id.buttonSetTimeandDate);
		reserveTable = (Button) view.findViewById(R.id.buttonReserveTable);

		restaurantName = (TextView) view
				.findViewById(R.id.textviewReataurantName);
		restaurantStreetAdd = (TextView) view
				.findViewById(R.id.textviewReataurantStreetAddress);
		restaurantCityName = (TextView) view
				.findViewById(R.id.textviewReataurantCityName);
		restaurantDistance = (TextView) view
				.findViewById(R.id.textViewResturantDistance);
		restaurantPhoneNumber = (TextView) view
				.findViewById(R.id.textViewContactPhoneNumber);

		senderName = (EditText) view.findViewById(R.id.edittextSenderName);
		senderEmail = (EditText) view.findViewById(R.id.edittextSenderEmail);
		senderPhone = (EditText) view.findViewById(R.id.edittextSenderPhone);
		senderMessage = (EditText) view.findViewById(R.id.edittextSendmessege);

		// tripadvisor=(WebView) view.findViewById(R.id.webViewtripAdvisor);
	}

	@Override
	public void onClick(View click) {
		// TODO Auto-generated method stub
		switch (click.getId()) {
		case R.id.buttonChooseMenu:
			if (datetime==null)
				Toast.makeText(getActivity(), "Please Set The Data and Time",
						Toast.LENGTH_SHORT).show();
			else {
				
				fragmentResturantProfileCommunicator
						.setButtonClicked(SELECTED_RESTURANT_DTO
								.getResturantID(),datetime);
			}
			// Toast.makeText(getActivity(), "Choose mnu clicked", 1000).show();
			break;
		case R.id.buttonSendMessage:
			if (senderName.getText().toString().matches("")
					|| senderEmail.getText().toString().matches("")
					|| senderPhone.getText().toString().matches("")
					|| senderMessage.getText().toString().matches("")) {
				Toast.makeText(getActivity(),
						"Please Enter All Field Correctly", Toast.LENGTH_SHORT)
						.show();

			} else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
					senderEmail.getText().toString()).matches()
					&& !TextUtils.isEmpty(senderEmail.getText().toString())) {
				senderEmail.setError("Invalid Email");
				senderEmail.requestFocus();
				return;
			} else {
				Toast.makeText(getActivity(), "Messege sent ",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.buttonReserveTable:
			Toast.makeText(getActivity(), "Reserve Table Button clicked",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.buttonSetTimeandDate:

			CustomDateTimePicker custom = new CustomDateTimePicker(
					getActivity(),
					new CustomDateTimePicker.ICustomDateTimeListener() {

						@Override
						public void onSet(Dialog dialog,
								Calendar calendarSelected, Date dateSelected,
								int year, String monthFullName,
								String monthShortName, int monthNumber,
								int date, String weekDayFullName,
								String weekDayShortName, int hour24,
								int hour12, int min, int sec, String AM_PM) {
							// TODO Auto-generated method stub
							datetime = (calendarSelected
									.get(Calendar.DAY_OF_MONTH)
									+ "/"
									+ (monthNumber + 1)
									+ "/"
									+ year
									+ ","
									+ hour12 + ":" + min + " " + AM_PM);
							Toast.makeText(getActivity(), datetime,
									Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onCancel() {
							// TODO Auto-generated method stub

						}
					});
			custom.showDialog();
			break;
		case R.id.textViewContactPhoneNumber:

			Intent callIntent1 = new Intent(Intent.ACTION_CALL);
			callIntent1.setData(Uri.parse("tel:"
					+ restaurantPhoneNumber.getText().toString().trim()));
			startActivity(callIntent1);
			break;

		default:
			break;
		}
	}

	public void setSelectedData(ResturantDTO resturantDTO) {
		restaurantName.setText(SELECTED_RESTURANT_DTO.getResturantName());
		restaurantStreetAdd.setText(SELECTED_RESTURANT_DTO
				.getResturantAddress().getResturantStreetAddress());
		restaurantCityName.setText(SELECTED_RESTURANT_DTO.getResturantAddress()
				.getReturantCityName());
		restaurantDistance.setText(String.valueOf(SELECTED_RESTURANT_DTO
				.getResturantAddress().getResturantDistance()));
		restaurantPhoneNumber.setText(SELECTED_RESTURANT_DTO
				.getResturantContactInfo().getResturantphoneNoA());
	}

	public static interface FragmentResturantProfileCommunicator {
		public void setButtonClicked(int restaurantID,String dateTime);
	}

}
