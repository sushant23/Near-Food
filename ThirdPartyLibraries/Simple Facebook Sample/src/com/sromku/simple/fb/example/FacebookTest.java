package com.sromku.simple.fb.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.SessionDefaultAudience;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.example.utils.Utils;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Logger;

public class FacebookTest extends Activity {
	private static final String APP_ID = "1384264938499969";
	private static final String APP_NAMESPACE = "near_food";
	private SimpleFacebook mSimpleFacebook;
	Button showProfile;
	TextView detailProfile;

	@Override
	public void onResume() {
		super.onResume();
		mSimpleFacebook = SimpleFacebook.getInstance(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		showProfile = (Button) findViewById(R.id.buttonTest);
		detailProfile = (TextView) findViewById(R.id.tvDetail);
		mSimpleFacebook = SimpleFacebook.getInstance();
		// set log to true
		Logger.DEBUG_WITH_STACKTRACE = true;

		// initialize facebook configuration
		Permission[] permissions = new Permission[] { Permission.PUBLIC_PROFILE };

		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
				.setAppId(APP_ID).setNamespace(APP_NAMESPACE)
				.setPermissions(permissions)
				.setDefaultAudience(SessionDefaultAudience.FRIENDS)
				.setAskForAllPermissionsAtOnce(false).build();

		SimpleFacebook.setConfiguration(configuration);

		final OnLoginListener onLoginListener = new OnLoginListener() {

			@Override
			public void onFail(String reason) {
				// mTextStatus.setText(reason);
				Log.w("FaceBook Test", "Failed to login");
			}

			@Override
			public void onException(Throwable throwable) {
				// mTextStatus.setText("Exception: " + throwable.getMessage());
				Log.e("FaceBook Test", "Bad thing happened", throwable);
			}

			@Override
			public void onThinking() {
				// show progress bar or something to the user while login is
				// happening
				// mTextStatus.setText("Thinking...");
			}

			@Override
			public void onLogin() {
				// change the state of the button or do whatever you want
				// mTextStatus.setText("Logged in");
				// loggedInUIState();
				Toast.makeText(getApplicationContext(), "User Is loggedIn",
						Toast.LENGTH_SHORT).show();
				profileDetails();

			}

			@Override
			public void onNotAcceptingPermissions(Permission.Type type) {
				// toast(String.format("You didn't accept %s permissions",
				// type.name()));
			}
		};
		mSimpleFacebook.login(onLoginListener);

	}

	public void profileDetails() {
		showProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				SimpleFacebook.getInstance().getProfile(
						new OnProfileListener() {

							@Override
							public void onThinking() {

							}

							@Override
							public void onException(Throwable throwable) {

								detailProfile.setText(throwable.getMessage());
							}

							@Override
							public void onFail(String reason) {
								detailProfile.setText(reason);
							}

							@Override
							public void onComplete(Profile response) {

								String str = Utils.toHtml(response);
								detailProfile.setText(Html.fromHtml(str));
							}
						});
			}
		});
	}
}
