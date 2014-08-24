package com.sromku.simple.fb.example;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLoginListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityFacebook extends Activity {
	Button facebookLogin;
	TextView mTextStatus;
	String TAG="MY FAVCEBOOK LOGIN";
	private SimpleFacebook mSimpleFacebook;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity_facebook);
		mSimpleFacebook = SimpleFacebook.getInstance();
		facebookLogin=(Button) findViewById(R.id.myloginButton);
		mTextStatus=(TextView) findViewById(R.id.textViewstatus);
		setLogin();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mSimpleFacebook = SimpleFacebook.getInstance(this);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
	}
	/**
	 * Login example.
	 */
	private void setLogin() {
		// Login listener
		final OnLoginListener onLoginListener = new OnLoginListener() {

			@Override
			public void onFail(String reason) {
				mTextStatus.setText(reason);
				Log.w(TAG, "Failed to login");
			}

			@Override
			public void onException(Throwable throwable) {
				mTextStatus.setText("Exception: " + throwable.getMessage());
				Log.e(TAG, "Bad thing happened", throwable);
			}

			@Override
			public void onThinking() {
				// show progress bar or something to the user while login is
				// happening
				mTextStatus.setText("Thinking...");
			}

			@Override
			public void onLogin() {
				// change the state of the button or do whatever you want
				mTextStatus.setText("Logged in");
				
			}

			@Override
			public void onNotAcceptingPermissions(Permission.Type type) {
//				toast(String.format("You didn't accept %s permissions", type.name()));
			}
		};

		facebookLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mSimpleFacebook.login(onLoginListener);
			}
		});
	}

}
