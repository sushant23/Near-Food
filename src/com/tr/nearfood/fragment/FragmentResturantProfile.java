package com.tr.nearfood.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.tr.nearfood.R;
import com.tr.nearfood.activity.RestaurantCatagory;
import com.tr.nearfood.activity.RestaurantSubscribtion;
import com.tr.nearfood.dbhelper.DatabaseHelper;
import com.tr.nearfood.fragment.FragmentAdminManageRestaurantDetails.RegistrationDetails;
import com.tr.nearfood.model.ResturantDTO;
import com.tr.nearfood.pushnotification.MainActivity;
import com.tr.nearfood.utills.AppConstants;
import com.tr.nearfood.utills.CustomDateTimePicker;
import com.tr.nearfood.utills.SetEventToCalandar;

public class FragmentResturantProfile extends Fragment implements
		OnClickListener {
	View view;

	TextView restaurantName, restaurantStreetAdd, restaurantCityName,
			restaurantDistance, restaurantPhoneNumber, contact;
	Button sendMessage, chooseMenu, setDateAndTime, reserveTable;
	EditText senderName, senderEmail, senderPhone, senderMessage;
	FragmentResturantProfileCommunicator fragmentResturantProfileCommunicator;
	GoogleCloudMessaging gcm;
	// WebView tripadvisor;
	public static ResturantDTO SELECTED_RESTURANT_DTO;
	public static String datetime = null;
	SetEventToCalandar writeEvent = new SetEventToCalandar();
	LinearLayout restaurantOperation, reserveTableUserDetail;
	ImageButton subscribe;

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

		if (SELECTED_RESTURANT_DTO.getRegisrered()) {
			restaurantOperation.setVisibility(View.VISIBLE);
			subscribe.setVisibility(View.GONE);
			reserveTableUserDetail.setVisibility(View.VISIBLE);
			senderMessage.setVisibility(View.VISIBLE);
			sendMessage.setVisibility(View.VISIBLE);
			contact.setVisibility(View.VISIBLE);

		} else {
			restaurantOperation.setVisibility(View.GONE);
			subscribe.setVisibility(View.VISIBLE);
			reserveTableUserDetail.setVisibility(View.GONE);
			senderMessage.setVisibility(View.GONE);
			sendMessage.setVisibility(View.GONE);
			contact.setVisibility(View.GONE);
		}
		restaurantPhoneNumber.setOnClickListener(this);
		subscribe.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent me) {
				// TODO Auto-generated method stub
				if (me.getAction() == MotionEvent.ACTION_DOWN) {
					subscribe.setColorFilter(Color.argb(150, 155, 155, 155));

					Intent start = new Intent(getActivity(),
							RestaurantSubscribtion.class);
					start.putExtra("registerNearBy", true);
					startActivity(start);

					return true;
				} else if (me.getAction() == MotionEvent.ACTION_UP) {
					subscribe.setColorFilter(Color.argb(0, 155, 155, 155)); // or
																			// null
					return true;
				}
				return false;
			}

		});

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
		contact = (TextView) view.findViewById(R.id.textViewContact);
		senderName = (EditText) view.findViewById(R.id.edittextSenderName);
		senderEmail = (EditText) view.findViewById(R.id.edittextSenderEmail);
		senderPhone = (EditText) view.findViewById(R.id.edittextSenderPhone);
		senderMessage = (EditText) view.findViewById(R.id.edittextSendmessege);

		restaurantOperation = (LinearLayout) view
				.findViewById(R.id.linearlayoutButtonsCombo);
		reserveTableUserDetail = (LinearLayout) view
				.findViewById(R.id.linearlayoutuserDetails);
		subscribe = (ImageButton) view.findViewById(R.id.buttonSuscribe);
		// tripadvisor=(WebView) view.findViewById(R.id.webViewtripAdvisor);
	}

	@Override
	public void onClick(View click) {
		// TODO Auto-generated method stub
		switch (click.getId()) {
		case R.id.buttonChooseMenu:
			if (datetime == null)
				Toast.makeText(getActivity(), "Please Set The Data and Time",
						Toast.LENGTH_SHORT).show();
			else {

				fragmentResturantProfileCommunicator.setButtonClicked(
						SELECTED_RESTURANT_DTO.getResturantID(), datetime);
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
			} else if (datetime==null) {
				Toast.makeText(getActivity(), "Please Set Date and Time",
						Toast.LENGTH_SHORT).show();
			} else {
				new AdminReserveTableHttpPost().execute();

			}
			break;
		case R.id.buttonReserveTable:
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
			} else if (datetime == null) {
				Toast.makeText(getActivity(), "Please Set Date and Time",
						Toast.LENGTH_SHORT).show();

			} else {
				new AdminReserveTableHttpPost().execute();

			}
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
							datetime = (year
									+ "-"
									+ (monthNumber + 1)
									+ "-"
									+ calendarSelected
											.get(Calendar.DAY_OF_MONTH) + " "
									+ hour24 + ":" + min);
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
		double value = Double.parseDouble(SELECTED_RESTURANT_DTO
				.getResturantAddress().getResturantDistance());
		double rounded = (double) Math.round(value * 100) / 100;
		restaurantDistance.setText(Double.toString(rounded) + "km");
		restaurantPhoneNumber.setText(SELECTED_RESTURANT_DTO
				.getResturantContactInfo().getResturantphoneNoA());
	}

	public static interface FragmentResturantProfileCommunicator {
		public void setButtonClicked(int restaurantID, String dateTime);
	}

	public class AdminReserveTableHttpPost extends
			AsyncTask<String, Void, String> {
		ProgressDialog pd = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (pd == null) {
				pd = new ProgressDialog(getActivity());
				pd.setCancelable(true);
				pd.setTitle("Please wait");
				pd.setMessage("Reserving Table ...");
				pd.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String response = LoginHttpPostConnection();
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (pd.isShowing()) {
				pd.dismiss();
				pd = null;

				try {
					JSONObject login_status = new JSONObject(result);
					Log.d("fsdfs", result);
					String sucess = login_status.getString("status");
					String message = login_status.getString("message");
					if (sucess.equals("success")) {
						Toast.makeText(getActivity(), message,
								Toast.LENGTH_SHORT).show();

					} else if (sucess.equals("error")) {
						Toast.makeText(getActivity(), message,
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	long addEventToCalanadar() {

		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT-1"));
		Date dt = null;
		try {
			dt = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(datetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final Calendar beginTime = Calendar.getInstance();
		cal.setTime(dt);

		// beginTime.set(2013, 7, 25, 7, 30);
		beginTime.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE));

		long eventID = writeEvent.pushAppointmentsToCalender(getActivity(),
				SELECTED_RESTURANT_DTO.getResturantName(), senderMessage
						.getText().toString(), SELECTED_RESTURANT_DTO
						.getResturantAddress().getResturantStreetAddress(), 0,
				beginTime.getTimeInMillis(), true, false);
		return eventID;
	}

	class ReservationDetails {
		private String name = senderName.getText().toString();
		private String email = senderEmail.getText().toString();
		private String phone = senderPhone.getText().toString();
		private int restaurant_id = SELECTED_RESTURANT_DTO.getResturantID();
		private String message = senderMessage.getText().toString();
		private String booking_time = datetime;
		private String status = "PENDING";
	}

	public String LoginHttpPostConnection() {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(AppConstants.RESTAURANTS_RESERVE_TABLE);
		post.addHeader("api", AppConstants.API);
		long event_id = addEventToCalanadar();

		Gson gson = new Gson();
		ReservationDetails registrationDetails = new ReservationDetails();
		String json_string = gson.toJson(registrationDetails);
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("json_string", json_string));
			params.add(new BasicNameValuePair("reg_Id",
					RestaurantCatagory.regid));
			params.add(new BasicNameValuePair("event_id", Long
					.toString(event_id)));
			Log.d("RESERCATION PARAMS", params.toString());
			post.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			String result = convertStreamToString(is);
			// Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
			Log.d("HttpPOst Rest", result);
			return result;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append((line + "\n"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
