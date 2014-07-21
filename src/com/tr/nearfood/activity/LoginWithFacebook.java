package com.tr.nearfood.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.tr.nearfood.R;

public class LoginWithFacebook extends Activity {

	private Session.StatusCallback sessionStatusCallback;
	private Session currentSession;
	EditText firstName, lastName, userContactNumber, userAddress;
	SharedPreferences sharedPreference;
	Editor editor;
	Boolean autologin = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_registration);
		firstName = (EditText) findViewById(R.id.editTextFirstName);
		lastName = (EditText) findViewById(R.id.editTextLastName);

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
