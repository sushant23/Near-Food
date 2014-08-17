package com.tr.nearfood.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.tr.nearfood.R;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LoginWithGooglePlus extends Activity implements
		ConnectionCallbacks, OnConnectionFailedListener {
	private static final int RC_SIGN_IN = 0;
	// Logcat tag
	private static final String TAG = "GooglePlus Login";
	String id, name,email;
	Boolean fromGooglePlus = true;
	List<Integer> confirmedMenuList;
	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;

	private boolean mIntentInProgress;

	private boolean mSignInClicked;

	private ConnectionResult mConnectionResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_login);
		Bundle getConfirmedMenuList = getIntent().getExtras();
		if (getConfirmedMenuList != null) {
			confirmedMenuList = getConfirmedMenuList
					.getIntegerArrayList("confirmedMenuItems");
		}
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN).build();
		mGoogleApiClient.connect();
		signInWithGplus();
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mGoogleApiClient.connect();
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
	
		startReg.putIntegerArrayListExtra("confirmedMenuItems",
				(ArrayList<Integer>) confirmedMenuList);
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
				email=Plus.AccountApi.getAccountName(mGoogleApiClient);
				Log.d("UserProfileGoogleplus", "enetereted" + name);
				

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
			} catch (NullPointerException e) {
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

}
