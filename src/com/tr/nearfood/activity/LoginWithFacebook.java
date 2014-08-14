package com.tr.nearfood.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.google.gson.Gson;
import com.tr.nearfood.R;
import com.tr.nearfood.activity.Register.OrderDetails;
import com.tr.nearfood.adapter.PlaceAutoCompleteAdapter;
import com.tr.nearfood.fragment.FragmentRestaurantMenu;

public class LoginWithFacebook extends Activity {

	private Session.StatusCallback sessionStatusCallback;
	private Session currentSession;
	EditText firstName, lastName, userContactNumber,userEmail;
	AutoCompleteTextView userAddress;
	SharedPreferences sharedPreference;
	Editor editor;
	Boolean autologin = false;
	List<Integer> confirmedMenuList;
	int[] confirmedMenuArray;
	Button submitorderButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		initializeUiElements();
		
		userAddress.setAdapter(new PlaceAutoCompleteAdapter(this,
				R.layout.autocomplete_list));
		sharedPreference = getApplicationContext().getSharedPreferences(
				"MyPrefs", 0);
		editor = sharedPreference.edit();
		// create instace for sessionStatusCallback
		sessionStatusCallback = new Session.StatusCallback() {

			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				onSessionStateChange(session, state, exception);

			}
		};

		autologin = sharedPreference.getBoolean("autologin", false);
		// login to facebook
		if (autologin == false)
			connectToFB();
		if (autologin == true) {
			Log.d("autologin", "autologin bhayo la badhai xa");
			String fname = sharedPreference.getString("fname", "no value");
			String lname = sharedPreference.getString("lname", "no value");
			firstName.setText(fname);
			lastName.setText(lname);
		}
		// logout from facebook
		/*
		 * if (currentSession != null) {
		 * currentSession.closeAndClearTokenInformation(); editor.clear();
		 * editor.commit();}
		 */

		submitorderButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
						userEmail.getText().toString()).matches()
						&& !TextUtils.isEmpty(userEmail.getText()
								.toString())) {
					userEmail.setError("Invalid Email");
					userEmail.requestFocus();
					return;
				} else {

					Gson gson = new Gson();
					OrderDetails objectOrderDetails = new OrderDetails();
					String json = gson.toJson(objectOrderDetails);
					//new SendOrderHttpPost().execute(json);
					Log.d("orderDetails json data", json);
					Toast.makeText(getApplicationContext(), json,
							Toast.LENGTH_LONG).show();

				}
			}
		});
	}
	private void initializeUiElements() {
		// TODO Auto-generated method stub
		submitorderButton=(Button) findViewById(R.id.buttonSubmitUserInformation);
		firstName = (EditText) findViewById(R.id.editTextFirstName);
		lastName = (EditText) findViewById(R.id.editTextLastName);
		userAddress = (AutoCompleteTextView) findViewById(R.id.autoCompleteUserAddress);
		userEmail=(EditText) findViewById(R.id.editTextPersonalEmail);
		userContactNumber=(EditText) findViewById(R.id.editTextUserContact);
	}
	class OrderDetails {
		private String name = firstName.getText().toString() + " "
				+ lastName.getText().toString();
		private String email = userEmail.getText().toString();
		private String phone = userContactNumber.getText().toString();
		private String address = userAddress.getText().toString();
		private String datetime = FragmentRestaurantMenu.SETDATETIME;
		private int restaurant_id=FragmentRestaurantMenu.SELECTED_RESTAURANTID;
		private int[] items = confirmedMenuArray;

	}
	
	public class SendOrderHttpPost extends AsyncTask<String, Void, String> {
		ProgressDialog pd = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (pd == null) {
				pd = new ProgressDialog(getApplicationContext());
				pd.setCancelable(true);
				pd.setTitle("Please wait");
				pd.setMessage("Menu Item list is loading...");
				pd.show();
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String response = sendOrderDetails("PostUrl", params[0]);
			return response;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (pd.isShowing()) {
				pd.dismiss();
				pd = null;
			
			}

		}

	}

	public String sendOrderDetails(String url, String order) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			httppost.setEntity(new StringEntity("your string"));
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
	/**
	 * Connects the user to facebook
	 */
	public void connectToFB() {

		List<String> permissions = new ArrayList<String>();
		permissions.add("publish_stream");

		currentSession = new Session.Builder(this).build();
		currentSession.addCallback(sessionStatusCallback);

		Session.OpenRequest openRequest = new Session.OpenRequest(
				LoginWithFacebook.this);
		openRequest.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
		openRequest.setRequestCode(Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE);
		openRequest.setPermissions(permissions);
		currentSession.openForPublish(openRequest);

	}

	/**
	 * this method is used by the facebook API
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (currentSession != null) {
			currentSession
					.onActivityResult(this, requestCode, resultCode, data);
		}
	}

	/**
	 * manages the session state change. This method is called after the
	 * <code>connectToFB</code> method.
	 * 
	 * @param session
	 * @param state
	 * @param exception
	 */
	private void buildUserInfoDisplay(GraphUser user) {

		String fname = user.getFirstName();
		String lname = user.getLastName();
		firstName.setText(fname);
		lastName.setText(lname);
		String id = user.getId();

		Log.d("user info ", user.toString());
		Toast.makeText(getApplicationContext(),
				id + "\n" + fname + "\n" + lname, Toast.LENGTH_LONG).show();

	}

	@SuppressWarnings("deprecation")
	private void onSessionStateChange(final Session session,
			SessionState state, Exception exception) {
		if (session != currentSession) {
			return;
		}

		if (state.isOpened()) {
			// Log in just happened.
			Toast.makeText(getApplicationContext(), "session opened",
					Toast.LENGTH_SHORT).show();
			editor.putBoolean("autologin", true);

			// Request user data and show the results
			Request.executeMeRequestAsync(session,
					new Request.GraphUserCallback() {

						@Override
						public void onCompleted(GraphUser user,
								Response response) {
							if (user != null) {
								// Display the parsed user info
								buildUserInfoDisplay(user);
								editor.putString("fname", user.getFirstName());
								editor.putString("lname", user.getLastName());
								editor.commit();
							}
						}

					});
		} else if (state.isClosed()) {
			// Log out just happened. Update the UI.
			editor.putBoolean("autologin", false);
			Toast.makeText(getApplicationContext(), "session closed",
					Toast.LENGTH_SHORT).show();
		}
	}

}
