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
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tr.nearfood.R;
import com.tr.nearfood.fragment.FragmentRestaturantSubscribtion.RegistrationDetails;
import com.tr.nearfood.utills.AppConstants;

public class FragmentAdminManageRestaurantDetails extends Fragment implements
		OnClickListener {
	public static String AUTH;

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
	Button save, cancel;
	View view;
	String res_email, res_contact;

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
		save.setOnClickListener(this);
		// new AdminRegistraationHttpPost().execute();
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
		save = (Button) view.findViewById(R.id.bSaveRestaurantEditedDetails);
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
			tvrestaurantCityAddress.setText(restaurantCityAddress);
			lLayoutRestaurantCityAddress.setVisibility(View.GONE);
			break;
		case R.id.bRestaurantEmailAddressEdit:
			lLayoutRestaurantEmailAddress.setVisibility(View.VISIBLE);
			break;
		case R.id.bRestaurantEmailAddressCommit:
			res_email = etrestaurantEmailAddress.getText().toString();
			tvrestaurantEmailAddress.setText(res_email);
			lLayoutRestaurantEmailAddress.setVisibility(View.GONE);
			break;
		case R.id.bRestaurantContactNumberEdit:
			lLayoutRestaurantContactNumber.setVisibility(View.VISIBLE);
			break;
		case R.id.bRestaurantContactNumberCommit:
			res_contact = etrestaurantContactNumber.getText().toString();
			tvrestaurantContactNumber.setText(res_contact);
			lLayoutRestaurantContactNumber.setVisibility(View.GONE);
			break;
		case R.id.bSaveRestaurantEditedDetails:
			new AdminRegistraationHttpPost().execute();
			break;
		default:
			break;
		}
	}

	public class AdminRegistraationHttpPost extends
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
				pd.setMessage("Changing the Details ...");
				pd.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String response = HttpPostConnection();
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
					JSONObject register_status = new JSONObject(result);
					String sucess = register_status.getString("status");
					String message = register_status.getString("message");
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

	class RegistrationDetails {

		private String email = res_email;
		private String contact = res_contact;
		/*
		 * private String firstname = fName; private String lastname = lName;
		 * 
		 * private String password = resPass; private Double latitude =
		 * LATITUDE; private Double longitude = LONGITUDE; private String
		 * restaurant = resName; private String role = "restaurant"; private
		 * int[] restaurant_type = catagory; private String street_address =
		 * address; private String city_address = city;
		 */
	}

	public String HttpPostConnection() {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(AppConstants.RESTAURANTS_DETAILS_EDIT);
		post.addHeader("api", AppConstants.API);
		post.setHeader("Authorization", "Basic " + AUTH);

		Gson gson = new Gson();
		RegistrationDetails registrationDetails = new RegistrationDetails();
		String json_string = gson.toJson(registrationDetails);
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("json_string", json_string));
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

	private String convertStreamToString(InputStream is) {

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
