package com.tr.nearfood.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.tr.nearfood.model.ResturantDTO;

public class FragmentResturantProfile extends Fragment implements
		OnClickListener {
	View view;
	TextView restaurantName, restaurantStreetAdd, restaurantCityName,
			restaurantDistance, restaurantPhoneNumber;
	Button sendMessage, chooseMenu, setDateAndTime, reserveTable;
	EditText senderName, senderEmail, senderPhone, senderMessage;
	FragmentResturantProfileCommunicator fragmentResturantProfileCommunicator;

	public static ResturantDTO SELECTED_RESTURANT_DTO;

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
		Log.i(getActivity().getClass().toString(), "oncreate view fragment resturant profile");
		setSelectedData(SELECTED_RESTURANT_DTO);
		chooseMenu.setOnClickListener(this);
		sendMessage.setOnClickListener(this);
		setDateAndTime.setOnClickListener(this);
		reserveTable.setOnClickListener(this);
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

	}

	@Override
	public void onClick(View click) {
		// TODO Auto-generated method stub
		switch (click.getId()) {
		case R.id.buttonChooseMenu:
			fragmentResturantProfileCommunicator.setButtonClicked();
			// Toast.makeText(getActivity(), "Choose mnu clicked", 1000).show();
			break;
		case R.id.buttonSendMessage:
			Toast.makeText(getActivity(), "Message Send Button clicked",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.buttonReserveTable:
			Toast.makeText(getActivity(), "Reserve Table Button clicked",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.buttonSetTimeandDate:
			Toast.makeText(getActivity(), "Time and Date Button clicked",
					Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

	public void setSelectedData(ResturantDTO resturantDTO) {
		restaurantName.setText(SELECTED_RESTURANT_DTO.getResturantName());
		restaurantStreetAdd.setText(SELECTED_RESTURANT_DTO.getResturantAddress()
				.getResturantStreetAddress());
		restaurantCityName.setText(SELECTED_RESTURANT_DTO.getResturantAddress()
				.getReturantCityName());
		restaurantDistance.setText(String.valueOf(SELECTED_RESTURANT_DTO
				.getResturantAddress().getResturantDistance()));
		restaurantPhoneNumber.setText(SELECTED_RESTURANT_DTO
				.getResturantContactInfo().getResturantphoneNoA());
	}

	public static interface FragmentResturantProfileCommunicator {
		public void setButtonClicked();
	}

}
