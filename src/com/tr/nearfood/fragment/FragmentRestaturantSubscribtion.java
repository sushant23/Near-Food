package com.tr.nearfood.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.tr.nearfood.R;
import com.tr.nearfood.utills.ActivityLayoutAdjuster;
import com.tr.nearfood.utills.AppConstants;
import com.tr.nearfood.utills.GPSTracker;

public class FragmentRestaturantSubscribtion extends Fragment implements
		OnClickListener, OnCheckedChangeListener, LocationListener {

	View view;
	FragmentResturantSubscribtionCommunicator fragmentRestaurantSubscribtionCommunicator;
	Button adminLogin, signUpButton;
	EditText firstName, lastName, restaurantName, restaurantContactNo,
			restaurantEmail, password;
	RadioGroup restaurantLocationRadioGroup;
	RadioButton currentLocation, locationByCoordinate, locationFromGoogleMap;
	public static double longitude, latitude;
	String fName = "", lName = "", resName = "", resContact = "",
			resEmail = "", resPass = "";
	SharedPreferences regPrefs;
	Editor editor;
	private LocationManager locationManager;
	GPSTracker gps;

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
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			fName = savedInstanceState.getString("firstname", null);
			lName = savedInstanceState.getString("lastname", null);
			resEmail = savedInstanceState.getString("email", null);
			resPass = savedInstanceState.getString("password", null);
			resContact = savedInstanceState.getString("contact", null);
			resName = savedInstanceState.getString("name", null);

		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		regPrefs.edit().clear().commit();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		regPrefs.edit().clear().commit();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		regPrefs.edit().clear().commit();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		if (fName != null)
			outState.putString("firstname", fName);
		if (lName != null)
			outState.putString("lastname", lName);
		if (resEmail != null)
			outState.putString("email", resEmail);
		if (resPass != null)
			outState.putString("password", resPass);
		if (resContact != null)
			outState.putString("contact", resContact);
		if (resName != null)
			outState.putString("name", resName);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ActivityLayoutAdjuster.assistActivity(getActivity());
		regPrefs = getActivity().getSharedPreferences("MyPrefs", 0);
		editor = regPrefs.edit();
		view = inflater.inflate(R.layout.fragment_restaurant_subscribtion,
				container, false);

		initializeUIElsments();

		if (fName != null || lName != null || resEmail != null
				|| resPass != null || resName != null || resContact != null) {
			setTheUIElements();
		}

		prefdata();
		if (longitude != 0 && latitude != 0)
			Toast.makeText(getActivity(),
					"Longitude=" + longitude + "Latitude=" + latitude,
					Toast.LENGTH_SHORT).show();
		restaurantLocationRadioGroup.setOnCheckedChangeListener(this);

		adminLogin.setOnClickListener(this);
		signUpButton.setOnClickListener(this);
		return view;
	}

	void setTheUIElements() {
		firstName.setText(fName);
		lastName.setText(lName);
		restaurantName.setText(resName);
		restaurantEmail.setText(resEmail);
		restaurantContactNo.setText(resContact);
		password.setText(resPass);
	}

	void prefdata() {
		fName = regPrefs.getString("firstname", null);
		lName = regPrefs.getString("lastname", null);
		resName = regPrefs.getString("name", null);
		resEmail = regPrefs.getString("email", null);
		resContact = regPrefs.getString("contact", null);
		resPass = regPrefs.getString("password", null);
		setTheUIElements();
	}

	private void initializeUIElsments() {
		// TODO Auto-generated method stub
		adminLogin = (Button) view
				.findViewById(R.id.buttonRestaurantAdminLogin);
		signUpButton = (Button) view
				.findViewById(R.id.buttonRestaurantSubscriptionSubmit);
		restaurantLocationRadioGroup = (RadioGroup) view
				.findViewById(R.id.radioGroupRestaurantLocationChooser);

		firstName = (EditText) view
				.findViewById(R.id.editTextRestaurantOwnerFirstName);
		lastName = (EditText) view
				.findViewById(R.id.editTextRestaurantOwnerLastName);
		restaurantName = (EditText) view
				.findViewById(R.id.editTextRestaurantName);
		restaurantEmail = (EditText) view
				.findViewById(R.id.editTextRestaurantEmailAddress);
		password = (EditText) view
				.findViewById(R.id.editTextRestaurantOwnerPassword);
		restaurantContactNo = (EditText) view
				.findViewById(R.id.editTextRestaurantContactInfo);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.buttonRestaurantAdminLogin:
			fragmentRestaurantSubscribtionCommunicator.setButtonAdminLogin();
			Log.d("Subscribtion", "BUTTOn IS CLICKED");
			break;
		case R.id.buttonRestaurantSubscriptionSubmit:
			if (fName == null || lName == null || resEmail == null
					|| resPass == null || resName == null || resContact == null
					|| latitude == 0 || longitude == 0) {
				Toast.makeText(getActivity(),
						"Please Enter All the Field Properly",
						Toast.LENGTH_SHORT).show();
			} else {
				HttpPostConnection();
				regPrefs.edit().clear().commit();
			}
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
			getRequiredFields();
			fragmentRestaurantSubscribtionCommunicator
					.setGetCoordinateFromMap();
			break;
		case R.id.radioButtonCurrentLOcation:
			// Create class object
			gps = new GPSTracker(getActivity());

			// Check if GPS enabled
			if (gps.canGetLocation()) {

				latitude = gps.getLatitude();
				 longitude = gps.getLongitude();

				// \n is for new line
				Toast.makeText(
						getActivity(),
						"Your Location is - \nLat: " + latitude + "\nLong: "
								+ longitude, Toast.LENGTH_LONG).show();
			} else {
				// Can't get location.
				// GPS or network is not enabled.
				// Ask user to enable GPS/network in settings.
				gps.showSettingsAlert();
			}
			break;
		default:
			break;
		}

	}

	public void getRequiredFields() {
		fName = firstName.getText().toString();
		lName = lastName.getText().toString();
		resName = restaurantName.getText().toString();
		resContact = restaurantContactNo.getText().toString();
		resEmail = restaurantEmail.getText().toString();
		resPass = password.getText().toString();

		editor.putString("firstname", fName);
		editor.putString("lastname", lName);
		editor.putString("email", resEmail);
		editor.putString("name", resName);
		editor.putString("contact", resContact);
		editor.putString("password", resPass);
		editor.commit();

	}

	public void HttpPostConnection() {
		getRequiredFields();
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(AppConstants.RESTAURANTS_REGISTRATION);
		List<NameValuePair> registrationData = new ArrayList<NameValuePair>();
		registrationData.add(new BasicNameValuePair("firstname", fName));
		registrationData.add(new BasicNameValuePair("lastname", lName));
		registrationData.add(new BasicNameValuePair("email", resEmail));
		registrationData.add(new BasicNameValuePair("password", resPass));
		registrationData.add(new BasicNameValuePair("latitude", String
				.valueOf(latitude)));
		registrationData.add(new BasicNameValuePair("longitude", String
				.valueOf(longitude)));
		registrationData.add(new BasicNameValuePair("contact", resContact));
		registrationData.add(new BasicNameValuePair("restaurant", resName));

		try {
			post.setEntity(new UrlEncodedFormEntity(registrationData));
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			String result = convertStreamToString(is);
			Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
			Log.d("HttpPOst Rest", result);
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

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}
}
