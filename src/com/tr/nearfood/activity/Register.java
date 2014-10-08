package com.tr.nearfood.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tr.nearfood.R;
import com.tr.nearfood.adapter.PlaceAutoCompleteAdapter;
import com.tr.nearfood.fragment.FragmentRestaurantMenu;
import com.tr.nearfood.utills.ActivityLayoutAdjuster;
import com.tr.nearfood.utills.AppConstants;
import com.tr.nearfood.utills.SetEventToCalandar;

public class Register extends Activity {

	EditText firstName, lastName, personalEmail, contactNumber;
	Button submit;
	LinearLayout emailAddress;
	String id = "", name = "", email = "";
	Boolean fromgoogel = false;
	List<Integer> confirmedMenuList;
	AutoCompleteTextView autoCompView;
	int[] confirmedMenuArray;
	String json;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_registration);
		Bundle getConfirmedMenuList = getIntent().getExtras();
		if (getConfirmedMenuList != null) {
			confirmedMenuList = getConfirmedMenuList
					.getIntegerArrayList("confirmedMenuItems");
			Log.d("Register Activity",
					Integer.toString(confirmedMenuList.size()));

			confirmedMenuArray = new int[confirmedMenuList.size()];
			for (int i = 0; i < confirmedMenuList.size(); i++)
				confirmedMenuArray[i] = confirmedMenuList.get(i);
		}
		ActivityLayoutAdjuster.assistActivity(this);
		Bundle googlePlusData = getIntent().getExtras();
		if (googlePlusData != null) {
			fromgoogel = googlePlusData.getBoolean("googlePlus");
			id = googlePlusData.getString("id");
			name = googlePlusData.getString("name");
			email = googlePlusData.getString("email");

		}
		intitializrUIElements();

		emailAddress.setVisibility(View.VISIBLE);
		try {
			if (fromgoogel) {
				StringTokenizer tokens = new StringTokenizer(name, " ");
				String first = tokens.nextToken();
				String second = tokens.nextToken();
				firstName.setText(first);
				lastName.setText(second);
				personalEmail.setText(email);
			}
		} catch (NullPointerException e) {
			Toast.makeText(getApplicationContext(),
					"GOOGLE PLUS Login not completer sucessfully",
					Toast.LENGTH_LONG).show();
		}

		autoCompView.setAdapter(new PlaceAutoCompleteAdapter(this,
				R.layout.autocomplete_list));

		submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (firstName.length() == 0 || lastName.length() == 0) {
					if (firstName.length() == 0) {
						firstName.setError("Please Enter Name");
						firstName.requestFocus();
						return;
					} else {
						lastName.setError("Please Enter Name");
						lastName.requestFocus();
						return;
					}
				} else {

					Gson gson = new Gson();
					OrderDetails objectOrderDetails = new OrderDetails();
					json = gson.toJson(objectOrderDetails);

					Intent gcm_test = new Intent(getApplicationContext(),
							com.tr.nearfood.pushnotification.MainActivity.class);
					StringBuilder builder = new StringBuilder();
					for (int i : confirmedMenuArray) {
						builder.append(i);
						builder.append(",");
					}
					String items_list = builder.toString();
					// items_list=items_list.substring(1,
					// items_list.length()-1);
					gcm_test.putExtra("json_string", json);
					gcm_test.putExtra("items", items_list);
					startActivity(gcm_test);
					// new SendOrderHttpPost().execute(json);
					Log.d("orderDetails json data", items_list);

				}
			}
		});

	}

	class OrderDetails {
		private String name = firstName.getText().toString() + " "
				+ lastName.getText().toString();
		private String email = personalEmail.getText().toString();
		private String phone = contactNumber.getText().toString();
		private String address = autoCompView.getText().toString();
		private String datetime = FragmentRestaurantMenu.datetime;
		private int restaurant_id = FragmentRestaurantMenu.SELECTED_RESTAURANTID;
		private int[] items = confirmedMenuArray;

	}

	private void intitializrUIElements() {
		// TODO Auto-generated method stub
		firstName = (EditText) findViewById(R.id.editTextFirstName);
		lastName = (EditText) findViewById(R.id.editTextLastName);

		personalEmail = (EditText) findViewById(R.id.editTextPersonalEmail);
		contactNumber = (EditText) findViewById(R.id.editTextUserContact);

		submit = (Button) findViewById(R.id.buttonSubmitUserInformation);
		autoCompView = (AutoCompleteTextView) findViewById(R.id.autoCompleteUserAddress);

		emailAddress = (LinearLayout) findViewById(R.id.linLayoutUserEmail);
	}

	public class SendOrderHttpPost extends AsyncTask<String, Void, String> {
		ProgressDialog pd = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (pd == null) {
				pd = new ProgressDialog(Register.this);
				pd.setCancelable(true);
				pd.setTitle("Please wait");
				pd.setMessage("Sending Your Order...");
				pd.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String response = sendOrderDetails(AppConstants.CUSTOMER_ORDER,
					params[0]);
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
					String sucess = login_status.getString("status");
					String message = login_status.getString("message");
					if (sucess.equals("success")) {
						Toast.makeText(getApplicationContext(), message,
								Toast.LENGTH_SHORT).show();

					} else if (sucess.equals("error")) {
						Toast.makeText(getApplicationContext(), message,
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public String sendOrderDetails(String url, String order) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		httppost.setHeader("api", AppConstants.API);

		try {
			List<NameValuePair> namevaluepair = new ArrayList<NameValuePair>();
			namevaluepair.add(new BasicNameValuePair("json_string", json));
			httppost.setEntity(new UrlEncodedFormEntity(namevaluepair));
			HttpResponse resp;
			resp = httpclient.execute(httppost);
			HttpEntity ent = resp.getEntity();
			return EntityUtils.toString(ent);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}

}
