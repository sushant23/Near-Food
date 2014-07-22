package com.tr.nearfood.activity;

import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.tr.nearfood.R;

public class ChooseLoginMethod extends Activity implements OnClickListener,
		ConnectionCallbacks, OnConnectionFailedListener {
	Button facebookLogin;
	Button manualLogin;
	SignInButton googleplusLogin;
	private static final int RC_SIGN_IN = 0;
	// Logcat tag
	private static final String TAG = "GooglePlus Login";
	String id, name;
	Boolean fromGooglePlus = true;
	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;

	/**
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;

	private boolean mSignInClicked;

	private ConnectionResult mConnectionResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_login);
		facebookLogin = (Button) findViewById(R.id.buttonFacebookSignin);
		googleplusLogin = (SignInButton) findViewById(R.id.buttonGooglePlusSignin);
		manualLogin = (Button) findViewById(R.id.buttonUserManualLOgin);

		facebookLogin.setOnClickListener(this);
		googleplusLogin.setOnClickListener(this);
		manualLogin.setOnClickListener(this);
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();

	}

	/**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mGoogleApiClient.connect();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		signOutFromGplus();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		Log.d("Googleplus login", "onActivityResult methos called");

		// TODO Auto-generated method stub
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		mSignInClicked = false;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		Log.d("Googleplus login", "onconnected methos called");
		// Get user's information
		getProfileInformation();

		Intent startReg = new Intent(this, Register.class);
		startReg.putExtra("googlePlus", fromGooglePlus);
		startReg.putExtra("id", id);
		startReg.putExtra("name", name);
		startActivity(startReg);
		// Update the UI after signin

	}

	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				id = currentPerson.getId();
				name = currentPerson.getDisplayName();
				Log.d("UserProfileGoogleplus", "enetereted" + name);
				Toast.makeText(getApplicationContext(), name,
						Toast.LENGTH_SHORT).show();

				/*
				 * String personPhotoUrl = currentPerson.getImage().getUrl();
				 * String personGooglePlusProfile = currentPerson.getUrl();
				 * String email =
				 * Plus.AccountApi.getAccountName(mGoogleApiClient); String id =
				 * currentPerson.getId(); List<PlacesLived> location =
				 * currentPerson.getPlacesLived(); Log.e(TAG, "ID:" + id +
				 * "Name: " + personName + ", plusProfile: " +
				 * personGooglePlusProfile + ", email: " + email +
				 * ",current Location:" + location.toString() + ", Image: " +
				 * personPhotoUrl);
				 */

			} else {
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
		Log.d("UserProfileGoogleplus", "resolveSignInerroe");
	}

	/**
	 * Sign-out from google
	 * */
	private void signOutFromGplus() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			mGoogleApiClient.connect();

		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		mGoogleApiClient.connect();

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.buttonFacebookSignin:
			Intent facebookLoginIntent = new Intent(getApplicationContext(),
					LoginWithFacebook.class);
			startActivity(facebookLoginIntent);
			break;
		case R.id.buttonGooglePlusSignin:
			// Signin button clicked
			signInWithGplus();
			break;
		case R.id.buttonUserManualLOgin:
			Intent startRegisterActivity = new Intent(this, Register.class);
			startActivity(startRegisterActivity);
			break;
		default:
			break;
		}
	}
}
